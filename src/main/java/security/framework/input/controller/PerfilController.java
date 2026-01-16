package security.framework.input.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import security.aplication.port.input.PerfilInputPort;
import security.dominio.entidades.Perfil;
import security.framework.input.dto.PerfilRequestDTO;
import security.framework.input.dto.PerfilResponseDTO;
import security.framework.input.mapper.PerfilInputMapper;

import java.math.BigInteger;

/**
 * Controlador: PerfilController
 * 
 * Responsabilidad: Procesar solicitudes HTTP para operaciones CRUD de perfiles.
 * 
 * Patrón Hexagonal:
 * - Depende de PerfilInputPort (puerto de entrada)
 * - Convierte HTTP (DTOs) a objetos de dominio mediante PerfilInputMapper
 * - Mapea excepciones del dominio a respuestas HTTP apropiadas (manejadas por GlobalExceptionHandler)
 * - Implementa patrón REST: POST (crear), GET (buscar), PUT (actualizar), DELETE (eliminar)
 * 
 * Endpoint Base: /perfil
 * - POST   /perfil              → Crear nuevo perfil
 * - GET    /perfil/{id}         → Buscar perfil por ID
 * - PUT    /perfil/{id}         → Actualizar perfil existente
 * - DELETE /perfil/{id}         → Eliminar perfil
 * 
 * Flujo de Solicitud:
 * HTTP Request → [Validación @Valid] → [PerfilInputMapper] → Dominio → [PerfilInputPort]
 * → Lógica de Negocio → Respuesta → [PerfilInputMapper] → DTO → HTTP Response
 * 
 * Excepciones:
 * - SecurityValidationException: Mapea a 400 Bad Request (por GlobalExceptionHandler)
 * - SecurityNotFoundException: Mapea a 404 Not Found (por GlobalExceptionHandler)
 * 
 * Validación:
 * - @Valid en PerfilRequestDTO valida:
 *   - @NotBlank nombre
 * 
 * Content-Type:
 * - Request: application/json
 * - Response: application/json
 * 
 * HTTP Status:
 * - 201 Created: Perfil creado exitosamente
 * - 200 OK: Perfil encontrado o actualizado
 * - 204 No Content: Perfil eliminado exitosamente
 * - 404 Not Found: Perfil no existe
 * - 400 Bad Request: Datos inválidos
 * - 500 Internal Server Error: Error interno
 * 
 * @author Security Team
 * @version 1.0
 */
@Path("/perfil")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PerfilController {

    private final PerfilInputPort perfilInputPort;
    private final PerfilInputMapper perfilInputMapper;

    /**
     * Constructor con inyección de dependencias.
     * 
     * @param perfilInputPort Puerto de entrada de perfiles (PerfilService)
     * @param perfilInputMapper Mapper para conversiones PerfilRequestDTO ↔ Perfil
     */
    @Inject
    public PerfilController(PerfilInputPort perfilInputPort, 
                           PerfilInputMapper perfilInputMapper) {
        this.perfilInputPort = perfilInputPort;
        this.perfilInputMapper = perfilInputMapper;
    }

    /**
     * Crea nuevo perfil.
     * 
     * Flujo:
     * 1. Recibir PerfilRequestDTO (con validación @Valid)
     * 2. Validar datos (anotaciones en DTO)
     * 3. Convertir DTO a Perfil (dominio)
     * 4. Llamar PerfilInputPort.crear()
     * 5. Convertir Perfil a PerfilResponseDTO
     * 6. Retornar 201 Created con DTO y Location header
     * 
     * @param request PerfilRequestDTO con datos de perfil a crear
     * @return Response con status 201 y PerfilResponseDTO creado
     * @throws SecurityValidationException si datos inválidos (manejada por GlobalExceptionHandler)
     */
    @POST
    @Transactional
    public Response crear(@Valid PerfilRequestDTO request) {
        // 1. Convertir DTO a dominio
        Perfil perfil = perfilInputMapper.toDomain(request);
        
        // 2. Crear perfil mediante puerto de entrada
        Perfil perfilCreado = perfilInputPort.crear(perfil);
        
        // 3. Convertir a DTO de respuesta
        PerfilResponseDTO response = perfilInputMapper.toResponseDto(perfilCreado);
        
        // 4. Retornar 201 Created con Location header
        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .header("Location", "/perfil/" + perfilCreado.getId())
                .build();
    }

    /**
     * Busca perfil por ID.
     * 
     * Flujo:
     * 1. Recibir ID de perfil
     * 2. Llamar PerfilInputPort.buscarPorId(id)
     * 3. Convertir Perfil a PerfilResponseDTO
     * 4. Retornar 200 OK con DTO
     * 
     * @param id ID de perfil a buscar
     * @return Response con status 200 y PerfilResponseDTO encontrado
     * @throws SecurityNotFoundException si perfil no existe (maneja por GlobalExceptionHandler → 404)
     */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") BigInteger id) {
        // 1. Buscar perfil mediante puerto de entrada
        Perfil perfil = perfilInputPort.buscarPorId(id);
        
        // 2. Convertir a DTO de respuesta
        PerfilResponseDTO response = perfilInputMapper.toResponseDto(perfil);
        
        // 3. Retornar 200 OK con DTO
        return Response.ok(response).build();
    }

    /**
     * Actualiza perfil existente.
     * 
     * Flujo:
     * 1. Recibir ID y PerfilRequestDTO (con validación @Valid)
     * 2. Validar datos (anotaciones en DTO)
     * 3. Convertir DTO a Perfil (dominio)
     * 4. Llamar PerfilInputPort.actualizar(id, perfil)
     * 5. Convertir Perfil actualizado a PerfilResponseDTO
     * 6. Retornar 200 OK con DTO
     * 
     * @param id ID del perfil a actualizar
     * @param request PerfilRequestDTO con datos actualizados
     * @return Response con status 200 y PerfilResponseDTO actualizado
     * @throws SecurityNotFoundException si perfil no existe (manejada por GlobalExceptionHandler → 404)
     * @throws SecurityValidationException si datos inválidos (manejada por GlobalExceptionHandler → 400)
     */
    @PUT
    @Path("/{id}")
    @Transactional
    public Response actualizar(@PathParam("id") BigInteger id, @Valid PerfilRequestDTO request) {
        // 1. Convertir DTO a dominio
        Perfil perfil = perfilInputMapper.toDomain(request);
        
        // 2. Actualizar perfil mediante puerto de entrada
        Perfil perfilActualizado = perfilInputPort.actualizar(id, perfil);
        
        // 3. Convertir a DTO de respuesta
        PerfilResponseDTO response = perfilInputMapper.toResponseDto(perfilActualizado);
        
        // 4. Retornar 200 OK con DTO
        return Response.ok(response).build();
    }

    /**
     * Elimina perfil existente.
     * 
     * Flujo:
     * 1. Recibir ID de perfil a eliminar
     * 2. Llamar PerfilInputPort.eliminar(id)
     * 3. Retornar 204 No Content (sin cuerpo)
     * 
     * @param id ID del perfil a eliminar
     * @return Response con status 204 No Content
     * @throws SecurityNotFoundException si perfil no existe (manejada por GlobalExceptionHandler → 404)
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response eliminar(@PathParam("id") BigInteger id) {
        // 1. Eliminar perfil mediante puerto de entrada
        perfilInputPort.eliminar(id);
        
        // 2. Retornar 204 No Content
        return Response.noContent().build();
    }
}
