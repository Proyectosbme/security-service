package security.framework.output.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * Repositorio JPA Panache: MenuPerfilViewJpaRepository
 * 
 * Consulta la vista vw_menu_perfil (solo lectura).
 */
@ApplicationScoped
public class MenuPerfilViewJpaRepository implements PanacheRepositoryBase<MenuPerfilViewEntity, MenuPerfilViewId> {
    
    /**
     * Encuentra todos los menús de un perfil desde la vista
     */
    public List<MenuPerfilViewEntity> findByPerfilId(Long perfilId) {
        return list("id.idPerfil", perfilId);
    }
}
