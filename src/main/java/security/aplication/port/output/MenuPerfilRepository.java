package security.aplication.port.output;

import security.dominio.entidades.MenuPerfil;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de Salida (Output Port): MenuPerfilRepository
 * 
 * Define el contrato de persistencia para relaciones menú-perfil.
 * 
 * Responsabilidad:
 * 1. Crear y eliminar relaciones menú-perfil
 * 2. Consultar relaciones por menú o perfil
 * 3. Abstraer la tecnología de persistencia
 * 
 * Patrón: Repository / Output Port (Arquitectura Hexagonal)
 * 
 * Flujo:
 * UseCase → MenuPerfilRepository → Implementación de persistencia
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public interface MenuPerfilRepository {
    
    /**
     * Guarda una relación menú-perfil.
     *
     * @param menuPerfil relación a guardar
     * @return relación creada
     */
    MenuPerfil save(MenuPerfil menuPerfil);

    /**
     * Busca una relación menú-perfil específica.
     *
     * @param menuPerfil relación a buscar
     * @return relación encontrada, si existe
     */
    Optional<MenuPerfil> buscarMenuPerfil(MenuPerfil menuPerfil);
    /**
     * Busca todas las relaciones menú-perfil de un perfil.
     *
     * @param perfilId identificador del perfil
     * @return lista de relaciones
     */
    List<MenuPerfil> findByPerfilId(BigInteger perfilId);
    
    /**
     * Busca todas las relaciones menú-perfil de un menú.
     *
     * @param menuId identificador del menú
     * @return lista de relaciones
     */
    List<MenuPerfil> findByMenuId(BigInteger menuId);
    
    /**
     * Elimina una relación menú-perfil.
     *
     * @param menuId identificador del menú
     * @param perfilId identificador del perfil
     */
    void delete(BigInteger menuId, BigInteger perfilId);
    
    /**
     * Elimina todas las relaciones de un perfil.
     *
     * @param perfilId identificador del perfil
     */
    void deleteByPerfilId(BigInteger perfilId);
    
    /**
     * Elimina todas las relaciones de un menú.
     *
     * @param menuId identificador del menú
     */
    void deleteByMenuId(BigInteger menuId);
}
