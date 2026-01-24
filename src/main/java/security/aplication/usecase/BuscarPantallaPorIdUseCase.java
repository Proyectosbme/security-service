package security.aplication.usecase;

import security.aplication.port.output.PantallaRepository;
import security.dominio.entidades.Pantalla;
import security.dominio.exceptions.SecurityNotFoundException;

/**
 * Caso de Uso: BuscarPantallaPorIdUseCase
 * 
 * Orquesta la búsqueda de una pantalla por su identificador.
 * 
 * Responsabilidad:
 * 1. Validar el identificador
 * 2. Consultar el repositorio
 * 3. Retornar pantalla encontrada
 * 
 * Patrón: Use Case / Query Pattern
 * 
 * Flujo:
 * Validar ID → Buscar → Retornar
 * 
 * Excepciones:
 * - SecurityNotFoundException: si la pantalla no existe
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
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
