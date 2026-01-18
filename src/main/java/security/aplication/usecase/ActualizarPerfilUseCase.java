package security.aplication.usecase;

import security.aplication.port.output.PerfilRepository;
import security.dominio.entidades.Perfil;
import security.dominio.exceptions.SecurityNotFoundException;
import security.dominio.exceptions.SecurityValidationException;

import java.math.BigInteger;

/**
 * Caso de Uso: ActualizarPerfilUseCase
 * 
 * Responsabilidad: Actualizar datos de un perfil existente.
 * 
 * Flujo:
 * 1. Recibir ID y datos nuevos de perfil
 * 2. Validar que perfil existe
 * 3. Validar nuevos datos
 * 4. Actualizar en BD
 * 5. Retornar perfil actualizado
 * 
 * Validaciones:
 * - ID debe corresponder a perfil existente
 * - Nombre no puede estar vacío
 * 
 * @author Security Team
 * @version 1.0
 */
public class ActualizarPerfilUseCase {
    
    private final PerfilRepository perfilRepository;

    /**
     * Constructor con inyección de repositorio.
     * 
     * @param perfilRepository Repositorio de perfiles
     */
    public ActualizarPerfilUseCase(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    /**
     * Ejecuta actualización de perfil.
     * 
     * @param id ID del perfil a actualizar
     * @param perfilNuevo Perfil con datos actualizados
     * @return Perfil actualizado
     * @throws SecurityNotFoundException si perfil no existe
     * @throws SecurityValidationException si datos inválidos
     */
    public Perfil ejecutar(BigInteger id, Perfil perfilNuevo) {
        // 1. Validar que perfil existe
        Perfil perfilExistente = perfilRepository.findById(id)
                .orElseThrow(() -> new SecurityNotFoundException("Perfil no encontrado con ID: " + id));
        
        // 2. Validar nuevos datos
        if (perfilNuevo == null) {
            throw new SecurityValidationException("Perfil no puede ser nulo");
        }
        
        if (perfilNuevo.getNombre() == null || perfilNuevo.getNombre().isBlank()) {
            throw new SecurityValidationException("Nombre del perfil no puede estar vacío");
        }
        
        // 3. Actualizar datos
        perfilExistente.setNombre(perfilNuevo.getNombre());
        
        // 4. Persistir cambios
        return perfilRepository.update(id, perfilExistente);
    }
}
