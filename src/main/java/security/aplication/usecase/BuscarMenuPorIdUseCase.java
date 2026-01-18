package security.aplication.usecase;

import security.aplication.port.output.MenuRepository;
import security.dominio.entidades.Menu;
import security.dominio.exceptions.SecurityNotFoundException;

/**
 * Caso de Uso: BuscarMenuPorIdUseCase
 * Busca un menú existente por su identificador.
 * Patrón: Use Case / Query Pattern
 * Lanza excepción si no existe
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
