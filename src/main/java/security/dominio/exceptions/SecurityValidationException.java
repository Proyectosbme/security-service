package security.dominio.exceptions;

/**
 * Excepción de Dominio: PersonaValidationException
 *
 * Se lanza cuando una Persona incumple las reglas de validación del security.dominio.
 *
 * Ejemplos:
 * - Nombre vacío
 * - Edad fuera de rango (0-150)
 * - Sexo nulo
 *
 * ¿Por qué es una excepción de DOMINIO?
 * - Las validaciones son reglas de negocio
 * - El security.dominio debe garantizar que una Persona siempre sea válida
 * - Se lanza desde persona.validar()
 */
public class SecurityValidationException extends RuntimeException{
    public SecurityValidationException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor con campo y mensaje detallado.
     * Ejemplo: "Validación fallida en 'edad': Debe estar entre 0 y 150"
     */
    public SecurityValidationException(String campo, String mensaje) {
        super("Validación fallida en '" + campo + "': " + mensaje);
    }
}
