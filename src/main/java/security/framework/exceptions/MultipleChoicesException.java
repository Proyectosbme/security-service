package security.framework.exceptions;

/**
 * Excepción personalizada para múltiples opciones disponibles (HTTP 300)
 */
public class MultipleChoicesException extends RuntimeException {

    public MultipleChoicesException(String message) {
        super(message);
    }

    public MultipleChoicesException(String message, Throwable cause) {
        super(message, cause);
    }
}
