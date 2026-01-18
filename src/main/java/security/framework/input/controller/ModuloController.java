package security.framework.input.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import security.aplication.port.input.ModuloInputPort;
import security.dominio.entidades.Modulo;
import security.framework.input.dto.ModuloRequestDTO;
import security.framework.input.dto.ModuloResponseDTO;
import security.framework.input.mapper.ModuloInputMapper;

import java.util.List;

/**
 * Controlador REST: ModuloController
 * 
 * Expone endpoints HTTP para operaciones CRUD de módulos.
 */
@Path("/modulo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModuloController {
    
    private final ModuloInputMapper moduloInputMapper;
    private final ModuloInputPort moduloInputPort;

    @Inject
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
    
    /**
     * GET /modulo/{id}
     * Busca un módulo por su ID
     */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Modulo modulo = moduloInputPort.buscarPorId(id);
        ModuloResponseDTO response = moduloInputMapper.toResponseDto(modulo);
        return Response.ok(response).build();
    }
    
    /**
     * GET /modulo
     * Obtiene todos los módulos
     */
    @GET
    public Response obtenerTodos() {
        List<Modulo> modulos = moduloInputPort.obtenerTodas();
        List<ModuloResponseDTO> response = moduloInputMapper.toResponseDtoList(modulos);
        return Response.ok(response).build();
    }
    
    /**
     * PUT /modulo/{id}
     * Actualiza un módulo existente
     */
    @PUT
    @Path("/{id}")
    @Transactional
    public Response actualizar(@PathParam("id") Long id, ModuloRequestDTO dto) {
        Modulo modulo = moduloInputMapper.toDomain(dto);
        Modulo moduloActualizado = moduloInputPort.acualizar(id, modulo);
        ModuloResponseDTO response = moduloInputMapper.toResponseDto(moduloActualizado);
        return Response.ok(response).build();
    }
    
    /**
     * DELETE /modulo/{id}
     * Elimina un módulo
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response eliminar(@PathParam("id") Long id) {
        moduloInputPort.eliminar(id);
        return Response.noContent().build();
    }
}
