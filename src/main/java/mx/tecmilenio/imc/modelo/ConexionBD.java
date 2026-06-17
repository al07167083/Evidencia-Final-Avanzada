package mx.tecmilenio.imc.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de apoyo que entrega la conexion a la base de datos MySQL.
 *
 * Se centraliza aqui para no repetir la cadena de conexion en cada DAO. Si en
 * el futuro cambian el host, el usuario o la contrasena de la base de datos,
 * basta con modificar este unico lugar.
 *
 * Pertenece a la capa MODELO (acceso a datos).
 */
public class ConexionBD {

    // Parametros de conexion. Ajustar segun el entorno donde se despliegue.
    private static final String URL =
            "jdbc:mysql://localhost:3306/imc_db?useSSL=false&serverTimezone=America/Mexico_City";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "root";

    /**
     * Abre y devuelve una nueva conexion a MySQL.
     *
     * @return conexion lista para ejecutar sentencias SQL
     * @throws SQLException si no es posible establecer la conexion
     */
    public Connection obtenerConexion() throws SQLException {
        try {
            // A partir de JDBC 4 el driver se registra solo, pero se fuerza la
            // carga de la clase para evitar problemas en algunos servidores.
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontro el driver de MySQL", e);
        }
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
