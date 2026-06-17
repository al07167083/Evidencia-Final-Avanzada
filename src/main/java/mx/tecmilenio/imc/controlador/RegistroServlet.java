package mx.tecmilenio.imc.controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import mx.tecmilenio.imc.modelo.Usuario;
import mx.tecmilenio.imc.modelo.UsuarioDAO;

/**
 * Controlador que recibe el formulario de registro (registro.jsp), valida los
 * datos y, si todo es correcto, manda al modelo a guardar al nuevo usuario.
 *
 * Reglas de validacion exigidas por la evidencia:
 *   - Estatura entre 1.00 m y 2.50 m.
 *   - Edad minima de 15 anios.
 *   - El nombre de usuario no puede estar repetido.
 *
 * Pertenece a la capa CONTROLADOR de MVC.
 */
@WebServlet(name = "RegistroServlet", urlPatterns = {"/registro"})
public class RegistroServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Se recuperan los parametros enviados desde el formulario.
        String nombreCompleto = request.getParameter("nombreCompleto");
        String nombreUsuario = request.getParameter("nombreUsuario");
        String contrasena = request.getParameter("contrasena");
        String sexo = request.getParameter("sexo");

        try {
            // 2. Conversion de los campos numericos con manejo de errores.
            int edad = Integer.parseInt(request.getParameter("edad"));
            double estatura = Double.parseDouble(request.getParameter("estatura"));

            // 3. Validaciones de negocio.
            if (nombreCompleto == null || nombreCompleto.trim().isEmpty()
                    || nombreUsuario == null || nombreUsuario.trim().isEmpty()
                    || contrasena == null || contrasena.trim().isEmpty()
                    || sexo == null || sexo.trim().isEmpty()) {
                reenviarConError(request, response, "Todos los campos son obligatorios.");
                return;
            }
            if (estatura < 1.0 || estatura > 2.5) {
                reenviarConError(request, response,
                        "La estatura debe estar entre 1.00 y 2.50 metros.");
                return;
            }
            if (edad < 15) {
                reenviarConError(request, response,
                        "La edad minima para registrarse es 15 anios.");
                return;
            }
            if (usuarioDAO.existeUsuario(nombreUsuario)) {
                reenviarConError(request, response,
                        "El nombre de usuario ya esta registrado. Elige otro.");
                return;
            }

            // 4. Todo valido: se construye el modelo y se guarda.
            Usuario usuario = new Usuario(nombreCompleto, nombreUsuario, contrasena,
                    edad, sexo.charAt(0), estatura);
            usuarioDAO.registrar(usuario);

            // 5. Se redirige al login con un aviso de exito.
            response.sendRedirect(request.getContextPath()
                    + "/login.jsp?registro=ok");

        } catch (NumberFormatException e) {
            reenviarConError(request, response,
                    "Edad y estatura deben ser valores numericos validos.");
        } catch (SQLException e) {
            throw new ServletException("Error al registrar el usuario", e);
        }
    }

    /** Coloca el mensaje de error en la peticion y regresa al formulario. */
    private void reenviarConError(HttpServletRequest request, HttpServletResponse response,
                                  String mensaje) throws ServletException, IOException {
        request.setAttribute("error", mensaje);
        request.getRequestDispatcher("/registro.jsp").forward(request, response);
    }
}
