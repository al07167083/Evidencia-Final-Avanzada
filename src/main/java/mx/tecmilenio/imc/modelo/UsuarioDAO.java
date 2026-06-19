package mx.tecmilenio.imc.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mx.tecmilenio.imc.util.PasswordUtil;

/**
 * DAO (Data Access Object) encargado de las operaciones sobre la tabla
 * "usuario": registrar un nuevo usuario y validar el inicio de sesion.
 *
 * Concentra todo el SQL relacionado con usuarios para que ni los servlets ni
 * el servicio REST tengan que conocer los detalles de la base de datos.
 * Pertenece a la capa MODELO.
 */
public class UsuarioDAO {

    private final ConexionBD conexionBD = new ConexionBD();

    /**
     * Indica si un nombre de usuario ya existe (para no permitir duplicados).
     *
     * @param nombreUsuario nombre de usuario a verificar
     * @return true si ya esta registrado
     */
    public boolean existeUsuario(String nombreUsuario) throws SQLException {
        String sql = "SELECT 1 FROM usuario WHERE nombre_usuario = ?";
        try (Connection con = conexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Inserta un nuevo usuario en la base de datos. La contrasena se cifra
     * antes de guardarse.
     *
     * @param usuario datos capturados en el registro
     * @return el id generado para el nuevo usuario
     */
    public int registrar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario "
                + "(nombre_completo, nombre_usuario, contrasena, edad, sexo, estatura) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = conexionBD.obtenerConexion();
             PreparedStatement ps =
                     con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getNombreCompleto());
            ps.setString(2, usuario.getNombreUsuario());
            // Se guarda el hash, nunca la contrasena en texto plano.
            ps.setString(3, PasswordUtil.cifrar(usuario.getContrasena()));
            ps.setInt(4, usuario.getEdad());
            ps.setString(5, String.valueOf(usuario.getSexo()));
            ps.setDouble(6, usuario.getEstatura());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    /**
     * Valida las credenciales de inicio de sesion.
     *
     * @param nombreUsuario usuario capturado
     * @param password      contrasena en texto plano capturada
     * @return el Usuario si las credenciales son correctas; null si no
     */
    public Usuario validarLogin(String nombreUsuario, String password) throws SQLException {
        String sql = "SELECT id_usuario, nombre_completo, nombre_usuario, "
                + "contrasena, edad, sexo, estatura "
                + "FROM usuario WHERE nombre_usuario = ?";
        try (Connection con = conexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashGuardado = rs.getString("contrasena");
                    // Se compara el hash almacenado contra la contrasena dada.
                    if (PasswordUtil.verificar(password, hashGuardado)) {
                        Usuario u = new Usuario();
                        u.setIdUsuario(rs.getInt("id_usuario"));
                        u.setNombreCompleto(rs.getString("nombre_completo"));
                        u.setNombreUsuario(rs.getString("nombre_usuario"));
                        u.setEdad(rs.getInt("edad"));
                        u.setSexo(rs.getString("sexo").charAt(0));
                        u.setEstatura(rs.getDouble("estatura"));
                        return u;
                    }
                }
            }
        }
        return null; // Credenciales invalidas
    }
}
