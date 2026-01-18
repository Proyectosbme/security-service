package security.aplication.usecase;

import security.aplication.port.output.PerfilRepository;
import security.dominio.entidades.Perfil;
import security.dominio.exceptions.SecurityNotFoundException;

import java.math.BigInteger;

/**
 * Caso de Uso: BuscarPerfilPorIdUseCase
 * 
 * Responsabilidad: Buscar un perfil por su ID.
 * 
 * Flujo:
 * 1. Recibir ID de perfil
 * 2. Buscar en repositorio
 * 3. Retornar perfil encontrado o lanzar excepción
 * 
 * @author Security Team
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
