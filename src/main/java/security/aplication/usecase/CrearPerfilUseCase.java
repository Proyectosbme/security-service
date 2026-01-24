package security.aplication.usecase;

import security.aplication.port.output.PerfilRepository;
import security.dominio.entidades.Perfil;
import security.dominio.exceptions.SecurityValidationException;

/**
 * Caso de Uso: CrearPerfilUseCase
 * 
 * Orquesta la creación de un perfil con validación de negocio.
 * 
 * Responsabilidad:
 * 1. Validar datos del perfil
 * 2. Persistir perfil en BD
 * 3. Retornar perfil creado
 * 
 * Patrón: Use Case / Command Pattern
 * 
 * Flujo:
 * Validar → Persistir → Retornar
 * 
 * Excepciones:
 * - SecurityValidationException: si datos del perfil son inválidos
 * 
 * @author bme(Bryan Ivan Marroquin)
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
