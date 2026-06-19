package mx.tecmilenio.imc.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de las operaciones sobre la tabla "registro_imc": guardar una
 * nueva medicion y consultar el historico de un usuario.
 *
 * El metodo de consulta es el que alimenta al servicio REST del historico.
 * Pertenece a la capa MODELO.
 */
public class RegistroIMCDAO {

    private final ConexionBD conexionBD = new ConexionBD();

    /**
     * Guarda una medicion de IMC en la base de datos.
     *
     * @param registro medicion a guardar (peso, imc y usuario)
     */
    public void guardar(RegistroIMC registro) throws SQLException {
        // La fecha se toma en el servidor con NOW() para dejar constancia
        // exacta del momento en que se hizo la medicion.
        String sql = "INSERT INTO registro_imc (id_usuario, peso, imc, fecha_medicion) "
                + "VALUES (?, ?, ?, NOW())";
        try (Connection con = conexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, registro.getIdUsuario());
            ps.setDouble(2, registro.getPeso());
            ps.setDouble(3, registro.getImc());
            ps.executeUpdate();
        }
    }

    /**
     * Consulta el historico completo de mediciones de un usuario, de la mas
     * reciente a la mas antigua.
     *
     * La clasificacion no se guarda en la base de datos: se vuelve a calcular
     * aqui con la CalculadoraIMC a partir del IMC almacenado, para mantener
     * una sola fuente de verdad sobre las categorias de la OMS.
     *
     * @param idUsuario id del usuario dueno de las mediciones
     * @return lista de mediciones (puede venir vacia)
     */
    public List<RegistroIMC> obtenerHistorico(int idUsuario) throws SQLException {
        List<RegistroIMC> historico = new ArrayList<>();
        CalculadoraIMC calculadora = new CalculadoraIMC();

        String sql = "SELECT id_registro, id_usuario, peso, imc, fecha_medicion "
                + "FROM registro_imc WHERE id_usuario = ? "
                + "ORDER BY fecha_medicion DESC";
        try (Connection con = conexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RegistroIMC r = new RegistroIMC();
                    r.setIdRegistro(rs.getInt("id_registro"));
                    r.setIdUsuario(rs.getInt("id_usuario"));
                    r.setPeso(rs.getDouble("peso"));
                    double imc = rs.getDouble("imc");
                    r.setImc(imc);
                    r.setClasificacion(calculadora.clasificar(imc));
                    r.setFechaMedicion(rs.getString("fecha_medicion"));
                    historico.add(r);
                }
            }
        }
        return historico;
    }
}
