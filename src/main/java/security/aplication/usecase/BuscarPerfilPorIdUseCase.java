package security.aplication.usecase;

import security.aplication.port.output.PerfilRepository;
import security.dominio.entidades.Perfil;
import security.dominio.exceptions.SecurityNotFoundException;

import java.math.BigInteger;

/**
 * Caso de Uso: BuscarPerfilPorIdUseCase
 * 
 * Orquesta la búsqueda de un perfil por su identificador.
 * 
 * Responsabilidad:
 * 1. Consultar el repositorio por ID
 * 2. Retornar perfil encontrado
 * 3. Lanzar excepción si no existe
 * 
 * Patrón: Use Case / Query Pattern
 * 
 * Flujo:
 * Buscar → Validar existencia → Retornar
 * 
 * Excepciones:
 * - SecurityNotFoundException: si el perfil no existe
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class BuscarPerfilPorIdUseCase {
    
    private final PerfilRepository perfilRepository;

    /**
     * Constructor con inyección de repositorio.
     * 
     * @param perfilRepository Repositorio de perfiles
     */
    public BuscarPerfilPorIdUseCase(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    /**
     * Ejecuta búsqueda de perfil por ID.
     * 
     * @param id ID del perfil
     * @return Perfil encontrado
     * @throws SecurityNotFoundException si perfil no existe
     */
    public Perfil ejecutar(BigInteger id) {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new SecurityNotFoundException("Perfil no encontrado con ID: " + id));
    }
}
