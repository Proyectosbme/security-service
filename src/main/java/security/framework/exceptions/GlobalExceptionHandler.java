package security.framework.exceptions;


import jakarta.annotation.Priority;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import security.dominio.exceptions.SecurityNotFoundException;
import security.dominio.exceptions.SecurityValidationException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador Global de Excepciones: GlobalExceptionHandler
 * 
 * Mapea excepciones de dominio a respuestas HTTP con ErrorResponseDTO.
 * Estrategia: Convertir errores de aplicación en respuestas HTTP estructuradas.
 * 
 * Patrón: ExceptionMapper (JAX-RS)
 * Responsabilidad: Transformar excepciones en ResponseEntity con datos estructurados
 * 
 * Flujo:
 * Excepción en Controller/Service → GlobalExceptionHandler → ErrorResponseDTO → HTTP Response
 * 
 * Mapeos:
 * - SecurityValidationException → 400 Bad Request
 * - SecurityNotFoundException → 404 Not Found
 * - ConstraintViolationException → 400 Bad Request (con detalles)
 * - Exception (genérica) → 500 Internal Server Error
 */
@Provider
@Priority(1)
public class GlobalExceptionHandler {
    /**
     * Mapper: SecurityValidationException → HTTP 400
     * 
     * Maneja excepciones de validación de dominio.
     * Se lanza cuando la lógica de negocio detecta datos inválidos.
     * Ejemplo: Menu con nombre vacío, Estado inválido, etc.
     * 
     * Respuesta: HTTP 400 Bad Request + ErrorResponseDTO
     */
    @Provider
    public static class SecurityValidationExceptionMapper implements ExceptionMapper<SecurityValidationException> {

        /**
         * Convierte excepción de validación a respuesta HTTP 400.
         * 
         * @param exception Excepción de validación del dominio
         * @return Response HTTP 400 con ErrorResponseDTO
         */
        @Override
        public Response toResponse(SecurityValidationException exception) {
            ErrorResponseDTO error = new ErrorResponseDTO(
                    400,
                    "Validation Failed",
                    exception.getMessage(),
                    "/api/security"
            );

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(error)
                    .build();
        }
    }

    /**
     * Mapper: SecurityNotFoundException → HTTP 404
     * 
     * Maneja excepciones cuando no existe una entidad.
     * Se lanza cuando buscar por ID no encuentra el registro.
     * Ejemplo: Menu(id=999) no existe en BD
     * 
     * Respuesta: HTTP 404 Not Found + ErrorResponseDTO
     */
    @Provider
    public static class SecurityNotFoundExceptionMapper implements ExceptionMapper<SecurityNotFoundException> {

        /**
         * Convierte excepción de no encontrado a respuesta HTTP 404.
         * 
         * @param exception Excepción cuando no existe la entidad
         * @return Response HTTP 404 con ErrorResponseDTO
         */
        @Override
        public Response toResponse(SecurityNotFoundException exception) {
            ErrorResponseDTO error = new ErrorResponseDTO(
                    404,
                    "Not Found",
                    exception.getMessage(),
                    "/api/security"
            );

            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(error)
                    .build();
        }
    }

    /**
     * Mapper: ConstraintViolationException → HTTP 400
     * 
     * Maneja excepciones de validación de DTOs.
     * Se lanza cuando fallan anotaciones Jakarta Validation.
     * Ejemplos: @NotBlank, @NotNull, @Min, @Max, @Email, @Size, etc.
     * 
     * Características:
     * - Recoge TODOS los errores de validación
     * - Incluye un detalle por cada campo inválido
     * - Mensaje describe qué validación falló
     * 
     * Respuesta: HTTP 400 Bad Request + ErrorResponseDTO con lista de detalles
     */
    @Provider
    public static class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

        /**
         * Convierte excepciones de validación de DTO a respuesta HTTP 400.
         * Extrae cada violación de restricción como detalle.
         * 
         * @param exception Excepción de violación de restricción
         * @return Response HTTP 400 con ErrorResponseDTO y lista de detalles
         * 
         * Ejemplo de detalles:
         * - "nombre: no puede estar vacío"
         * - "jerarquia: debe ser mayor que 0"
         * - "estado: valor inválido"
         */
        @Override
        public Response toResponse(ConstraintViolationException exception) {
            // Extrae todos los mensajes de cada violación
            List<String> detalles = exception.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            ErrorResponseDTO error = new ErrorResponseDTO(
                    400,
                    "Validation Failed",
                    "Los datos enviados no cumplen con las validaciones requeridas",
                    "/api/security",
                    detalles
            );

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(error)
                    .build();
        }
    }

    /**
     * Mapper: Exception genérica → HTTP 500
     * 
     * Captura TODAS las excepciones no manejadas previamente.
     * Es la última red de seguridad: siempre devuelve una respuesta estructurada.
     * 
     * Responsabilidad:
     * - Evitar exponer stack traces al cliente
     * - Registrar el error para debugging
     * - Devolver respuesta consistente
     * 
     * Respuesta: HTTP 500 Internal Server Error + ErrorResponseDTO
     */
    @Provider
    public static class GenericExceptionMapper implements ExceptionMapper<Exception> {

        /**
         * Convierte cualquier excepción no manejada a respuesta HTTP 500.
         * Registra el error completo en servidor para investigación.
         * 
         * @param exception La excepción inesperada
         * @return Response HTTP 500 con ErrorResponseDTO
         */
        @Override
        public Response toResponse(Exception exception) {
            ErrorResponseDTO error = new ErrorResponseDTO(
                    500,
                    "Internal Server Error",
                    "Error interno del servidor: " + exception.getMessage(),
                    "/api/security"
            );

            // Log para debugging - imprime stack trace en logs del servidor
            exception.printStackTrace();

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error)
                    .build();
        }
    }
}
