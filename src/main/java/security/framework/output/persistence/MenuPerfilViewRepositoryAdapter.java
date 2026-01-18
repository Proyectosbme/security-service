package security.framework.output.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import security.aplication.dto.MenuPerfilView;
import security.aplication.port.output.MenuPerfilViewRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adaptador de Salida: MenuPerfilViewRepositoryAdapter
 * 
 * Implementa MenuPerfilViewRepository usando JPA/Panache sobre la vista.
 * Convierte MenuPerfilViewEntity (JPA) → MenuPerfilView (DTO aplicación).
 */
@ApplicationScoped
public class MenuPerfilViewRepositoryAdapter implements MenuPerfilViewRepository {
    
    @Inject
    MenuPerfilViewJpaRepository jpaRepository;
    
    @Override
    public List<MenuPerfilView> findByPerfilId(Long perfilId) {
        return jpaRepository.findByPerfilId(perfilId)
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
    
    /**
     * Convierte MenuPerfilViewEntity (JPA) a MenuPerfilView (DTO aplicación).
     * Mantiene la arquitectura hexagonal limpia.
     */
    private MenuPerfilView toDto(MenuPerfilViewEntity entity) {
        return new MenuPerfilView(
            entity.getId().getIdMenu(),
            entity.getId().getIdPerfil(),
            entity.getNombre(),
            entity.getJerarq(),
            entity.getMenuPadre(),
            entity.getOrden(),
            entity.getUrl()
        );
    }
}

