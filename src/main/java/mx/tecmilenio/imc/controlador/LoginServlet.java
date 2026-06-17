package mx.tecmilenio.imc.controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import mx.tecmilenio.imc.modelo.Usuario;
import mx.tecmilenio.imc.modelo.UsuarioDAO;

/**
 * Controlador que valida el inicio de sesion (login.jsp).
 *
 * Si las credenciales son correctas, guarda al usuario en la sesion HTTP. Esa
 * sesion es la que despues permite calcular el IMC y consultar el historico:
 * sin ella, esas operaciones quedan bloqueadas (asi lo pide la evidencia).
 *
 * Pertenece a la capa CONTROLADOR de MVC.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombreUsuario = request.getParameter("nombreUsuario");
        String contrasena = request.getParameter("contrasena");

        try {
            Usuario usuario = usuarioDAO.validarLogin(nombreUsuario, contrasena);

            if (usuario != null) {
                // Credenciales correctas: se abre la sesion y se guardan los
                // datos minimos que necesitaran las siguientes pantallas.
                HttpSession sesion = request.getSession();
                sesion.setAttribute("idUsuario", usuario.getIdUsuario());
                sesion.setAttribute("nombreUsuario", usuario.getNombreUsuario());
                sesion.setAttribute("nombreCompleto", usuario.getNombreCompleto());
                sesion.setAttribute("estatura", usuario.getEstatura());

                response.sendRedirect(request.getContextPath() + "/calcular.jsp");
            } else {
                // Credenciales invalidas: se regresa al formulario con aviso.
                request.setAttribute("error", "Usuario o contrasena incorrectos.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Error al validar el inicio de sesion", e);
        }
    }
}
