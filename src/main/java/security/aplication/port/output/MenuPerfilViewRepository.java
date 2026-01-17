package security.aplication.port.output;

import security.aplication.dto.MenuPerfilView;

import java.util.List;

/**
 * Puerto de Salida: MenuPerfilViewRepository
 * 
 * Acceso a la vista vw_menu_perfil para consultas jerárquicas.
 * 
 * Retorna DTOs de aplicación (MenuPerfilView) en lugar de entidades JPA,
 * manteniendo la arquitectura hexagonal limpia.
 */
public interface MenuPerfilViewRepository {
    
    /**
     * Busca todos los menús de un perfil desde la vista.
     * 
     * @param perfilId ID del perfil
     * @return Lista de MenuPerfilView (DTO de aplicación)
     */
    List<MenuPerfilView> findByPerfilId(Long perfilId);
}
