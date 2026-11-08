<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>`r`n<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Clientes | Dulce Conexión</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/estilos.css">
</head>
<body>
<header class="encabezado">
    <a class="marca" href="${pageContext.request.contextPath}/clientes">Dulce <b>Conexión</b></a>
    <span>Administración de clientes</span>
</header>
<main class="contenedor">
    <div class="titulo-fila">
        <div><p class="etiqueta">MÓDULO CRUD · JDBC + SERVLETS + JSP</p><h1>Clientes</h1></div>
        <a class="boton boton-principal" href="${pageContext.request.contextPath}/clientes?accion=nuevo">＋ Nuevo cliente</a>
    </div>
    <c:if test="${not empty param.mensaje}">
        <div class="alerta exito">
            <c:choose>
                <c:when test="${param.mensaje == 'creado'}">Cliente registrado correctamente.</c:when>
                <c:when test="${param.mensaje == 'actualizado'}">Cliente actualizado correctamente.</c:when>
                <c:when test="${param.mensaje == 'eliminado'}">Cliente eliminado correctamente.</c:when>
                <c:otherwise>No se encontró el cliente solicitado.</c:otherwise>
            </c:choose>
        </div>
    </c:if>
    <c:if test="${not empty error}"><div class="alerta error"><c:out value="${error}"/></div></c:if>
    <form class="busqueda" method="get" action="${pageContext.request.contextPath}/clientes">
        <input type="hidden" name="accion" value="buscar">
        <label for="buscar-id">Buscar por ID</label>
        <input id="buscar-id" name="id" type="number" min="1" placeholder="Ej. 3" value="${fn:escapeXml(param.id)}" required>
        <button class="boton boton-claro" type="submit">Buscar</button>
        <a class="limpiar" href="${pageContext.request.contextPath}/clientes">Ver todos</a>
    </form>
    <section class="tarjeta">
        <div class="tabla-scroll"><table>
            <thead><tr><th>ID</th><th>Nombre</th><th>Teléfono</th><th>Correo</th><th>Acciones</th></tr></thead>
            <tbody>
            <c:choose>
                <c:when test="${empty clientes}"><tr><td class="vacio" colspan="5">No hay clientes para mostrar. Agrega el primero con “Nuevo cliente”.</td></tr></c:when>
                <c:otherwise>
                    <c:forEach var="cliente" items="${clientes}">
                    <tr>
                        <td><span class="id">#<c:out value="${cliente.id}"/></span></td>
                        <td><strong><c:out value="${cliente.nombre}"/></strong></td>
                        <td><c:out value="${cliente.telefono}"/></td>
                        <td><c:out value="${cliente.correo}"/></td>
                        <td class="acciones">
                            <a class="accion editar" href="${pageContext.request.contextPath}/clientes?accion=editar&amp;id=${cliente.id}">Editar</a>
                            <form method="post" action="${pageContext.request.contextPath}/clientes" onsubmit="return confirm('¿Deseas eliminar este cliente?');">
                                <input type="hidden" name="accion" value="eliminar"><input type="hidden" name="id" value="${cliente.id}">
                                <button class="accion borrar" type="submit">Eliminar</button>
                            </form>
                        </td>
                    </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table></div>
    </section>
    <p class="nota">Los datos se guardan en la base MySQL <b>dulce_conexion</b>.</p>
</main>
<footer>Hecho con ♡ para Dulce Conexión</footer>
</body>
</html>

