package security.dominio.exceptions;

/**
 * Excepción de Dominio: SecurityValidationException
 *
 * Se lanza cuando una entidad de seguridad incumple las reglas de validación del dominio.
 * 
 * Esta excepción representa errores de negocio, NO errores técnicos.
 * 
 * Ejemplos de cuándo se lanza:
 * - Menu: Nombre vacío, jerarquía negativa, orden negativo
 * - Modulo: Nombre vacío
 * - Perfil: Validaciones específicas del perfil
 * - Pantalla: URL inválida, módulo no asignado
 *
 * ¿Por qué es una excepción de DOMINIO?
 * - Las validaciones son reglas de negocio, no errores técnicos
 * - El dominio debe garantizar que las entidades siempre sean válidas
 * - Se lanza desde métodos validar() de las entidades
 * - Se maneja en los casos de uso (UseCase)
 *
 * ¿Por qué extends RuntimeException?
 * - No requiere try-catch obligatorio
 * - Es más compatible con código asincrónico y reactive
 * - Las validaciones de dominio son excepciones esperadas en el flujo de negocio
 * 
 * Manejo recomendado:
 * Se recomienda capturar esta excepción en los controladores o en un 
 * GlobalExceptionHandler para convertirla en HTTP 400 (Bad Request).
 * 
 * @see menu.validar()
 * @see modulo.validar()
 */
public class SecurityValidationException extends RuntimeException{
    /**
     * Constructor simple con mensaje genérico.
     * 
     * @param mensaje Mensaje descriptivo del error de validación
     */
    public SecurityValidationException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor con campo y mensaje detallado.
     * Proporciona contexto sobre qué campo falló la validación.
     * 
     * Ejemplo: "Validación fallida en 'nombre': Debe estar entre 1 y 100 caracteres"
     * 
     * @param campo   Nombre del campo que falló la validación
     * @param mensaje Descripción del error específico
     */
    public SecurityValidationException(String campo, String mensaje) {
        super("Validación fallida en '" + campo + "': " + mensaje);
    }
}
