package security.aplication.usecase;

import security.aplication.port.output.PerfilRepository;
import security.dominio.entidades.Perfil;
import security.dominio.exceptions.SecurityNotFoundException;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Caso de Uso: EliminarPerfilUseCase
 * 
 * Responsabilidad: Eliminar un perfil existente.
 * 
 * Flujo:
 * 1. Recibir ID de perfil
 * 2. Verificar que perfil existe
 * 3. Eliminar de BD
 * 
 * @author Security Team
 * @version 1.0
 */
public class EliminarPerfilUseCase {
    
    private final PerfilRepository perfilRepository;

    /**
     * Constructor con inyección de repositorio.
     * 
     * @param perfilRepository Repositorio de perfiles
     */
    public EliminarPerfilUseCase(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    /**
     * Ejecuta eliminación de perfil.
     * 
     * @param id ID del perfil a eliminar
     * @throws SecurityNotFoundException si perfil no existe
     */
    public void ejecutar(BigInteger id) {
        // 1. Verificar que perfil existe
        Optional<Perfil> perfil = perfilRepository.findById(id);
        if (perfil.isEmpty()) {
            throw new SecurityNotFoundException("Perfil no encontrado con ID: " + id);
        }
        
        // 2. Eliminar de BD
        perfilRepository.deleteById(id);
    }
}
