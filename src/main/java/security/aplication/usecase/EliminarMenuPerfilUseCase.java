package security.aplication.usecase;

import security.aplication.port.output.MenuPerfilRepository;
import security.dominio.entidades.MenuPerfil;
import security.dominio.exceptions.SecurityNotFoundException;

import java.math.BigInteger;

/**
 * Caso de Uso: EliminarMenuPerfilUseCase
 * 
 * Orquesta la eliminación de la relación entre menú y perfil.
 * 
 * Responsabilidad:
 * 1. Validar identificadores
 * 2. Verificar existencia de la relación
 * 3. Eliminar relación en BD
 * 
 * Patrón: Use Case / Command Pattern
 * 
 * Flujo:
 * Validar → Verificar existencia → Eliminar
 * 
 * Excepciones:
 * - SecurityNotFoundException: si la relación no existe o IDs inválidos
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class EliminarMenuPerfilUseCase {

    private final MenuPerfilRepository menuPerfilRepository;

    /**
     * Constructor con inyección del repositorio.
     *
     * @param menuPerfilRepository repositorio de relaciones menú-perfil
     */
    public EliminarMenuPerfilUseCase(MenuPerfilRepository menuPerfilRepository) {
        this.menuPerfilRepository = menuPerfilRepository;
    }

    /**
     * Ejecuta la eliminación de una relación menú-perfil.
     *
     * @param menuId identificador del menú
     * @param perfilId identificador del perfil
     * @throws SecurityNotFoundException si no existe la relación o los IDs son inválidos
     */
    public void ejecutar(BigInteger menuId, BigInteger perfilId){
        if(menuId == null || perfilId == null){
            throw new SecurityNotFoundException(
                    "No existe el menú " + menuId + " asigando al perfil " + perfilId
            );
        }
        MenuPerfil menuPerfil= new MenuPerfil(menuId, perfilId);
        boolean existe =menuPerfilRepository.buscarMenuPerfil(menuPerfil).isPresent();

        if(!existe){
            throw new SecurityNotFoundException(menuId.toString()+ perfilId.toString());
        }
        menuPerfilRepository.delete(menuId,perfilId);
    }
}
