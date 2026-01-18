package security.aplication.usecase;

import security.aplication.port.output.PerfilRepository;
import security.dominio.entidades.Perfil;
import security.dominio.exceptions.SecurityValidationException;

/**
 * Caso de Uso: CrearPerfilUseCase
 * 
 * Responsabilidad: Crear un nuevo perfil con validación de negocio.
 * 
 * Flujo:
 * 1. Recibir datos de perfil
 * 2. Validar datos (nombre no vacío)
 * 3. Persistir en BD
 * 4. Retornar perfil creado con ID asignado
 * 
 * Validaciones:
 * - Perfil no nulo
 * - Nombre no vacío
 * 
 * @author Security Team
 * @version 1.0
 */
public class CrearPerfilUseCase {
    
    private final PerfilRepository perfilRepository;

    /**
     * Constructor con inyección de repositorio.
     * 
     * @param perfilRepository Repositorio de perfiles
     */
    public CrearPerfilUseCase(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    /**
     * Ejecuta creación de perfil.
     * 
     * @param perfil Perfil a crear (sin ID)
     * @return Perfil creado con ID asignado
     * @throws SecurityValidationException si datos inválidos
     */
    public Perfil ejecutar(Perfil perfil) {
        // 1. Validar datos
        if (perfil == null) {
            throw new SecurityValidationException("Perfil no puede ser nulo");
        }
        
        if (perfil.getNombre() == null || perfil.getNombre().isBlank()) {
            throw new SecurityValidationException("Nombre del perfil no puede estar vacío");
        }
        
        // 2. Persistir en BD
        return perfilRepository.save(perfil);
    }
}
