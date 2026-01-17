package security.aplication.port.input;

import security.dominio.entidades.MenuPerfil;

import java.math.BigInteger;

/**
 * Caso de Uso: Asignar Menú a Perfil
 * 
 * Crea una relación entre un menú y un perfil.
 */
public interface AsignarMenuAPerfilUseCase {
    
    /**
     * Asigna un menú a un perfil
     * 
     * @param menuId ID del menú
     * @param perfilId ID del perfil
     * @return MenuPerfil creado
     */
    MenuPerfil asignar(BigInteger menuId, BigInteger perfilId);
}
