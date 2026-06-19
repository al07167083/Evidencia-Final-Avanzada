<%--
    calcular.jsp
    Pantalla para calcular el IMC. Solo se pide la masa corporal (la estatura
    ya se conoce del registro). Si el usuario no tiene sesion, se le redirige
    al login. Cuando el IMCServlet regresa un resultado, se muestra el valor,
    su clasificacion y una barra de colores para ubicarlo de un vistazo.
    Pertenece a la capa VISTA de MVC.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- Control de acceso directo a la pagina sin sesion iniciada --%>
<c:if test="${empty sessionScope.idUsuario}">
    <c:redirect url="/login.jsp?error=sesion"/>
</c:if>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calcular IMC</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="tarjeta">
        <div class="barra-cabecera">
            <span class="usuario">Hola, ${sessionScope.nombreCompleto}</span>
            <a class="enlace" style="margin:0"
               href="${pageContext.request.contextPath}/logout">Salir</a>
        </div>

        <h1>Calcular IMC</h1>
        <p class="subtitulo">Ingresa tu masa corporal actual.</p>

        <c:if test="${not empty error}">
            <div class="mensaje-error">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/calcular" method="post">
            <label for="peso">Masa corporal (kg)</label>
            <input type="number" id="peso" name="peso" step="0.1" required>
            <p class="ayuda">Debe ser un valor mayor a cero.</p>

            <button type="submit">Calcular</button>
        </form>

        <%-- Resultado del calculo (solo cuando el servlet lo envia) --%>
        <c:if test="${not empty imc}">
            <div class="resultado">
                <div class="valor">${imc}</div>
                <div class="categoria">${clasificacion}</div>
                <div class="barra">
                    <span class="b-bajo"></span>
                    <span class="b-normal"></span>
                    <span class="b-sobre"></span>
                    <span class="b-obes"></span>
                </div>
                <p class="ayuda" style="margin-top:8px">
                    Bajo peso &lt; 18.5 &nbsp;|&nbsp; Normal 18.5-24.9 &nbsp;|&nbsp;
                    Sobrepeso 25-29.9 &nbsp;|&nbsp; Obesidad &ge; 30
                </p>
            </div>
        </c:if>

        <a class="enlace" href="${pageContext.request.contextPath}/historico.jsp">
            Ver mi historico
        </a>
    </div>
</body>
</html>
