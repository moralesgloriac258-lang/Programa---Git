# CupcakeApp — gestión web de clientes

Aplicación web de aprendizaje para el módulo de clientes de Dulce Conexión. Está construida con Java, JDBC, MySQL, Servlets, formularios HTML y JSP. Las JSP se usan como vistas y `ClienteServlet` controla las solicitudes; `ClienteDAO` consulta y modifica los datos.

## Requisitos

- JDK 17
- Apache Maven
- MySQL Server
- Apache Tomcat 10.1 (implementa Jakarta Servlet 6.0 y Jakarta Pages/JSP 3.1)

## 1. Crear la base de datos

Abre MySQL Workbench, conéctate a tu servidor y ejecuta el script `database/schema.sql`. Esto crea la base `dulce_conexion` y la tabla `cliente`.

## 2. Configurar la conexión en VS Code (Windows)

Abre la carpeta `Programa-Git` en VS Code. En **Terminal → Nueva terminal**, escribe tus datos de MySQL en PowerShell (cambia `TU_CLAVE` por tu contraseña):

```powershell
$env:DB_URL = "jdbc:mysql://localhost:3306/dulce_conexion"
$env:DB_USER = "root"
$env:DB_PASSWORD = "TU_CLAVE"
```

La contraseña queda en la terminal actual y no se guarda dentro del proyecto.

## 3. Crear y desplegar el WAR

Desde la terminal, en la carpeta que contiene `pom.xml`, ejecuta:

```powershell
mvn clean package
```

Se generará `target/DulceConexion.war`. Copia ese archivo a la carpeta `webapps` de Tomcat 10.1 e inicia Tomcat. Abre en el navegador:

```text
http://localhost:8080/DulceConexion/
```

Para conectar Tomcat con MySQL, inicia Tomcat desde una terminal que tenga configuradas las tres variables de entorno del paso 2.

## Funciones disponibles

- **GET** `/clientes`: lista los clientes.
- **GET** `/clientes?accion=nuevo`: muestra el formulario vacío.
- **GET** `/clientes?accion=editar&id=1`: muestra un cliente para editar.
- **GET** `/clientes?accion=buscar&id=1`: busca por ID.
- **POST** `/clientes` con `accion=crear`: registra un cliente.
- **POST** `/clientes` con `accion=actualizar`: actualiza los datos.
- **POST** `/clientes` con `accion=eliminar`: elimina el cliente después de confirmar.

El formulario solicita nombre, teléfono y correo. Las JSP están protegidas bajo `WEB-INF` y se renderizan por medio del Servlet.

## Estructura principal

```text
src/main/java/co/com/dulceconexion/
├── config/ConexionBD.java
├── dao/ClienteDAO.java
├── modelo/Cliente.java
└── web/ClienteServlet.java
src/main/webapp/
├── index.jsp
├── assets/estilos.css
└── WEB-INF/vistas/
    ├── clientes.jsp
    ├── cliente-formulario.jsp
    └── error.jsp
```

## Versionamiento

El proyecto tiene historial Git local en la rama `master`. Cuando crees un repositorio remoto, enlázalo con la URL HTTPS real y publica los cambios:

```bash
git remote add github https://github.com/<tu-usuario>/Programa-Git.git
git push -u github master
```
