<%--
    registro.jsp
    Formulario para dar de alta un nuevo usuario. Recuerda al usuario las
    validaciones (estatura 1.0-2.5 m y edad minima de 15 anios) y muestra el
    mensaje de error que, en su caso, coloque el RegistroServlet.
    Pertenece a la capa VISTA de MVC.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro - Calculadora de IMC</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="tarjeta">
        <h1>Crear cuenta</h1>
        <p class="subtitulo">Estos datos se piden una sola vez.</p>

        <%-- Mensaje de error enviado por el controlador --%>
        <c:if test="${not empty error}">
            <div class="mensaje-error">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/registro" method="post">
            <label for="nombreCompleto">Nombre completo</label>
            <input type="text" id="nombreCompleto" name="nombreCompleto" required>

            <label for="edad">Edad</label>
            <input type="number" id="edad" name="edad" min="15" required>
            <p class="ayuda">Debes tener al menos 15 anios.</p>

            <label for="sexo">Sexo</label>
            <select id="sexo" name="sexo" required>
                <option value="">Selecciona...</option>
                <option value="M">Masculino</option>
                <option value="F">Femenino</option>
            </select>

            <label for="estatura">Estatura (m)</label>
            <input type="number" id="estatura" name="estatura" step="0.01" required>
            <p class="ayuda">Valor entre 1.00 y 2.50 metros.</p>

            <label for="nombreUsuario">Nombre de usuario</label>
            <input type="text" id="nombreUsuario" name="nombreUsuario" required>

            <label for="contrasena">Contrasena</label>
            <input type="password" id="contrasena" name="contrasena" required>

            <button type="submit">Registrarme</button>
        </form>

        <a class="enlace" href="${pageContext.request.contextPath}/login.jsp">
            Ya tengo cuenta, iniciar sesion
        </a>
    </div>
</body>
</html>
