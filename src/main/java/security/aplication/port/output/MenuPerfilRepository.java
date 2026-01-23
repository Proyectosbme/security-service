package security.aplication.port.output;

import security.dominio.entidades.MenuPerfil;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de Salida: MenuPerfilRepository
 * 
 * Define las operaciones de persistencia para MenuPerfil.
 */
public interface MenuPerfilRepository {
    
    /**
     * Guarda una relación menú-perfil
     */
    MenuPerfil save(MenuPerfil menuPerfil);


    Optional<MenuPerfil> buscarMenuPerfil(MenuPerfil menuPerfil);
    /**
     * Busca todas las relaciones menú-perfil de un perfil
     */
    List<MenuPerfil> findByPerfilId(BigInteger perfilId);
    
    /**
     * Busca todas las relaciones menú-perfil de un menú
     */
    List<MenuPerfil> findByMenuId(BigInteger menuId);
    
    /**
     * Elimina una relación menú-perfil
     */
    void delete(BigInteger menuId, BigInteger perfilId);
    
    /**
     * Elimina todas las relaciones de un perfil
     */
    void deleteByPerfilId(BigInteger perfilId);
    
    /**
     * Elimina todas las relaciones de un menú
     */
    void deleteByMenuId(BigInteger menuId);
}
