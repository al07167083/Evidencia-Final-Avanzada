<%--
    historico.jsp
    Muestra el IMC actual y el historico de mediciones. IMPORTANTE: los datos
    NO se leen directamente de la base de datos desde esta pagina, sino que se
    consumen del servicio web REST /api/historico mediante fetch (JavaScript),
    tal como lo pide la evidencia. Se agrega ademas una pequena grafica de
    tendencia dibujada con la etiqueta <canvas>.
    Pertenece a la capa VISTA de MVC.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:if test="${empty sessionScope.idUsuario}">
    <c:redirect url="/login.jsp?error=sesion"/>
</c:if>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Historico de IMC</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="tarjeta">
        <div class="barra-cabecera">
            <span class="usuario">Hola, ${sessionScope.nombreCompleto}</span>
            <a class="enlace" style="margin:0"
               href="${pageContext.request.contextPath}/logout">Salir</a>
        </div>

        <h1>Mi historico</h1>
        <p class="subtitulo">Evolucion de tu IMC a lo largo del tiempo.</p>

        <%-- IMC actual (la medicion mas reciente) --%>
        <div class="resultado" id="actual" style="display:none">
            <div class="valor" id="imcActual">--</div>
            <div class="categoria" id="clasActual"></div>
        </div>

        <%-- Grafica de tendencia dibujada con canvas --%>
        <canvas id="grafica" width="380" height="120"
                style="margin-top:16px; width:100%;"></canvas>

        <%-- Tabla del historico --%>
        <table id="tabla">
            <thead>
                <tr>
                    <th>Fecha</th>
                    <th>Peso (kg)</th>
                    <th>IMC</th>
                    <th>Clasificacion</th>
                </tr>
            </thead>
            <tbody id="cuerpoTabla"></tbody>
        </table>
        <p class="ayuda" id="sinDatos" style="display:none; text-align:center; margin-top:12px">
            Aun no tienes mediciones registradas.
        </p>

        <a class="enlace" href="${pageContext.request.contextPath}/calcular.jsp">
            Volver a calcular
        </a>
    </div>

    <script>
        // URL del servicio REST que expone el historico del usuario en sesion.
        const API = '${pageContext.request.contextPath}/api/historico';

        // Se consume el servicio pidiendo explicitamente JSON. La misma URL
        // puede devolver XML si se solicita con Accept: application/xml.
        fetch(API, { headers: { 'Accept': 'application/json' } })
            .then(resp => {
                if (!resp.ok) { throw new Error('No autorizado o error del servidor'); }
                return resp.json();
            })
            .then(data => pintarHistorico(data.registros || []))
            .catch(err => console.error('Error al consumir el REST:', err));

        // Rellena la tabla, el IMC actual y la grafica con los datos del REST.
        function pintarHistorico(registros) {
            const cuerpo = document.getElementById('cuerpoTabla');

            if (registros.length === 0) {
                document.getElementById('sinDatos').style.display = 'block';
                return;
            }

            // La lista viene ordenada de la mas reciente a la mas antigua:
            // el primer elemento es el IMC actual.
            const actual = registros[0];
            document.getElementById('imcActual').textContent = actual.imc;
            document.getElementById('clasActual').textContent = actual.clasificacion;
            document.getElementById('actual').style.display = 'block';

            // Se llena la tabla fila por fila.
            registros.forEach(r => {
                const fila = document.createElement('tr');
                fila.innerHTML =
                    '<td>' + r.fechaMedicion + '</td>' +
                    '<td>' + r.peso + '</td>' +
                    '<td>' + r.imc + '</td>' +
                    '<td>' + r.clasificacion + '</td>';
                cuerpo.appendChild(fila);
            });

            // Para la grafica se invierte el orden (de la mas antigua a la
            // mas reciente) para que la linea avance de izquierda a derecha.
            dibujarTendencia(registros.map(r => r.imc).reverse());
        }

        // Dibuja una linea de tendencia sencilla del IMC en el canvas.
        function dibujarTendencia(valores) {
            const canvas = document.getElementById('grafica');
            const ctx = canvas.getContext('2d');
            const w = canvas.width, h = canvas.height, pad = 20;

            if (valores.length < 2) { return; } // Se necesitan al menos 2 puntos.

            const min = Math.min(...valores) - 1;
            const max = Math.max(...valores) + 1;
            const paso = (w - pad * 2) / (valores.length - 1);

            ctx.strokeStyle = '#2C7A4B';
            ctx.lineWidth = 2;
            ctx.beginPath();
            valores.forEach((v, i) => {
                const x = pad + i * paso;
                const y = h - pad - ((v - min) / (max - min)) * (h - pad * 2);
                if (i === 0) { ctx.moveTo(x, y); } else { ctx.lineTo(x, y); }
            });
            ctx.stroke();

            // Puntos sobre cada medicion.
            ctx.fillStyle = '#1F5A37';
            valores.forEach((v, i) => {
                const x = pad + i * paso;
                const y = h - pad - ((v - min) / (max - min)) * (h - pad * 2);
                ctx.beginPath();
                ctx.arc(x, y, 3, 0, Math.PI * 2);
                ctx.fill();
            });
        }
    </script>
</body>
</html>
