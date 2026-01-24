package security.aplication.port.input;

import security.aplication.dto.MenuJerarquico;
import security.dominio.entidades.MenuPerfil;

import java.math.BigInteger;
import java.util.List;

/**
 * Puerto de Entrada (Input Port): MenuPerfilInputPort
 * 
 * Define el contrato de aplicación para la gestión de relaciones menú-perfil.
 * 
 * Responsabilidad:
 * 1. Exponer operaciones de asignación y consulta
 * 2. Construir menús jerárquicos por perfil
 * 3. Mantener la independencia de la infraestructura
 * 
 * Patrón: Input Port (Arquitectura Hexagonal)
 * 
 * Flujo:
 * Controller → MenuPerfilInputPort → MenuPerfilService → UseCases → Repositorios
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public interface MenuPerfilInputPort {
    
    /**
     * Asigna un menú a un perfil.
     * 
     * @param menuId identificador del menú
     * @param perfilId identificador del perfil
     * @return relación menú-perfil creada
     */
    MenuPerfil asignar(BigInteger menuId, BigInteger perfilId);
    
    /**
     * Busca todos los menús asignados a un perfil.
     * 
     * @param perfilId identificador del perfil
     * @return lista de relaciones menú-perfil
     */
    List<MenuPerfil> buscarPorPerfil(BigInteger perfilId);
    
    /**
     * Remueve un menú de un perfil.
     * 
     * @param menuId identificador del menú
     * @param perfilId identificador del perfil
     */
    void remover(BigInteger menuId, BigInteger perfilId);
    
    /**
     * Obtiene estructura jerárquica de menús para un perfil.
     * 
     * @param perfilId identificador del perfil
     * @return lista de menús raíz con sus hijos anidados
     */
    List<MenuJerarquico> obtenerMenusJerarquicos(Long perfilId);
}
