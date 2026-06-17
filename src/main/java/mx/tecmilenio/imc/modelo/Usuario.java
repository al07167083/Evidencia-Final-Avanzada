package mx.tecmilenio.imc.modelo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Representa a una persona registrada en la aplicacion.
 *
 * Contiene los datos que se capturan una sola vez durante el registro. La
 * contrasena se guarda en su forma cifrada (nunca en texto plano) por lo que
 * este atributo se marca para que NO se exponga en las representaciones XML
 * ni JSON del servicio REST.
 *
 * Pertenece a la capa MODELO dentro de la arquitectura MVC.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Usuario {

    private int idUsuario;
    private String nombreCompleto;
    private String nombreUsuario;

    // La contrasena cifrada es informacion sensible: se excluye de la
    // serializacion transient para que jamas viaje en las respuestas REST.
    private transient String contrasena;

    private int edad;
    private char sexo;        // 'M' o 'F'
    private double estatura;  // en metros

    /** Constructor vacio requerido por JAXB / JSON-B. */
    public Usuario() {
    }

    /** Constructor de conveniencia para el registro de un nuevo usuario. */
    public Usuario(String nombreCompleto, String nombreUsuario, String contrasena,
                   int edad, char sexo, double estatura) {
        this.nombreCompleto = nombreCompleto;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.edad = edad;
        this.sexo = sexo;
        this.estatura = estatura;
    }

    // -------- Metodos de acceso (getters y setters) --------

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }
}
