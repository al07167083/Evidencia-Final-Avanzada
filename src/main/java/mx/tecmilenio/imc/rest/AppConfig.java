package mx.tecmilenio.imc.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Punto de arranque de los servicios REST (JAX-RS).
 *
 * La anotacion @ApplicationPath define el prefijo comun de todos los recursos
 * REST de la aplicacion. Con esto, el historico queda disponible bajo:
 *   /imc-web/api/historico
 *
 * No requiere codigo adicional: JAX-RS descubre por si mismo las clases
 * anotadas con @Path dentro del proyecto.
 */
@ApplicationPath("/api")
public class AppConfig extends Application {
    // Sin configuracion manual: el escaneo automatico se encarga del registro.
}
