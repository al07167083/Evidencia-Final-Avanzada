<%--
    index.jsp
    Pantalla de bienvenida. Es la puerta de entrada a la aplicacion: desde
    aqui el usuario decide si va a registrarse o a iniciar sesion.
    Pertenece a la capa VISTA de MVC.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calculadora de IMC</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="tarjeta">
        <h1>Calculadora de IMC</h1>
        <p class="subtitulo">Calcula tu Indice de Masa Corporal y da seguimiento a tu progreso.</p>

        <a href="${pageContext.request.contextPath}/registro.jsp">
            <button type="button">Crear una cuenta</button>
        </a>
        <a class="enlace" href="${pageContext.request.contextPath}/login.jsp">
            Ya tengo cuenta, iniciar sesion
        </a>
    </div>
</body>
</html>
