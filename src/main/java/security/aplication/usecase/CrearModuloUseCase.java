package security.aplication.usecase;

import security.aplication.port.output.ModuloRepository;
import security.dominio.entidades.Modulo;

/**
 * Caso de Uso: CrearModuloUseCase
 * 
 * Orquesta la creación de un módulo con validación de reglas de negocio.
 * 
 * Responsabilidad:
 * 1. Validar datos del módulo
 * 2. Persistir módulo en BD
 * 
 * Patrón: Use Case / Command Pattern
 * 
 * Flujo:
 * Validar → Persistir
 * 
 * Excepciones:
 * - SecurityValidationException: si datos del módulo son inválidos
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class CrearModuloUseCase {

    private final ModuloRepository moduloRepository;

    /**
     * Constructor con inyección de dependencia.
     */
    public CrearModuloUseCase(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    /**
     * Ejecuta creación de módulo.
     * @param modulo Módulo a crear (sin ID)
     * @return Modulo creado con ID asignado
     * @throws SecurityValidationException si incumple reglas
     */
    public Modulo ejecutar(Modulo modulo){
        modulo.validar();
        return moduloRepository.save(modulo);
    }
}
