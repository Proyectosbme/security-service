package security.aplication.usecase;

import security.aplication.port.output.MenuPerfilRepository;
import security.dominio.entidades.MenuPerfil;

import java.math.BigInteger;
import java.util.List;

/**
 * Caso de Uso: BuscarMenuPefilPorPefil
 * 
 * Orquesta la búsqueda de relaciones menú-perfil por identificador de perfil.
 * 
 * Responsabilidad:
 * 1. Consultar el repositorio por ID de perfil
 * 2. Retornar relaciones encontradas
 * 
 * Patrón: Use Case / Query Pattern
 * 
 * Flujo:
 * Buscar → Retornar
 * 
 * Excepciones:
 * - Ninguna
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class BuscarMenuPefilPorPefil {

    private final MenuPerfilRepository menuPerfilRepository;

    /**
     * Constructor con inyección del repositorio.
     *
     * @param menuPerfilRepository repositorio de relaciones menú-perfil
     */
    public BuscarMenuPefilPorPefil(MenuPerfilRepository menuPerfilRepository) {
        this.menuPerfilRepository = menuPerfilRepository;
    }

    /**
     * Ejecuta la búsqueda de menús por perfil.
     *
     * @param id identificador del perfil
     * @return lista de relaciones menú-perfil
     */
    public List<MenuPerfil> ejecutar(BigInteger id){
       return this.menuPerfilRepository.findByPerfilId( id);
    }
}
