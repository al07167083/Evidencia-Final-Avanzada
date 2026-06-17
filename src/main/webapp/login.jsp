<%--
    login.jsp
    Formulario de inicio de sesion. El calculo del IMC solo esta disponible
    despues de iniciar sesion, tal como lo exige la evidencia.
    Pertenece a la capa VISTA de MVC.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar sesion - Calculadora de IMC</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="tarjeta">
        <h1>Iniciar sesion</h1>
        <p class="subtitulo">Ingresa para calcular tu IMC.</p>

        <%-- Aviso cuando el registro fue exitoso --%>
        <c:if test="${param.registro == 'ok'}">
            <div class="mensaje-ok">Registro exitoso. Ahora inicia sesion.</div>
        </c:if>

        <%-- Aviso cuando se intento calcular sin sesion --%>
        <c:if test="${param.error == 'sesion'}">
            <div class="mensaje-error">Debes iniciar sesion para calcular tu IMC.</div>
        </c:if>

        <%-- Error de credenciales enviado por el LoginServlet --%>
        <c:if test="${not empty error}">
            <div class="mensaje-error">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <label for="nombreUsuario">Nombre de usuario</label>
            <input type="text" id="nombreUsuario" name="nombreUsuario" required>

            <label for="contrasena">Contrasena</label>
            <input type="password" id="contrasena" name="contrasena" required>

            <button type="submit">Entrar</button>
        </form>

        <a class="enlace" href="${pageContext.request.contextPath}/registro.jsp">
            No tengo cuenta, registrarme
        </a>
    </div>
</body>
</html>
