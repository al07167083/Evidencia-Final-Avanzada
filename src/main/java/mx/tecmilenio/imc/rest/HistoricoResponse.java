package mx.tecmilenio.imc.rest;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import mx.tecmilenio.imc.modelo.RegistroIMC;

/**
 * Envoltorio de la respuesta REST del historico.
 *
 * En lugar de devolver una lista "pelona", se envuelve en este objeto para
 * poder incluir metadatos (el usuario y el total de mediciones) y, sobre todo,
 * los controles de hipermedia (HATEOAS). Se serializa igual en XML y en JSON.
 */
@XmlRootElement(name = "historico")
@XmlAccessorType(XmlAccessType.FIELD)
public class HistoricoResponse {

    private int idUsuario;
    private int total;

    // El wrapper permite que en XML se genere <registros><registro/>...</registros>
    @XmlElementWrapper(name = "registros")
    @XmlElement(name = "registro")
    private List<RegistroIMC> registros = new ArrayList<>();

    @XmlElementWrapper(name = "enlaces")
    @XmlElement(name = "enlace")
    private List<Enlace> enlaces = new ArrayList<>();

    public HistoricoResponse() {
    }

    public HistoricoResponse(int idUsuario, List<RegistroIMC> registros) {
        this.idUsuario = idUsuario;
        this.registros = registros;
        this.total = registros.size();
    }

    /** Agrega un control de hipermedia a la respuesta. */
    public void agregarEnlace(Enlace enlace) {
        this.enlaces.add(enlace);
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RegistroIMC> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroIMC> registros) {
        this.registros = registros;
        this.total = registros.size();
    }

    public List<Enlace> getEnlaces() {
        return enlaces;
    }

    public void setEnlaces(List<Enlace> enlaces) {
        this.enlaces = enlaces;
    }
}
