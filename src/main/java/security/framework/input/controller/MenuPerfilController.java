package security.framework.input.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import security.aplication.port.input.AsignarMenuAPerfilUseCase;
import security.aplication.port.input.BuscarMenusPorPerfilUseCase;
import security.aplication.port.input.RemoverMenuDePerfilUseCase;
import security.dominio.entidades.MenuPerfil;
import security.framework.input.dto.MenuPerfilRequestDTO;
import security.framework.input.dto.MenuPerfilResponseDTO;
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
    
    @Inject
    AsignarMenuAPerfilUseCase asignarMenuAPerfilUseCase;
    
    @Inject
    BuscarMenusPorPerfilUseCase buscarMenusPorPerfilUseCase;
    
    @Inject
    RemoverMenuDePerfilUseCase removerMenuDePerfilUseCase;
    
    @Inject
    MenuPerfilInputMapper mapper;
    
    /**
     * POST /menu-perfil
     * Asigna un menú a un perfil
     */
    @POST
    public Response asignar(MenuPerfilRequestDTO dto) {
        MenuPerfil menuPerfil = asignarMenuAPerfilUseCase.asignar(
            BigInteger.valueOf(dto.getMenuId()),
            BigInteger.valueOf(dto.getPerfilId())
        );
        MenuPerfilResponseDTO response = mapper.toResponseDto(menuPerfil);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }
    
    /**
     * GET /menu-perfil/perfil/{perfilId}
     * Obtiene todos los menús asignados a un perfil
     */
    @GET
    @Path("/perfil/{perfilId}")
    public Response buscarPorPerfil(@PathParam("perfilId") Long perfilId) {
        List<MenuPerfil> menus = buscarMenusPorPerfilUseCase.buscar(BigInteger.valueOf(perfilId));
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
        removerMenuDePerfilUseCase.remover(
            BigInteger.valueOf(menuId),
            BigInteger.valueOf(perfilId)
        );
        return Response.noContent().build();
    }
}
