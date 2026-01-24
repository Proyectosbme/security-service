package security.aplication.port.output;

import security.aplication.dto.MenuPerfilView;

import java.util.List;

/**
 * Puerto de Salida (Output Port): MenuPerfilViewRepository
 * 
 * Define el contrato de acceso a la vista vw_menu_perfil para consultas jerárquicas.
 * 
 * Responsabilidad:
 * 1. Consultar menús por perfil desde la vista
 * 2. Retornar DTOs de aplicación (MenuPerfilView)
 * 
 * Patrón: Repository / Output Port (Arquitectura Hexagonal)
 * 
 * Flujo:
 * UseCase → MenuPerfilViewRepository → Implementación de persistencia
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
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
