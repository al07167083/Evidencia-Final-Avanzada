package mx.tecmilenio.imc.modelo;

/**
 * Contiene la logica de negocio para el calculo del Indice de Masa Corporal.
 *
 * Se mantiene separada de los servlets y de los DAO para respetar la
 * arquitectura MVC: aqui vive UNICAMENTE la regla de calculo y la
 * clasificacion, sin saber nada de HTTP ni de la base de datos.
 *
 * Formula:  IMC = peso (kg) / estatura (m)^2
 * Clasificacion segun la Organizacion Mundial de la Salud (OMS).
 */
public class CalculadoraIMC {

    /**
     * Calcula el IMC redondeado a dos decimales.
     *
     * @param peso     masa corporal en kilogramos (debe ser mayor a 0)
     * @param estatura estatura en metros (debe ser mayor a 0)
     * @return el valor del IMC con dos decimales
     */
    public double calcular(double peso, double estatura) {
        if (peso <= 0 || estatura <= 0) {
            // Blindaje de la logica: aunque el controlador ya valida, la
            // clase de negocio no debe confiar ciegamente en su entrada.
            throw new IllegalArgumentException("Peso y estatura deben ser mayores a cero");
        }
        double imc = peso / (estatura * estatura);
        // Se redondea a dos decimales para presentar un valor limpio.
        return Math.round(imc * 100.0) / 100.0;
    }

    /**
     * Devuelve la categoria a la que pertenece un IMC segun la OMS.
     *
     * @param imc valor previamente calculado
     * @return "Bajo peso", "Normal", "Sobrepeso" u "Obesidad"
     */
    public String clasificar(double imc) {
        if (imc < 18.5) {
            return "Bajo peso";
        } else if (imc < 25.0) {
            return "Normal";
        } else if (imc < 30.0) {
            return "Sobrepeso";
        } else {
            return "Obesidad";
        }
    }
}
