package security.framework.output.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import security.dominio.entidades.MenuPerfil;
import security.framework.output.persistence.MenuPerfilId;
import security.framework.output.persistence.MenuPerfilJpaEntity;

/**
 * Mapper: MenuPerfilJpaMapper
 * 
 * Convierte entre MenuPerfil (dominio) y MenuPerfilJpaEntity (persistencia).
 */
@Mapper(componentModel = "cdi")
public interface MenuPerfilJpaMapper {
    
    /**
     * Convierte MenuPerfil de dominio a entidad JPA
     */
    default MenuPerfilJpaEntity toJpaEntity(MenuPerfil menuPerfil) {
        if (menuPerfil == null) {
            return null;
        }
        
        MenuPerfilId id = new MenuPerfilId(
            menuPerfil.getMenuId().longValue(),
            menuPerfil.getPerfilId().longValue()
        );
        
        return new MenuPerfilJpaEntity(id);
    }
    
    /**
     * Convierte entidad JPA a MenuPerfil de dominio
     */
    default MenuPerfil toDomain(MenuPerfilJpaEntity entity) {
        if (entity == null || entity.getId() == null) {
            return null;
        }
        
        return new MenuPerfil(
            java.math.BigInteger.valueOf(entity.getId().getMenuId()),
            java.math.BigInteger.valueOf(entity.getId().getPerfilId())
        );
    }
    
    /**
     * Convierte lista de entidades JPA a lista de dominio
     */
    default java.util.List<MenuPerfil> toDomainList(java.util.List<MenuPerfilJpaEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
            .map(this::toDomain)
            .collect(java.util.stream.Collectors.toList());
    }
}
