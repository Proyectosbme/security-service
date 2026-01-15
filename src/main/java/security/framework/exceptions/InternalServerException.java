package security.framework.exceptions;

/**
 * Excepción personalizada para errores internos del servidor (HTTP 500)
 */
public class InternalServerException extends RuntimeException {

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
