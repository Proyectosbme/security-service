package security.dominio.exceptions;

/**
 * Excepción de Dominio: SecurityNotFoundException
 *
 * Se lanza cuando se intenta acceder a una entidad de seguridad que no existe en el sistema.
 *
 * Ejemplos de cuándo se lanza:
 * - Buscar un Menú con ID que no existe
 * - Buscar un Perfil inexistente
 * - Buscar un Módulo que fue eliminado
 * - Buscar una Pantalla que no está registrada
 *
 * ¿Por qué es una excepción de DOMINIO y no técnica?
 * - Es un concepto del negocio: "La entidad no existe"
 * - NO es un error de base de datos (SQLException)
 * - NO es un error de HTTP (HttpException) - aunque se convierte a 404 HTTP
 * - Se lanza desde los casos de uso (UseCase)
 * - Representa un estado esperado en el flujo de negocio
 *
 * ¿Por qué extends RuntimeException?
 * - No requiere try-catch obligatorio
 * - Es una excepción de negocio, no técnica
 * - Proporciona mejor compatibilidad con código asincrónico
 * 
 * Manejo recomendado:
 * Se recomienda capturar esta excepción en un GlobalExceptionHandler
 * para convertirla en HTTP 404 (Not Found).
 * 
 * Ejemplo de uso:
 * <pre>
 * Menu menu = menuRepository.findById(id)
 *     .orElseThrow(() -> new SecurityNotFoundException(id, "Menú"));
 * </pre>
 * 
 * @see MenuInputPort
 * @see ModuloInputPort
 */
public class SecurityNotFoundException extends RuntimeException{
    /**
     * Constructor simple con mensaje descriptivo.
     * 
     * @param mensaje Mensaje descriptivo de la entidad no encontrada
     */
    public SecurityNotFoundException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor con ID y nombre de la entidad.
     * Proporciona contexto específico sobre qué se buscaba.
     * 
     * Ejemplo generado: "Menú con ID 123 no encontrada"
     * 
     * @param id               Identificador de la entidad buscada
     * @param nombreDominio    Nombre descriptivo de la entidad (ej: "Menú", "Perfil", "Módulo")
     */
    public SecurityNotFoundException(Long id,String nombreDominio) {
        super(nombreDominio + " con ID " + id + " no encontrada");
    }
}
