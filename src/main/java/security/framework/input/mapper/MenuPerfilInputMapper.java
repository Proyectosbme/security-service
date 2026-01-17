package security.framework.input.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import security.dominio.entidades.MenuPerfil;
import security.framework.input.dto.MenuPerfilRequestDTO;
import security.framework.input.dto.MenuPerfilResponseDTO;

import java.util.List;

/**
 * Mapper: MenuPerfilInputMapper
 * 
 * Convierte entre MenuPerfil (dominio) y DTOs (input).
 */
@Mapper(componentModel = "cdi")
public interface MenuPerfilInputMapper {
    
    /**
     * Convierte DTO de entrada a MenuPerfil de dominio
     */
    @Mapping(target = "menuId", expression = "java(java.math.BigInteger.valueOf(dto.getMenuId()))")
    @Mapping(target = "perfilId", expression = "java(java.math.BigInteger.valueOf(dto.getPerfilId()))")
    MenuPerfil toDomain(MenuPerfilRequestDTO dto);
    
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
