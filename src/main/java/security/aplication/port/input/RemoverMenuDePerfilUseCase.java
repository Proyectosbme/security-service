package security.aplication.port.input;

import java.math.BigInteger;

/**
 * Caso de Uso: Remover Menú de Perfil
 * 
 * Elimina la relación entre un menú y un perfil.
 */
public interface RemoverMenuDePerfilUseCase {
    
    /**
     * Remueve un menú de un perfil
     * 
     * @param menuId ID del menú
     * @param perfilId ID del perfil
     */
    void remover(BigInteger menuId, BigInteger perfilId);
}
