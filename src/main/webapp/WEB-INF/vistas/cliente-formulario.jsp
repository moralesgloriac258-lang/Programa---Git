<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>`r`n<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:choose><c:when test="${not empty cliente}">Editar cliente</c:when><c:otherwise>Nuevo cliente</c:otherwise></c:choose> | Dulce Conexión</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/estilos.css">
</head>
<body>
<header class="encabezado"><a class="marca" href="${pageContext.request.contextPath}/clientes">Dulce <b>Conexión</b></a><span>Administración de clientes</span></header>
<main class="contenedor formulario-contenedor">
    <a class="volver" href="${pageContext.request.contextPath}/clientes">← Volver a clientes</a>
    <section class="formulario-tarjeta">
        <p class="etiqueta">INFORMACIÓN DEL CLIENTE</p>
        <h1><c:choose><c:when test="${not empty cliente}">Editar cliente</c:when><c:otherwise>Nuevo cliente</c:otherwise></c:choose></h1>
        <p class="subtitulo">Completa los datos para guardar la información.</p>
        <c:if test="${not empty error}"><div class="alerta error"><c:out value="${error}"/></div></c:if>
        <form method="post" action="${pageContext.request.contextPath}/clientes" class="campos">
            <c:choose>
                <c:when test="${not empty cliente}"><input type="hidden" name="accion" value="actualizar"><input type="hidden" name="id" value="${cliente.id}"></c:when>
                <c:otherwise><input type="hidden" name="accion" value="crear"></c:otherwise>
            </c:choose>
            <label for="nombre">Nombre completo <span>*</span></label>
            <input id="nombre" name="nombre" type="text" maxlength="100" value="${fn:escapeXml(cliente.nombre)}" placeholder="Ej. Laura Gómez" required autofocus>
            <label for="telefono">Teléfono</label>
            <input id="telefono" name="telefono" type="tel" maxlength="20" value="${fn:escapeXml(cliente.telefono)}" placeholder="Ej. 300 123 4567">
            <label for="correo">Correo electrónico</label>
            <input id="correo" name="correo" type="email" maxlength="100" value="${fn:escapeXml(cliente.correo)}" placeholder="nombre@correo.com">
            <div class="form-acciones"><a class="boton boton-claro" href="${pageContext.request.contextPath}/clientes">Cancelar</a><button class="boton boton-principal" type="submit">Guardar cliente</button></div>
        </form>
    </section>
</main>
<footer>Hecho con ♡ para Dulce Conexión</footer>
</body>
</html>

