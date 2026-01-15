package security.framework.input.controller;

import security.aplication.port.input.MenuInputPort;
import security.dominio.entidades.Menu;
import security.framework.input.dto.MenuRequestDTO;
import security.framework.input.dto.MenuResponseDTO;
import security.framework.input.mapper.MenuInputMapper;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/menu")
public class MenuController {

    private final MenuInputPort menuInputPort;
    private final MenuInputMapper menuInputMapper;

    public MenuController(MenuInputPort personaService, MenuInputMapper menuInputMapper) {
        this.menuInputPort = personaService;
        this.menuInputMapper = menuInputMapper;
    }

    @POST
    @Transactional
    public Response crear(MenuRequestDTO dto) {
        Menu menu = menuInputMapper.toDomain(dto);
        Menu menuGuardado = menuInputPort.crear(menu);
        // 3. Mapear Persona → DTO respuesta
        MenuResponseDTO response = menuInputMapper.toResponseDto(menuGuardado);
        // 4. Devolver 201 Created
        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @GET
    @Path("/idmenu/{idmenu}")
    public MenuResponseDTO buscarPorId(@PathParam("idmenu") Long id) {
        // 1. Llamar al servicio
        Menu menu = menuInputPort.buscarPorId(id);

        // 2. Mapear respuesta
        return menuInputMapper.toResponseDto(menu);

    }

    @PUT
    @Path("/idmenu/{idmenu}")
    @Transactional
    public MenuResponseDTO actualizar(@PathParam("idmenu") Long id,
                                         MenuRequestDTO dto ) {
        // 1. Mapear DTO → menu domain
        Menu datosNuevos = menuInputMapper.toDomain(dto);

        // 2. Llamar al servicio
        Menu personaActualizada = menuInputPort.acualizar(id,datosNuevos);

        // 3. Mapear respuesta
        return menuInputMapper.toResponseDto(personaActualizada);


    }
}
