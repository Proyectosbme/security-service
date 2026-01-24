package security.aplication.usecase;

import security.aplication.port.output.PerfilRepository;
import security.dominio.entidades.Perfil;

import java.util.List;

/**
 * Caso de Uso: ObtenerPerfilesUseCase
 * 
 * Orquesta la consulta de todos los perfiles disponibles.
 * 
 * Responsabilidad:
 * 1. Consultar repositorio
 * 2. Retornar listado completo
 * 
 * Patrón: Use Case / Query Pattern
 * 
 * Flujo:
 * Consultar → Retornar
 * 
 * Excepciones:
 * - Ninguna
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class ObtenerPerfilesUseCase {

    private final PerfilRepository perfilRepository;

    /**
     * Constructor con inyección del repositorio.
     *
     * @param perfilRepository repositorio de perfiles
     */
    public ObtenerPerfilesUseCase(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    /**
     * Ejecuta la consulta de todos los perfiles.
     *
     * @return lista de perfiles
     */
    public List<Perfil> ejecutar(){
        return perfilRepository.findAll();
    }

}
