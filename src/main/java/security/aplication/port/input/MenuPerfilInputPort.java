package security.aplication.port.input;

import security.aplication.dto.MenuJerarquico;
import security.dominio.entidades.MenuPerfil;

import java.math.BigInteger;
import java.util.List;

/**
 * Puerto de Entrada: MenuPerfilInputPort
 * 
 * Define las operaciones disponibles para gestión de relaciones menú-perfil.
 * 
 * Patrón Hexagonal:
 * - Puerto de entrada para la capa de aplicación
 * - Implementado por MenuPerfilService
 * - Invocado desde MenuPerfilController
 * 
 * Operaciones:
 * - asignar(): Asignar menú a perfil
 * - buscarPorPerfil(): Obtener menús de un perfil
 * - remover(): Eliminar relación menú-perfil
 * - obtenerMenusJerarquicos(): Construir árbol de menús para un perfil
 */
public interface MenuPerfilInputPort {
    
    /**
     * Asigna un menú a un perfil
     * 
     * @param menuId ID del menú
     * @param perfilId ID del perfil
     * @return MenuPerfil creado
     */
    MenuPerfil asignar(BigInteger menuId, BigInteger perfilId);
    
    /**
     * Busca todos los menús asignados a un perfil
     * 
     * @param perfilId ID del perfil
     * @return Lista de relaciones menú-perfil
     */
    List<MenuPerfil> buscarPorPerfil(BigInteger perfilId);
    
    /**
     * Remueve un menú de un perfil
     * 
     * @param menuId ID del menú
     * @param perfilId ID del perfil
     */
    void remover(BigInteger menuId, BigInteger perfilId);
    
    /**
     * Obtiene estructura jerárquica de menús para un perfil
     * 
     * @param perfilId ID del perfil
     * @return Lista de menús raíz con sus hijos anidados
     */
    List<MenuJerarquico> obtenerMenusJerarquicos(Long perfilId);
}
