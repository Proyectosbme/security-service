package security.framework.input.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import security.aplication.dto.MenuJerarquico;
import security.aplication.port.input.MenuPerfilInputPort;
import security.dominio.entidades.MenuPerfil;
import security.framework.input.dto.MenuJerarquicoResponseDTO;
import security.framework.input.dto.MenuPerfilRequestDTO;
import security.framework.input.dto.MenuPerfilResponseDTO;
import security.framework.input.mapper.MenuJerarquicoMapper;
import security.framework.input.mapper.MenuPerfilInputMapper;

import java.math.BigInteger;
import java.util.List;

/**
 * Controlador REST: MenuPerfilController
 * 
 * Responsabilidad: Procesar solicitudes HTTP para la gestión de relaciones menú-perfil.
 * 
 * Patrón Hexagonal:
 * - Depende de MenuPerfilInputPort (puerto de entrada)
 * - Convierte HTTP (DTOs) a dominio mediante MenuPerfilInputMapper
 * - Convierte DTOs de aplicación a DTOs de framework (MenuJerarquicoMapper)
 * - Manejo de excepciones vía GlobalExceptionHandler
 * 
 * Endpoint Base: /menu-perfil
 * - POST   /menu-perfil                         → Asignar menú a perfil
 * - GET    /menu-perfil/perfil/{perfilId}       → Listar menús asignados a perfil
 * - DELETE /menu-perfil/menu/{menuId}/perfil/{perfilId} → Remover menú de perfil
 * - GET    /menu-perfil/jerarquico/perfil/{perfilId}    → Menús jerárquicos por perfil
 * 
 * Flujo de Solicitud:
 * HTTP Request → [MenuPerfilInputMapper] → Dominio → [MenuPerfilInputPort]
 * → Lógica de Negocio → Respuesta → [MenuPerfilInputMapper / MenuJerarquicoMapper] → DTO → HTTP Response
 * 
 * Content-Type:
 * - Request: application/json
 * - Response: application/json
 * 
 * HTTP Status:
 * - 201 Created: Asignación creada correctamente
 * - 200 OK: Consulta exitosa
 * - 204 No Content: Eliminación exitosa
 * - 400 Bad Request: Datos inválidos
 * - 404 Not Found: Relación no encontrada
 * - 500 Internal Server Error: Error interno
 * 
 * @author Security Team
 * @version 1.0
 */
@Path("/menu-perfil")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MenuPerfilController {
    
    /** Puerto de entrada para casos de uso de menú-perfil */
    private final MenuPerfilInputPort menuPerfilInputPort;
    
    /** Mapper para conversiones MenuPerfilRequestDTO ↔ MenuPerfil */
    private final MenuPerfilInputMapper mapper;
    
    /** Mapper para convertir DTOs jerárquicos de aplicación a framework */
    private final MenuJerarquicoMapper menuJerarquicoMapper;
    
    /**
     * Constructor con inyección de dependencias.
     * 
     * @param menuPerfilInputPort Puerto de entrada de menú-perfil
     * @param mapper Mapper de entrada para MenuPerfil
     * @param menuJerarquicoMapper Mapper para menús jerárquicos
     */
    @Inject
    public MenuPerfilController(MenuPerfilInputPort menuPerfilInputPort,
                                MenuPerfilInputMapper mapper,
                                MenuJerarquicoMapper menuJerarquicoMapper) {
        this.menuPerfilInputPort = menuPerfilInputPort;
        this.mapper = mapper;
        this.menuJerarquicoMapper = menuJerarquicoMapper;
    }
    
    /**
     * Asigna un menú a un perfil.
     * 
     * Flujo:
     * 1. Recibe MenuPerfilRequestDTO (JSON)
     * 2. Convierte a MenuPerfil (dominio) con MenuPerfilInputMapper
     * 3. Delega a menuPerfilInputPort.asignar(menuId, perfilId)
     * 4. Convierte el resultado a MenuPerfilResponseDTO
     * 5. Retorna HTTP 201 Created
     * 
     * @param dto MenuPerfilRequestDTO con IDs de menú y perfil
     * @return Response HTTP 201 Created con MenuPerfilResponseDTO
     * 
     * Ejemplo Request:
     * POST /menu-perfil
     * {
     *   "menuId": 10,
     *   "perfilId": 3
     * }
     * 
     * Ejemplo Response (201):
     * {
     *   "id": 1001,
     *   "menuId": 10,
     *   "perfilId": 3
     * }
     */
    @POST
    public Response asignar(MenuPerfilRequestDTO dto) {
        // Convertir DTO a entidad de dominio (respetando arquitectura hexagonal)
        MenuPerfil menuPerfilDomain = mapper.toDomain(dto);
        
        // Llamar al puerto de entrada
        MenuPerfil resultado = menuPerfilInputPort.asignar(
            menuPerfilDomain.getMenuId(),
            menuPerfilDomain.getPerfilId()
        );
        
        // Convertir resultado de dominio a DTO de respuesta
        MenuPerfilResponseDTO response = mapper.toResponseDto(resultado);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }
    
    /**
     * Obtiene todos los menús asignados a un perfil.
     * 
     * Flujo:
     * 1. Extrae perfilId desde la ruta
     * 2. Delega a menuPerfilInputPort.buscarPorPerfil(perfilId)
     * 3. Convierte lista de dominio a lista de MenuPerfilResponseDTO
     * 4. Retorna HTTP 200 OK
     * 
     * @param perfilId ID del perfil (ruta)
     * @return Response HTTP 200 OK con List<MenuPerfilResponseDTO>
     * 
     * Ejemplo Request:
     * GET /menu-perfil/perfil/3
     * 
     * Ejemplo Response (200):
     * [
     *   { "id": 1001, "menuId": 10, "perfilId": 3 },
     *   { "id": 1002, "menuId": 11, "perfilId": 3 }
     * ]
     */
    @GET
    @Path("/perfil/{perfilId}")
    public Response buscarPorPerfil(@PathParam("perfilId") Long perfilId) {
        List<MenuPerfil> menus = menuPerfilInputPort.buscarPorPerfil(BigInteger.valueOf(perfilId));
        List<MenuPerfilResponseDTO> response = mapper.toResponseDtoList(menus);
        return Response.ok(response).build();
    }
    
    /**
     * Remueve un menú de un perfil.
     * 
     * Flujo:
     * 1. Extrae menuId y perfilId desde la ruta
     * 2. Delega a menuPerfilInputPort.remover(menuId, perfilId)
     * 3. Retorna HTTP 204 No Content
     * 
     * @param menuId ID del menú (ruta)
     * @param perfilId ID del perfil (ruta)
     * @return Response HTTP 204 No Content
     * 
     * Ejemplo Request:
     * DELETE /menu-perfil/menu/10/perfil/3
     */
    @DELETE
    @Path("/menu/{menuId}/perfil/{perfilId}")
    public Response remover(
        @PathParam("menuId") Long menuId,
        @PathParam("perfilId") Long perfilId
    ) {
        menuPerfilInputPort.remover(
            BigInteger.valueOf(menuId),
            BigInteger.valueOf(perfilId)
        );
        return Response.noContent().build();
    }
    
    /**
     * Obtiene la estructura jerárquica de menús para un perfil.
     * 
     * Flujo:
     * 1. Extrae perfilId desde la ruta
     * 2. Llama a menuPerfilInputPort.obtenerMenusJerarquicos(perfilId)
     * 3. Convierte DTOs de aplicación (MenuJerarquico) a DTOs de framework
     * 4. Retorna HTTP 200 OK con lista jerárquica
     * 
     * @param perfilId ID del perfil (ruta)
     * @return Response HTTP 200 OK con List<MenuJerarquicoResponseDTO>
     * 
     * Ejemplo Request:
     * GET /menu-perfil/jerarquico/perfil/3
     */
    @GET
    @Path("/jerarquico/perfil/{perfilId}")
    public Response obtenerMenusJerarquicos(@PathParam("perfilId") Long perfilId) {
        // Llamar al puerto de entrada (retorna DTO de aplicación)
        List<MenuJerarquico> menusApp = menuPerfilInputPort.obtenerMenusJerarquicos(perfilId);
        
        // Convertir DTO de aplicación a DTO de framework
        List<MenuJerarquicoResponseDTO> response = menuJerarquicoMapper.toResponseDtoList(menusApp);
        
        return Response.ok(response).build();
    }
}