package mx.tecmilenio.imc.controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import mx.tecmilenio.imc.modelo.CalculadoraIMC;
import mx.tecmilenio.imc.modelo.RegistroIMC;
import mx.tecmilenio.imc.modelo.RegistroIMCDAO;

/**
 * Controlador que recibe la masa corporal (calcular.jsp), calcula el IMC con
 * ayuda del modelo, guarda la medicion y muestra el resultado.
 *
 * Reglas exigidas por la evidencia:
 *   - El usuario debe tener sesion iniciada; si no, no se permite calcular.
 *   - La masa corporal no puede ser 0 ni negativa.
 *
 * Pertenece a la capa CONTROLADOR de MVC.
 */
@WebServlet(name = "IMCServlet", urlPatterns = {"/calcular"})
public class IMCServlet extends HttpServlet {

    private final CalculadoraIMC calculadora = new CalculadoraIMC();
    private final RegistroIMCDAO registroDAO = new RegistroIMCDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);

        // 1. Control de acceso: sin sesion no se puede calcular.
        if (sesion == null || sesion.getAttribute("idUsuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=sesion");
            return;
        }

        int idUsuario = (int) sesion.getAttribute("idUsuario");
        double estatura = (double) sesion.getAttribute("estatura");

        try {
            double peso = Double.parseDouble(request.getParameter("peso"));

            // 2. Validacion de la masa corporal.
            if (peso <= 0) {
                request.setAttribute("error",
                        "La masa corporal debe ser mayor a cero.");
                request.getRequestDispatcher("/calcular.jsp").forward(request, response);
                return;
            }

            // 3. Se delega el calculo y la clasificacion al modelo.
            double imc = calculadora.calcular(peso, estatura);
            String clasificacion = calculadora.clasificar(imc);

            // 4. Se guarda la medicion en la base de datos.
            RegistroIMC registro = new RegistroIMC(idUsuario, peso, imc, clasificacion);
            registroDAO.guardar(registro);

            // 5. Se envian los resultados a la vista.
            request.setAttribute("imc", imc);
            request.setAttribute("clasificacion", clasificacion);
            request.setAttribute("peso", peso);
            request.getRequestDispatcher("/calcular.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Ingresa un valor numerico valido para la masa.");
            request.getRequestDispatcher("/calcular.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al guardar la medicion", e);
        }
    }
}
