package security.aplication.usecase;

import security.aplication.port.output.MenuRepository;
import security.dominio.entidades.Menu;
import security.dominio.exceptions.SecurityNotFoundException;

/**
 * Caso de Uso: BuscarMenuPorIdUseCase
 * 
 * Orquesta la búsqueda de un menú por su identificador único.
 * 
 * Responsabilidad:
 * 1. Consultar el repositorio por ID
 * 2. Retornar el menú si existe
 * 3. Lanzar excepción si no existe
 * 
 * Patrón: Use Case / Query Pattern
 * 
 * Flujo:
 * Buscar → Validar existencia → Retornar
 * 
 * Excepciones:
 * - SecurityNotFoundException: si el menú no existe
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class BuscarMenuPorIdUseCase {
    private final MenuRepository menuRepository;

    /**
     * Constructor con inyección de dependencia.
     */
    public BuscarMenuPorIdUseCase(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    /**
     * Ejecuta búsqueda de menú por ID.
     * @param id Identificador a buscar
     * @return Menu encontrado
     * @throws SecurityNotFoundException si no existe
     */
    public Menu ejecutar(Long id){
        return menuRepository.findById(id)
                .orElseThrow(()->new SecurityNotFoundException(id.toString()));
    }
}
