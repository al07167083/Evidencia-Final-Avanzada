package mx.tecmilenio.imc.rest;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Representa un error devuelto por el servicio REST.
 *
 * Al ser una clase anotada con @XmlRootElement, el mismo objeto de error se
 * puede serializar tanto en XML como en JSON, de modo que las excepciones se
 * manejan de forma consistente en ambas representaciones.
 */
@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorResponse {

    private int codigo;      // codigo HTTP asociado
    private String mensaje;  // descripcion legible del error

    public ErrorResponse() {
    }

    public ErrorResponse(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
