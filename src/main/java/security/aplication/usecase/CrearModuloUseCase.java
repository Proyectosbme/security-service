package security.aplication.usecase;

import security.aplication.port.output.ModuloRepository;
import security.dominio.entidades.Modulo;

/**
 * Caso de Uso: CrearModuloUseCase
 * Crea un nuevo módulo validando reglas de negocio.
 * Patrón: Use Case / Command Pattern
 * Flujo: Validar → Persistir
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
