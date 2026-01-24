package security.aplication.usecase;

import security.aplication.port.output.ModuloRepository;
import security.dominio.exceptions.SecurityNotFoundException;

/**
 * Caso de Uso: EliminarModuloUseCase
 * 
 * Orquesta la eliminación de un módulo existente.
 * 
 * Responsabilidad:
 * 1. Validar identificador
 * 2. Verificar existencia del módulo
 * 3. Eliminar registro en BD
 * 
 * Patrón: Use Case / Command Pattern
 * 
 * Flujo:
 * Validar → Verificar existencia → Eliminar
 * 
 * Excepciones:
 * - SecurityNotFoundException: si el ID es inválido o el módulo no existe
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class EliminarModuloUseCase {
    private final ModuloRepository moduloRepository;

    /**
     * Constructor con inyección del repositorio.
     *
     * @param moduloRepository repositorio de módulos
     */
    public EliminarModuloUseCase(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    /**
     * Ejecuta la eliminación del módulo.
     *
     * @param id identificador del módulo
     * @throws SecurityNotFoundException si el ID es inválido o el módulo no existe
     */
    public void ejecutar(Long id){
        // 1. Validar ID
        if (id == null || id <= 0) {
            throw new SecurityNotFoundException("ID de Modulo inválido: " + id);
        }

        // 2. Verificar que pantalla existe antes de eliminar
        if (!moduloRepository.findById(id).isPresent()) {
            throw new SecurityNotFoundException("Modulo no encontrada con ID: " + id);
        }
        moduloRepository.delete(id);
    }
}
