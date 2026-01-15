package security.framework.input.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import security.aplication.port.input.ModuloInputPort;
import security.dominio.entidades.Menu;
import security.dominio.entidades.Modulo;
import security.framework.input.dto.MenuResponseDTO;
import security.framework.input.dto.ModuloRequestDTO;
import security.framework.input.dto.ModuloResponseDTO;
import security.framework.input.mapper.ModuloInputMapper;

@Path("/modulo")
public class ModuloController {
    private final ModuloInputMapper moduloInputMapper;
    private final ModuloInputPort moduloInputPort;

    public ModuloController(ModuloInputMapper moduloInputMapper, ModuloInputPort moduloInputPort) {
        this.moduloInputMapper = moduloInputMapper;
        this.moduloInputPort = moduloInputPort;
    }

    @POST
    @Transactional
    public Response crear(ModuloRequestDTO dto) {
        Modulo modulo = moduloInputMapper.toDomain(dto);
        Modulo moduloGuardado = moduloInputPort.crear(modulo);
        // 3. Mapear Persona → DTO respuesta
       ModuloResponseDTO response = moduloInputMapper.toResponseDto(moduloGuardado);
        // 4. Devolver 201 Created
        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }
}
