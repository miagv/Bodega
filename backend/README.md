# Bodega Backend

API REST transaccional para inventario y ventas. Java 21, Spring Boot 4.1.2, PostgreSQL/Neon, Flyway y JWT.

## Variables de entorno

No versionar un archivo `.env`. En PowerShell, antes de ejecutar:

```powershell
$env:DB_URL="jdbc:postgresql://ep-example.us-east-2.aws.neon.tech/neondb?sslmode=require"
$env:DB_USERNAME="bodega_user"
$env:DB_PASSWORD="not-a-real-password"
$env:JWT_SECRET="REPLACE_WITH_A_BASE64_SECRET_OF_AT_LEAST_32_BYTES"
$env:CORS_ALLOWED_ORIGINS="http://localhost:5173"
$env:ADMIN_BOOTSTRAP_USERNAME="admin"
$env:ADMIN_BOOTSTRAP_PASSWORD="replace-this-on-first-start"
```

`ADMIN_BOOTSTRAP_*` crea el administrador solo si no existe ningún administrador activo. Después del primer inicio debe quitarse del entorno. `JWT_SECRET` debe ser Base64 y representar al menos 32 bytes.

## Ejecución en Windows y VS Code

Desde la carpeta `backend`:

```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

Flyway aplica `src/main/resources/db/migration/V1__create_inventory_schema.sql` antes de iniciar Hibernate. Hibernate solamente valida el esquema; no lo modifica.

Swagger está disponible en `http://localhost:8080/swagger-ui.html`. Inicie sesión con `POST /api/auth/login`, copie `token` y autorice en Swagger con `Bearer <token>`.

## Endpoints

| Método | Ruta | Rol |
|---|---|---|
| POST | `/api/auth/login` | Público |
| POST | `/api/auth/logout` | Autenticado |
| GET, POST | `/api/usuarios` | ADMIN |
| PUT, PATCH | `/api/usuarios/{id}` | ADMIN |
| GET | `/api/categorias` | Autenticado |
| POST, PUT, PATCH | `/api/categorias/**` | ADMIN |
| GET | `/api/productos` | ADMIN, CAJERO |
| GET | `/api/productos/barcode/{codigo}` | ADMIN, CAJERO |
| POST, PUT, PATCH | `/api/productos/**` | ADMIN |
| GET, POST | `/api/inventario/movimientos` | ADMIN |
| POST | `/api/ventas` | ADMIN, CAJERO |
| GET | `/api/ventas/{id}` | ADMIN, CAJERO |
| POST | `/api/ventas/{id}/anulacion` | ADMIN |
| GET | `/api/alertas/stock-bajo`, `/agotados` | ADMIN |
| GET | `/api/alertas/productos-disponibles` | ADMIN, CAJERO |
| GET | `/api/reportes/**` | ADMIN |

Las ventas requieren la cabecera `Idempotency-Key` con un valor único estable por intento lógico. El backend calcula precios y totales; el cuerpo no puede establecerlos.

Ejemplo de venta:

```json
{
  "metodoPago": "YAPE",
  "detalles": [{ "productoId": 1, "cantidad": 2 }]
}
```

## Consistencia

La creación y anulación de ventas usa una transacción y bloqueos pesimistas de PostgreSQL sobre los productos, ordenados por ID. Así dos ventas simultáneas no pueden descontar el mismo stock disponible. Cada modificación guarda un movimiento de inventario inmutable en la misma transacción.

## Pruebas

Las pruebas unitarias no usan Neon. Cubren rechazo de precios inválidos, código de barras duplicado, stock insuficiente y cálculo de venta con movimiento. Testcontainers PostgreSQL está incluido para pruebas de integración futuras, pero no se ejecuta de forma automática porque requiere Docker.
