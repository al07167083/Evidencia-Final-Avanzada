package mx.tecmilenio.imc.rest;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Representa un control de hipermedia (HATEOAS) dentro de una respuesta REST.
 *
 * Cada enlace indica una relacion ("rel"), la URL a la que apunta ("href") y
 * el metodo HTTP con el que se debe consumir. Gracias a estos enlaces, el
 * cliente puede descubrir que acciones tiene disponibles sin tener las rutas
 * "hardcodeadas".
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Enlace {

    private String rel;     // relacion: self, coleccion, etc.
    private String href;    // URL del recurso
    private String metodo;  // metodo HTTP: GET, POST, ...

    public Enlace() {
    }

    public Enlace(String rel, String href, String metodo) {
        this.rel = rel;
        this.href = href;
        this.metodo = metodo;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
}
