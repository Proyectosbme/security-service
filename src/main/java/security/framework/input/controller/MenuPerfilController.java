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
 * Endpoints para gestión de relaciones menú-perfil.
 */
@Path("/menu-perfil")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MenuPerfilController {
    
    private final MenuPerfilInputPort menuPerfilInputPort;
    private final MenuPerfilInputMapper mapper;
    private final MenuJerarquicoMapper menuJerarquicoMapper;
    
    @Inject
    public MenuPerfilController(MenuPerfilInputPort menuPerfilInputPort,
                                MenuPerfilInputMapper mapper,
                                MenuJerarquicoMapper menuJerarquicoMapper) {
        this.menuPerfilInputPort = menuPerfilInputPort;
        this.mapper = mapper;
        this.menuJerarquicoMapper = menuJerarquicoMapper;
    }
    
    /**
     * POST /menu-perfil
     * Asigna un menú a un perfil
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
     * GET /menu-perfil/perfil/{perfilId}
     * Obtiene todos los menús asignados a un perfil
     */
    @GET
    @Path("/perfil/{perfilId}")
    public Response buscarPorPerfil(@PathParam("perfilId") Long perfilId) {
        List<MenuPerfil> menus = menuPerfilInputPort.buscarPorPerfil(BigInteger.valueOf(perfilId));
        List<MenuPerfilResponseDTO> response = mapper.toResponseDtoList(menus);
        return Response.ok(response).build();
    }
    
    /**
     * DELETE /menu-perfil/menu/{menuId}/perfil/{perfilId}
     * Remueve un menú de un perfil
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
     * GET /menu-perfil/jerarquico/perfil/{perfilId}
     * Obtiene estructura jerárquica de menús para un perfil
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