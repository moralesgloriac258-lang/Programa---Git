# CupcakeApp — módulo CRUD de clientes

Aplicación de consola para administrar clientes de Dulce Conexión con Java, JDBC y MySQL. La estructura sigue el patrón por capas: configuración de conexión, modelo, acceso a datos (DAO) e interfaz de consola.

## Requisitos

- Java 17 o posterior
- Apache Maven
- MySQL Server y MySQL Workbench (opcional)

## Preparar la base de datos

1. Abre MySQL Workbench y conéctate a tu servidor local.
2. Abre `database/schema.sql` y ejecuta el script. Este crea la base `dulce_conexion` y la tabla `cliente`.
3. Ten a la mano el usuario y la contraseña de tu servidor MySQL.

## Configurar la conexión en Windows PowerShell

Abre una terminal nueva en VS Code y configura tus datos. Reemplaza `TU_CLAVE` por la contraseña de MySQL de tu computador:

```powershell
$env:DB_URL = "jdbc:mysql://localhost:3306/dulce_conexion"
$env:DB_USER = "root"
$env:DB_PASSWORD = "TU_CLAVE"
```

La contraseña se configura en la terminal y no se guarda en el código.

## Ejecutar desde VS Code

Abre la carpeta `Programa-Git` en VS Code, selecciona **Terminal → Nueva terminal**, ejecuta las tres líneas de configuración anteriores y luego:

```powershell
mvn compile exec:java
```

## Funciones del menú

1. Registrar cliente.
2. Consultar todos los clientes.
3. Buscar un cliente por ID.
4. Actualizar sus datos.
5. Eliminar un cliente con confirmación.
0. Salir.

El DAO usa consultas preparadas (`PreparedStatement`) y cierra conexiones y resultados automáticamente. Los campos `nombre`, `telefono` y `correo` corresponden al modelo compartido. Nombre y correo de ejemplo no se insertan solos; la persona usuaria registra los datos desde el menú.

## Estructura

```text
Programa-Git/
├── database/schema.sql
├── src/main/java/co/com/dulceconexion/
│   ├── config/ConexionBD.java
│   ├── dao/ClienteDAO.java
│   ├── main/Principal.java
│   └── modelo/Cliente.java
├── pom.xml
└── kotlin.txt
```

## Git

El proyecto ya tiene historial local en la rama `master`. Cuando crees el repositorio público `Programa-Git`, enlázalo con su URL HTTPS y publica los commits:

```bash
git remote add github https://github.com/<tu-usuario>/Programa-Git.git
git remote -v
git push -u github master
```
