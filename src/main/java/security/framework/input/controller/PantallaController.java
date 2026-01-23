package security.framework.input.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import security.framework.input.dto.PantallaRequestDTO;
import security.framework.input.dto.PantallaResponseDTO;
import security.aplication.port.input.PantallaInputPort;
import security.dominio.entidades.Pantalla;
import security.framework.input.mapper.PantallaInputMapper;

import java.util.List;

/**
 * Controlador: PantallaController
 * 
 * Responsabilidad: Procesar solicitudes HTTP para operaciones CRUD de pantallas.
 * 
 * Patrón Hexagonal:
 * - Depende de PantallaInputPort (puerto de entrada)
 * - Convierte HTTP (DTOs) a objetos de dominio mediante PantallaInputMapper
 * - Mapea excepciones del dominio a respuestas HTTP apropiadas (manejadas por GlobalExceptionHandler)
 * - Implementa patrón REST: POST (crear), GET (buscar), PUT (actualizar), DELETE (eliminar)
 * 
 * Endpoint Base: /pantalla
 * - POST   /pantalla              → Crear nueva pantalla
 * - GET    /pantalla/{id}         → Buscar pantalla por ID
 * - PUT    /pantalla/{id}         → Actualizar pantalla existente
 * - DELETE /pantalla/{id}         → Eliminar pantalla
 * 
 * Flujo de Solicitud:
 * HTTP Request → [Validación @Valid] → [PantallaInputMapper] → Dominio → [PantallaInputPort]
 * → Lógica de Negocio → Respuesta → [PantallaInputMapper] → DTO → HTTP Response
 * 
 * Excepciones:
 * - SecurityValidationException: Mapea a 400 Bad Request (por GlobalExceptionHandler)
 * - SecurityNotFoundException: Mapea a 404 Not Found (por GlobalExceptionHandler)
 * 
 * Validación:
 * - @Valid en PantallaRequestDTO valida:
 *   - @NotNull código, nome, url, codModulo
 *   - @NotBlank código, nome, url
 *   - @Positive codModulo
 * 
 * Content-Type:
 * - Request: application/json
 * - Response: application/json
 * 
 * HTTP Status:
 * - 201 Created: Pantalla creada exitosamente
 * - 200 OK: Pantalla encontrada o actualizada
 * - 404 Not Found: Pantalla no existe
 * - 400 Bad Request: Datos inválidos
 * - 500 Internal Server Error: Error interno
 * 
 * @author Security Team
 * @version 1.0
 */
@Path("/pantalla")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PantallaController {

    private final PantallaInputPort pantallaInputPort;
    private final PantallaInputMapper pantallaInputMapper;

    /**
     * Constructor con inyección de dependencias.
     * 
     * @param pantallaInputPort Puerto de entrada de pantallas (PantallaService)
     * @param pantallaInputMapper Mapper para conversiones PantallaRequestDTO ↔ Pantalla
     */
    @Inject
    public PantallaController(PantallaInputPort pantallaInputPort, 
                             PantallaInputMapper pantallaInputMapper) {
        this.pantallaInputPort = pantallaInputPort;
        this.pantallaInputMapper = pantallaInputMapper;
    }

    /**
     * Crea nueva pantalla.
     * 
     * Flujo:
     * 1. Recibir PantallaRequestDTO (con validación @Valid)
     * 2. Validar datos (anotaciones en DTO)
     * 3. Convertir DTO a Pantalla (dominio)
     * 4. Llamar PantallaInputPort.crear()
     * 5. Convertir Pantalla a PantallaResponseDTO
     * 6. Retornar 201 Created con DTO y Location header
     * 
     * @param request PantallaRequestDTO con datos de pantalla a crear
     * @return Response con status 201 y PantallaResponseDTO creada
     * @throws SecurityValidationException si datos inválidos (manejada por GlobalExceptionHandler)
     */
    @POST
    @Transactional
    public Response crear(@Valid PantallaRequestDTO request) {
        // 1. Convertir DTO a dominio
        Pantalla pantalla = pantallaInputMapper.toDomain(request);
        
        // 2. Crear pantalla mediante puerto de entrada
        Pantalla pantallaCreada = pantallaInputPort.crear(pantalla);
        
        // 3. Convertir a DTO de respuesta
        PantallaResponseDTO response = pantallaInputMapper.toResponseDto(pantallaCreada);
        
        // 4. Retornar 201 Created con Location header
        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .header("Location", "/pantalla/" + pantallaCreada.getId())
                .build();
    }

    /**
     * Busca pantalla por ID.
     * 
     * Flujo:
     * 1. Recibir ID de pantalla
     * 2. Llamar PantallaInputPort.buscarPorId(id)
     * 3. Convertir Pantalla a PantallaResponseDTO
     * 4. Retornar 200 OK con DTO
     * 
     * @param id ID de pantalla a buscar
     * @return Response con status 200 y PantallaResponseDTO encontrada
     * @throws SecurityNotFoundException si pantalla no existe (maneja por GlobalExceptionHandler → 404)
     */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        // 1. Buscar pantalla mediante puerto de entrada
        Pantalla pantalla = pantallaInputPort.buscarPorId(id);
        
        // 2. Convertir a DTO de respuesta
        PantallaResponseDTO response = pantallaInputMapper.toResponseDto(pantalla);
        
        // 3. Retornar 200 OK con DTO
        return Response.ok(response).build();
    }


    @GET
    public Response obtenerTodas() {
        List<Pantalla> lstPantallas = pantallaInputPort.obtenerTodas();
        return Response.ok(lstPantallas).build();
    }
    /**
     * Actualiza pantalla existente.
     * 
     * Flujo:
     * 1. Recibir ID y PantallaRequestDTO (con validación @Valid)
     * 2. Validar datos (anotaciones en DTO)
     * 3. Convertir DTO a Pantalla (dominio)
     * 4. Llamar PantallaInputPort.actualizar(id, pantalla)
     * 5. Convertir Pantalla actualizada a PantallaResponseDTO
     * 6. Retornar 200 OK con DTO actualizada
     * 
     * Auditoría:
     * - UseCase preserva userC y fechaC de registro original
     * - UseCase registra userMod y fechaMod de modificación
     * - Response DTO incluye información completa de auditoría
     * 
     * @param id ID de pantalla a actualizar
     * @param request PantallaRequestDTO con datos actualizados
     * @return Response con status 200 y PantallaResponseDTO actualizada
     * @throws SecurityNotFoundException si pantalla no existe (maneja por GlobalExceptionHandler → 404)
     * @throws SecurityValidationException si datos inválidos (maneja por GlobalExceptionHandler → 400)
     */
    @PUT
    @Path("/{id}")
    @Transactional
    public Response actualizar(@PathParam("id") Long id, @Valid PantallaRequestDTO request) {
        // 1. Convertir DTO a dominio
        Pantalla pantalla = pantallaInputMapper.toDomain(request);
        
        // 2. Actualizar pantalla mediante puerto de entrada
        Pantalla pantallaActualizada = pantallaInputPort.actualizar(id, pantalla);
        
        // 3. Convertir a DTO de respuesta
        PantallaResponseDTO response = pantallaInputMapper.toResponseDto(pantallaActualizada);
        
        // 4. Retornar 200 OK con DTO actualizada
        return Response.ok(response).build();
    }

    /**
     * Elimina pantalla.
     * 
     * Flujo:
     * 1. Recibir ID de pantalla
     * 2. Llamar PantallaInputPort.eliminar(id)
     * 3. Retornar 204 No Content (éxito sin cuerpo)
     * 
     * @param id ID de pantalla a eliminar
     * @return Response con status 204 No Content
     * @throws SecurityNotFoundException si pantalla no existe (maneja por GlobalExceptionHandler → 404)
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response eliminar(@PathParam("id") Long id) {
        // 1. Eliminar pantalla mediante puerto de entrada
        pantallaInputPort.eliminar(id);
        
        // 2. Retornar 204 No Content (éxito sin cuerpo)
        return Response.noContent().build();
    }
}
