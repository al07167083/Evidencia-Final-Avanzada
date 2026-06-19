package mx.tecmilenio.imc.modelo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Representa una medicion individual del IMC de un usuario.
 *
 * Cada vez que la persona calcula su IMC se crea un RegistroIMC que se guarda
 * en la base de datos junto con la fecha, de modo que despues pueda revisar su
 * historico. Un Usuario puede tener muchos RegistroIMC (relacion uno a muchos).
 *
 * Pertenece a la capa MODELO. Se anota con @XmlRootElement para poder
 * exponerlo tanto en XML como en JSON desde el servicio REST.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RegistroIMC {

    private int idRegistro;
    private int idUsuario;
    private double peso;              // masa corporal en kilogramos
    private double imc;               // valor del IMC calculado
    private String clasificacion;     // categoria segun la OMS
    private String fechaMedicion;     // fecha y hora en formato legible

    /** Constructor vacio requerido por JAXB / JSON-B. */
    public RegistroIMC() {
    }

    /** Constructor usado al momento de guardar una nueva medicion. */
    public RegistroIMC(int idUsuario, double peso, double imc, String clasificacion) {
        this.idUsuario = idUsuario;
        this.peso = peso;
        this.imc = imc;
        this.clasificacion = clasificacion;
    }

    // -------- Metodos de acceso (getters y setters) --------

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getFechaMedicion() {
        return fechaMedicion;
    }

    public void setFechaMedicion(String fechaMedicion) {
        this.fechaMedicion = fechaMedicion;
    }
}
