package security.framework.output.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repositorio JPA Panache: MenuPerfilJpaRepository
 * 
 * Proporciona operaciones CRUD para MenuPerfilJpaEntity.
 */
@ApplicationScoped
public class MenuPerfilJpaRepository implements PanacheRepositoryBase<MenuPerfilJpaEntity, MenuPerfilId> {
    
    /**
     * Encuentra todas las relaciones de un perfil
     */
    public java.util.List<MenuPerfilJpaEntity> findByPerfilId(Long perfilId) {
        return list("id.perfilId", perfilId);
    }
    
    /**
     * Encuentra todas las relaciones de un menú
     */
    public java.util.List<MenuPerfilJpaEntity> findByMenuId(Long menuId) {
        return list("id.menuId", menuId);
    }
    
    /**
     * Elimina una relación específica
     */
    public void deleteByMenuIdAndPerfilId(Long menuId, Long perfilId) {
        delete("id.menuId = ?1 and id.perfilId = ?2", menuId, perfilId);
    }
    
    /**
     * Elimina todas las relaciones de un perfil
     */
    public void deleteByPerfilId(Long perfilId) {
        delete("id.perfilId", perfilId);
    }
    
    /**
     * Elimina todas las relaciones de un menú
     */
    public void deleteByMenuId(Long menuId) {
        delete("id.menuId", menuId);
    }
}
