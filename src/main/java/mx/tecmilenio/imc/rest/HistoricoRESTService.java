package mx.tecmilenio.imc.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.List;
import mx.tecmilenio.imc.modelo.RegistroIMC;
import mx.tecmilenio.imc.modelo.RegistroIMCDAO;

/**
 * Servicio web tipo REST que entrega el historico de mediciones del usuario
 * que tiene la sesion iniciada.
 *
 * Cumple con lo que pide la evidencia: la pantalla de historico consume estos
 * datos desde aqui (no directamente de la base de datos). Ademas:
 *   - Ofrece la informacion en XML y en JSON (@Produces con ambos tipos).
 *   - Incluye controles de hipermedia (HATEOAS) en cada respuesta.
 *   - Usa el metodo HTTP correcto (GET, porque solo consulta).
 *   - Maneja las excepciones devolviendolas en la misma representacion.
 *
 * Pertenece a la capa CONTROLADOR de MVC (expone la logica hacia el cliente).
 */
@Path("/historico")
public class HistoricoRESTService {

    private final RegistroIMCDAO registroDAO = new RegistroIMCDAO();

    /**
     * Devuelve el historico del usuario en sesion.
     *
     * @param request contexto de la peticion, del que se obtiene la sesion
     * @param uriInfo informacion de la URI, usada para construir los enlaces
     * @return 200 con el historico, 401 si no hay sesion, 500 si falla la BD
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response obtenerHistorico(@Context HttpServletRequest request,
                                     @Context UriInfo uriInfo) {

        HttpSession sesion = request.getSession(false);

        // Control de acceso: sin sesion no se entrega informacion. El error
        // se devuelve como objeto, por lo que sale igual en XML o en JSON.
        if (sesion == null || sesion.getAttribute("idUsuario") == null) {
            ErrorResponse error = new ErrorResponse(401,
                    "Debes iniciar sesion para consultar tu historico.");
            return Response.status(Response.Status.UNAUTHORIZED).entity(error).build();
        }

        int idUsuario = (int) sesion.getAttribute("idUsuario");

        try {
            List<RegistroIMC> registros = registroDAO.obtenerHistorico(idUsuario);
            HistoricoResponse respuesta = new HistoricoResponse(idUsuario, registros);

            // Controles de hipermedia (HATEOAS): se le dice al cliente donde
            // esta este mismo recurso y como volver a la pantalla de calculo.
            String base = uriInfo.getBaseUri().toString();
            respuesta.agregarEnlace(new Enlace("self", base + "historico", "GET"));
            respuesta.agregarEnlace(new Enlace("calcular",
                    request.getContextPath() + "/calcular.jsp", "GET"));

            return Response.ok(respuesta).build();

        } catch (SQLException e) {
            // Cualquier fallo de base de datos se traduce a un error 500 que
            // tambien respeta la representacion solicitada (XML o JSON).
            ErrorResponse error = new ErrorResponse(500,
                    "Ocurrio un error al consultar el historico.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error).build();
        }
    }
}
