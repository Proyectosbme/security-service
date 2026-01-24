package security.aplication.usecase;

import security.aplication.port.output.ModuloRepository;
import security.dominio.entidades.Modulo;
import security.dominio.exceptions.SecurityNotFoundException;

/**
 * Caso de Uso: ActualizarModuloUseCase
 * 
 * Orquesta la actualización de un módulo existente con validación previa.
 * 
 * Responsabilidad:
 * 1. Validar datos nuevos del módulo
 * 2. Verificar existencia del módulo
 * 3. Actualizar módulo en BD
 * 
 * Patrón: Use Case / Command Pattern
 * 
 * Flujo:
 * Validar → Verificar existencia → Actualizar
 * 
 * Excepciones:
 * - SecurityNotFoundException: si el módulo no existe
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class ActualizarModuloUseCase {
    private final ModuloRepository moduloRepository;

    /**
     * Constructor con inyección del repositorio.
     *
     * @param moduloRepository repositorio de módulos
     */
    public ActualizarModuloUseCase(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    /**
     * Ejecuta la actualización del módulo.
     *
     * @param id identificador del módulo a actualizar
     * @param datosNuevos datos nuevos del módulo
     * @return módulo actualizado
     * @throws SecurityNotFoundException si el módulo no existe
     */
    public Modulo ejecutar(Long id, Modulo datosNuevos){

        datosNuevos.validar();
        if (!moduloRepository.findById(id).isPresent()) {
            throw new SecurityNotFoundException("Módulo no encontrado con id: " + id);
        }
       return moduloRepository.update(id, datosNuevos);
    }
}
