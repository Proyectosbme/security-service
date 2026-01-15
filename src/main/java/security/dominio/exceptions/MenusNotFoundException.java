package security.dominio.exceptions;


/**
 * Excepción de Dominio: PersonaNotFoundException
 *
 * Se lanza cuando se intenta acceder a una persona que no existe.
 *
 * ¿Por qué es una excepción de DOMINIO y no técnica?
 * - Es un concepto del negocio: "La persona no existe"
 * - No es un error de BD (SQLException) o HTTP (HttpException)
 * - Se lanza desde los Use Cases
 * - El GlobalExceptionHandler la convierte en HTTP 404
 *
 * ¿Por qué extends RuntimeException?
 * - No requiere try-catch obligatorio
 * - Es una excepción de negocio, no técnica
 */
public class MenusNotFoundException extends RuntimeException{
    public MenusNotFoundException(String mensaje) {
        super(mensaje);
    }

    public MenusNotFoundException(Long id) {
        super("Menu con ID " + id + " no encontrada");
    }
}
