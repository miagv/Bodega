create table usuarios (
    id bigserial primary key,
    username varchar(50) not null unique,
    nombre varchar(120) not null,
    password_hash varchar(100) not null,
    rol varchar(20) not null check (rol in ('ADMIN', 'CAJERO')),
    activo boolean not null default true,
    created_at timestamptz not null default current_timestamp,
    updated_at timestamptz not null default current_timestamp
);

create table categorias (
    id bigserial primary key,
    nombre varchar(100) not null unique,
    descripcion varchar(500),
    activa boolean not null default true,
    created_at timestamptz not null default current_timestamp,
    updated_at timestamptz not null default current_timestamp
);

create table productos (
    id bigserial primary key,
    nombre varchar(150) not null,
    descripcion varchar(500),
    codigo_barras varchar(100),
    categoria_id bigint not null references categorias(id),
    precio_compra numeric(14,2) not null check (precio_compra >= 0),
    precio_venta numeric(14,2) not null check (precio_venta >= precio_compra),
    stock_actual integer not null default 0 check (stock_actual >= 0),
    stock_minimo integer not null default 0 check (stock_minimo >= 0),
    unidad_medida varchar(20) not null,
    activo boolean not null default true,
    version bigint not null default 0,
    created_at timestamptz not null default current_timestamp,
    updated_at timestamptz not null default current_timestamp
);
create unique index uk_productos_codigo_barras on productos(codigo_barras) where codigo_barras is not null;
create index idx_productos_nombre on productos(lower(nombre));
create index idx_productos_categoria_activo on productos(categoria_id, activo);

create table ventas (
    id bigserial primary key,
    numero varchar(40) not null unique,
    usuario_id bigint not null references usuarios(id),
    fecha timestamptz not null default current_timestamp,
    metodo_pago varchar(20) not null check (metodo_pago in ('EFECTIVO', 'YAPE', 'PLIN', 'OTRO')),
    estado varchar(20) not null check (estado in ('COMPLETADA', 'ANULADA')),
    total numeric(14,2) not null check (total >= 0),
    idempotency_key varchar(100) not null unique,
    anulada_at timestamptz,
    anulada_por_id bigint references usuarios(id),
    motivo_anulacion varchar(300)
);
create index idx_ventas_fecha on ventas(fecha);
create index idx_ventas_metodo_pago_fecha on ventas(metodo_pago, fecha);

create table detalles_venta (
    id bigserial primary key,
    venta_id bigint not null references ventas(id),
    producto_id bigint not null references productos(id),
    cantidad integer not null check (cantidad > 0),
    precio_unitario numeric(14,2) not null check (precio_unitario >= 0),
    subtotal numeric(14,2) not null check (subtotal >= 0)
);
create index idx_detalles_venta_producto on detalles_venta(producto_id);

create table movimientos_inventario (
    id bigserial primary key,
    producto_id bigint not null references productos(id),
    venta_id bigint references ventas(id),
    tipo varchar(30) not null check (tipo in ('ENTRADA', 'SALIDA', 'AJUSTE', 'VENTA', 'ANULACION_VENTA')),
    cantidad integer not null check (cantidad > 0),
    stock_anterior integer not null check (stock_anterior >= 0),
    stock_posterior integer not null check (stock_posterior >= 0),
    motivo varchar(300) not null,
    usuario_id bigint not null references usuarios(id),
    fecha timestamptz not null default current_timestamp
);
create index idx_movimientos_producto_fecha on movimientos_inventario(producto_id, fecha desc);
create index idx_movimientos_venta on movimientos_inventario(venta_id);
