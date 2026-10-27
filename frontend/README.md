# Frontend Bodega

Aplicacion React + Vite para el backend Spring Boot de este repositorio. Todas las operaciones pasan por la API; el navegador nunca se conecta a Neon.

## Configuracion y ejecucion

En PowerShell desde `frontend`:

```powershell
Copy-Item .env.example .env
npm install
npm run dev
```

Configure `.env` con la URL publica del backend:

```properties
VITE_API_URL=http://localhost:8080/api
```

Para compilar produccion:

```powershell
npm run build
```

El backend debe permitir el origen de Vite con `CORS_ALLOWED_ORIGINS=http://localhost:5173`.

## Rutas

| Ruta | Uso | Rol visible |
|---|---|---|
| `/login` | Autenticacion JWT | Publica |
| `/` | Resumen diario | ADMIN, CAJERO |
| `/ventas/nueva` | Punto de venta y escaner | ADMIN, CAJERO |
| `/ventas` | Historial y detalle | ADMIN, CAJERO |
| `/productos` | Catalogo y codigos de barras | ADMIN, CAJERO; escritura ADMIN |
| `/inventario` | Movimientos y ajustes | ADMIN |
| `/categorias` | Categorias | ADMIN |
| `/reportes` | Reportes y CSV | ADMIN |
| `/usuarios` | Usuarios | ADMIN |

El backend mantiene siempre la autorizacion real. Ocultar rutas o botones no reemplaza sus controles de Spring Security.

## Escaner USB y venta

1. Inicie sesion y abra **Nueva venta**.
2. Conecte el escaner USB configurado como teclado.
3. Mantenga el cursor en el campo de lectura. El foco se recupera tras cada busqueda.
4. Escanee un codigo que termine con Enter. La aplicacion consulta `GET /productos/barcode/{codigo}` y agrega el producto al carrito.
5. Un escaneo repetido incrementa la cantidad sin exceder el stock que devolvio la API.
6. Seleccione el metodo de pago y confirme. La solicitud envia un `Idempotency-Key` unico a `POST /ventas`.

El total mostrado antes de confirmar es orientativo; Spring Boot recalcula importes, stock y total. Solo se limpia el carrito luego de recibir una venta confirmada.

## Contrato ampliado

Se agregaron al backend los listados paginados reales de ventas y movimientos, ademas de endpoints de cambio de estado para usuarios, categorias y productos. Esto permite que la UI no dependa de datos simulados.

No se realizaron pruebas contra Neon ni sesiones reales desde esta maquina. La compilacion valida imports, JSX y el empaquetado, pero para validar operaciones debe iniciarse el backend con su base de datos configurada.
