package security.aplication.port.output;

import security.framework.output.persistence.MenuPerfilViewEntity;

import java.util.List;

/**
 * Puerto de Salida: MenuPerfilViewRepository
 * 
 * Define operaciones de consulta sobre la vista vw_menu_perfil.
 */
public interface MenuPerfilViewRepository {
    
    /**
     * Busca todos los menús de un perfil desde la vista
     */
    List<MenuPerfilViewEntity> findByPerfilId(Long perfilId);
}
