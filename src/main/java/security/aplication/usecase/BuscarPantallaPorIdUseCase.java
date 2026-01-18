package security.aplication.usecase;

import security.aplication.port.output.PantallaRepository;
import security.dominio.entidades.Pantalla;
import security.dominio.exceptions.SecurityNotFoundException;

/**
 * Caso de Uso: BuscarPantallaPorIdUseCase
 * 
 * Responsabilidad: Buscar una pantalla específica por su identificador único.
 * 
 * Flujo:
 * 1. Recibir ID de pantalla (desde Controller)
 * 2. Consultar BD mediante PantallaRepository
 * 3. Validar que pantalla existe
 * 4. Retornar pantalla encontrada
 * 5. Si no existe, lanzar SecurityNotFoundException
 * 
 * Validaciones:
 * - ID no nulo y válido
 * - Pantalla debe existir en BD
 * 
 * Excepción:
 * - SecurityNotFoundException: Cuando pantalla no existe
 * 
 * @param id Identificador único de pantalla
 * @return Pantalla encontrada
 * @throws SecurityNotFoundException si pantalla no existe
 */
public class BuscarPantallaPorIdUseCase {
    
    private final PantallaRepository pantallaRepository;

    /**
     * Constructor con inyección de repositorio.
     * 
     * @param pantallaRepository Repositorio de pantallas
     */
    public BuscarPantallaPorIdUseCase(PantallaRepository pantallaRepository) {
        this.pantallaRepository = pantallaRepository;
    }

    /**
     * Ejecuta búsqueda de pantalla por ID.
     * 
     * @param id ID de pantalla a buscar
     * @return Pantalla encontrada
     * @throws SecurityNotFoundException si pantalla no existe
     */
    public Pantalla ejecutar(Long id) {
        // 1. Validar ID
        if (id == null || id <= 0) {
            throw new SecurityNotFoundException("ID de pantalla inválido: " + id);
        }
        
        // 2. Buscar en BD
        return pantallaRepository.findById(id)
                .orElseThrow(() -> new SecurityNotFoundException("Pantalla no encontrada con ID: " + id));
    }
}
