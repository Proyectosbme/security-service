package security.framework.exceptions;

/**
 * Excepción personalizada para recursos no encontrados (HTTP 404)
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
