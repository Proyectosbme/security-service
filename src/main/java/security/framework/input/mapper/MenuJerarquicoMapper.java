package security.framework.input.mapper;

import org.mapstruct.Mapper;
import security.aplication.dto.MenuJerarquico;
import security.framework.input.dto.MenuJerarquicoResponseDTO;

import java.util.List;

/**
 * Mapper: MenuJerarquicoMapper
 * 
 * Convierte MenuJerarquico (aplicación) a MenuJerarquicoResponseDTO (framework).
 */
@Mapper(componentModel = "cdi")
public interface MenuJerarquicoMapper {
    
    /**
     * Convierte MenuJerarquico de aplicación a DTO de respuesta
     */
    MenuJerarquicoResponseDTO toResponseDto(MenuJerarquico menuJerarquico);
    
    /**
     * Convierte lista de MenuJerarquico a lista de DTOs de respuesta
     */
    List<MenuJerarquicoResponseDTO> toResponseDtoList(List<MenuJerarquico> menuJerarquicos);
}
