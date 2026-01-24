package security.aplication.usecase;

import security.aplication.port.output.PantallaRepository;
import security.dominio.exceptions.SecurityNotFoundException;

/**
 * Caso de Uso: EliminarPantallaUseCase
 * 
 * Orquesta la eliminación de una pantalla existente.
 * 
 * Responsabilidad:
 * 1. Validar el identificador
 * 2. Verificar existencia
 * 3. Eliminar registro en BD
 * 
 * Patrón: Use Case / Command Pattern
 * 
 * Flujo:
 * Validar ID → Verificar existencia → Eliminar
 * 
 * Excepciones:
 * - SecurityNotFoundException: si la pantalla no existe
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class EliminarPantallaUseCase {
    
    private final PantallaRepository pantallaRepository;

    /**
     * Constructor con inyección de repositorio.
     * 
     * @param pantallaRepository Repositorio de pantallas
     */
    public EliminarPantallaUseCase(PantallaRepository pantallaRepository) {
        this.pantallaRepository = pantallaRepository;
    }

    /**
     * Ejecuta eliminación de pantalla.
     * 
     * @param id ID de pantalla a eliminar
     * @throws SecurityNotFoundException si pantalla no existe
     */
    public void ejecutar(Long id) {
        // 1. Validar ID
        if (id == null || id <= 0) {
            throw new SecurityNotFoundException("ID de pantalla inválido: " + id);
        }
        
        // 2. Verificar que pantalla existe antes de eliminar
        if (!pantallaRepository.findById(id).isPresent()) {
            throw new SecurityNotFoundException("Pantalla no encontrada con ID: " + id);
        }
        
        // 3. Eliminar de BD
        pantallaRepository.deleteById(id);
    }
}
