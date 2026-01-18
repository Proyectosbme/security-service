package security.aplication.usecase;

import security.aplication.port.output.PantallaRepository;
import security.dominio.exceptions.SecurityNotFoundException;

/**
 * Caso de Uso: EliminarPantallaUseCase
 * 
 * Responsabilidad: Eliminar una pantalla existente.
 * 
 * Flujo:
 * 1. Recibir ID de pantalla (desde Controller)
 * 2. Validar que pantalla existe
 * 3. Eliminar de BD mediante PantallaRepository
 * 4. Confirmar eliminación exitosa
 * 
 * Validaciones:
 * - ID debe ser válido
 * - Pantalla debe existir antes de eliminar
 * 
 * Auditoría:
 * - No se registra auditoría de eliminación en esta versión
 * - TODO: Considerar soft-delete con userEliminado/fechaEliminado si se requiere
 * 
 * @param id Identificador único de pantalla
 * @throws SecurityNotFoundException si pantalla no existe
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
