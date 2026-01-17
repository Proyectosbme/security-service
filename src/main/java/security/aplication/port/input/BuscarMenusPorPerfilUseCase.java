package security.aplication.port.input;

import security.dominio.entidades.MenuPerfil;

import java.math.BigInteger;
import java.util.List;

/**
 * Caso de Uso: Buscar Menús por Perfil
 * 
 * Obtiene todos los menús asignados a un perfil.
 */
public interface BuscarMenusPorPerfilUseCase {
    
    /**
     * Busca todos los menús de un perfil
     * 
     * @param perfilId ID del perfil
     * @return Lista de relaciones menú-perfil
     */
    List<MenuPerfil> buscar(BigInteger perfilId);
}
