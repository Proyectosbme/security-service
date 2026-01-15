package security.framework.input.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import security.dominio.entidades.Modulo;
import security.framework.input.dto.ModuloRequestDTO;
import security.framework.input.dto.ModuloResponseDTO;

@Mapper(componentModel = "cdi")
public interface ModuloInputMapper {

    ModuloResponseDTO toResponseDto(Modulo modulo);

    @Mapping(target = "id", ignore = true)
    Modulo toDomain(ModuloRequestDTO dto);
}
