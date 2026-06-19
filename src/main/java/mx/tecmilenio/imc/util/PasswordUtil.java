package mx.tecmilenio.imc.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utilidad para el manejo seguro de contrasenas.
 *
 * En la base de datos nunca se guarda la contrasena en texto plano: se
 * almacena el hash SHA-256 combinado con un "salt" aleatorio. El salt evita
 * que dos usuarios con la misma contrasena terminen con el mismo hash y
 * dificulta los ataques por diccionario.
 *
 * El valor persistido tiene el formato  salt:hash  (ambos en Base64).
 */
public final class PasswordUtil {

    /** Constructor privado: es una clase de utilidad, no se instancia. */
    private PasswordUtil() {
    }

    /**
     * Genera el valor cifrado que se guardara en la base de datos.
     *
     * @param passwordPlano contrasena escrita por el usuario
     * @return cadena con formato salt:hash lista para persistir
     */
    public static String cifrar(String passwordPlano) {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        String hash = aplicarHash(passwordPlano, salt);
        return Base64.getEncoder().encodeToString(salt) + ":" + hash;
    }

    /**
     * Verifica que una contrasena en texto plano corresponda al valor
     * cifrado que se tiene almacenado.
     *
     * @param passwordPlano contrasena capturada en el login
     * @param valorAlmacenado cadena salt:hash guardada en la base de datos
     * @return true si coinciden, false en caso contrario
     */
    public static boolean verificar(String passwordPlano, String valorAlmacenado) {
        if (valorAlmacenado == null || !valorAlmacenado.contains(":")) {
            return false;
        }
        String[] partes = valorAlmacenado.split(":");
        byte[] salt = Base64.getDecoder().decode(partes[0]);
        String hashEsperado = partes[1];
        String hashCalculado = aplicarHash(passwordPlano, salt);
        return hashEsperado.equals(hashCalculado);
    }

    /** Aplica SHA-256 sobre la contrasena mas el salt y devuelve Base64. */
    private static String aplicarHash(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            // SHA-256 siempre esta disponible en la JVM; si fallara es un
            // error irrecuperable de configuracion del entorno.
            throw new IllegalStateException("Algoritmo SHA-256 no disponible", e);
        }
    }
}
