package security.framework.input.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import security.aplication.port.input.ModuloInputPort;
import security.dominio.entidades.Modulo;
import security.framework.input.dto.ModuloRequestDTO;
import security.framework.input.dto.ModuloResponseDTO;
import security.framework.input.mapper.ModuloInputMapper;

/**
 * Controlador REST: ModuloController
 * 
 * Expone endpoints HTTP para operaciones CRUD de módulos.
 * Punto de entrada de la aplicación para gestión de módulos (REST API Adapter).
 * 
 * Responsabilidad:
 * - Recibir peticiones HTTP
 * - Validar DTOs de entrada
 * - Delegar lógica a puertos de entrada (ModuloInputPort)
 * - Retornar respuestas HTTP estructuradas
 * 
 * Patrón: REST Controller / Adapter de Entrada
 * Framework: JAX-RS (Jakarta REST)
 * 
 * Flujo Completo:
 * HTTP Request → @Path /modulo → ModuloController → ModuloInputPort (UseCase) → Respuesta HTTP
 * 
 * Anotaciones:
 * - @Path("/modulo"): Prefijo base para todos los endpoints
 * - @POST: Método HTTP soportado
 * - @Transactional: Demarca transacciones de BD
 * 
 * Endpoints:
 * - POST /modulo: Crear nuevo módulo (201 Created)
 * 
 * Nota: Actualmente solo implementa operación CREATE.
 * Otras operaciones (READ, UPDATE, DELETE, SEARCH) no están implementadas aún.
 */
@Path("/modulo")
public class ModuloController {
    
    /** Mapper para conversiones ModuloRequestDTO ↔ ModuloResponseDTO ↔ Modulo */
    private final ModuloInputMapper moduloInputMapper;
    
    /** Puerto de entrada para casos de uso de módulos */
    private final ModuloInputPort moduloInputPort;

    /**
     * Constructor con inyección de dependencias.
     * CDI automáticamente inyecta mapper y puerto.
     * 
     * @param moduloInputMapper Mapper de entrada (inyectado por CDI)
     * @param moduloInputPort Puerto ModuloInputPort (inyectado por CDI)
     */
    public ModuloController(ModuloInputMapper moduloInputMapper, ModuloInputPort moduloInputPort) {
        this.moduloInputMapper = moduloInputMapper;
        this.moduloInputPort = moduloInputPort;
    }

    /**
     * Crea un nuevo módulo.
     * 
     * Flujo:
     * 1. Recibe ModuloRequestDTO (JSON) desde cliente HTTP
     * 2. Convierte a Modulo (dominio) con ModuloInputMapper
     * 3. Delega a moduloInputPort.crear() para persistencia
     * 4. Convierte resultado a ModuloResponseDTO
     * 5. Retorna HTTP 201 Created con el módulo creado
     * 
     * Transacción: @Transactional asegura commit/rollback de BD
     * 
     * @param dto ModuloRequestDTO con datos de entrada
     * @return Response HTTP 201 Created con ModuloResponseDTO
     * 
     * Ejemplo Request:
     * POST /modulo
     * {
     *   "nombre": "Administración"
     * }
     * 
     * Ejemplo Response (201):
     * {
     *   "id": 1,
     *   "nombre": "Administración"
     * }
     */
    @POST
    @Transactional
    public Response crear(ModuloRequestDTO dto) {
        // 1. Mapear DTO request → entidad dominio
        Modulo modulo = moduloInputMapper.toDomain(dto);
        
        // 2. Ejecutar lógica de caso de uso
        Modulo moduloGuardado = moduloInputPort.crear(modulo);
        
        // 3. Mapear entidad dominio → DTO respuesta
        ModuloResponseDTO response = moduloInputMapper.toResponseDto(moduloGuardado);
        
        // 4. Devolver 201 Created
        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }
}
