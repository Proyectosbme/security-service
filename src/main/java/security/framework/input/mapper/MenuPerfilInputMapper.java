package security.framework.input.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import security.dominio.entidades.MenuPerfil;
import security.framework.input.dto.MenuPerfilResponseDTO;

import java.util.List;

/**
 * Mapper: MenuPerfilInputMapper
 * 
 * Convierte entre MenuPerfil (dominio) y MenuPerfilResponseDTO (input).
 */
@Mapper(componentModel = "cdi")
public interface MenuPerfilInputMapper {
    
    /**
     * Convierte MenuPerfil de dominio a DTO de salida
     */
    @Mapping(target = "menuId", expression = "java(menuPerfil.getMenuId().longValue())")
    @Mapping(target = "perfilId", expression = "java(menuPerfil.getPerfilId().longValue())")
    MenuPerfilResponseDTO toResponseDto(MenuPerfil menuPerfil);
    
    /**
     * Convierte lista de MenuPerfil a lista de DTOs
     */
    List<MenuPerfilResponseDTO> toResponseDtoList(List<MenuPerfil> menuPerfils);
}
