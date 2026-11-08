# CupcakeApp — módulo CRUD de clientes

Proyecto Maven de ejemplo para gestionar clientes de Dulce Conexión con Java, JDBC y MySQL.

## Requisitos

- Java 17 o posterior
- Maven
- MySQL con la base de datos definida en `database/schema.sql`

Configure antes de ejecutar estas variables de entorno: `DB_URL` (por defecto `jdbc:mysql://localhost:3306/dulce_conexion`), `DB_USER` (por defecto `root`) y `DB_PASSWORD`.

## Versionamiento

El repositorio local se llama `Programa-Git` y usa la rama `master` para seguir la guía del ejercicio. Tras crear el repositorio público remoto, reemplace la URL de ejemplo por su URL HTTPS:

```bash
git remote add github https://github.com/<usuario>/Programa-Git.git
git remote -v
git push -u github master
```

Para el paso de datos personales, el archivo `kotlin.txt` se añade en un segundo commit. Después de conectar el remoto, súbalo con:

```bash
git push github master
```
