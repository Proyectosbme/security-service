package security.aplication.usecase;

import security.aplication.dto.FiltroMenu;
import security.aplication.port.output.MenuRepository;
import security.dominio.entidades.Menu;

import java.util.List;

/**
 * Caso de Uso: BuscarMenuPorFiltros
 * Busca menús aplicando criterios de filtro avanzado.
 * Patrón: Use Case / Query Pattern
 */
public class BuscarMenuPorFiltros {

    private final MenuRepository menuRepository;

    /**
     * Constructor con inyección de dependencia.
     */
    public BuscarMenuPorFiltros(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    /**
     * Ejecuta búsqueda con filtros.
     * @param filtroMenu Criterios de búsqueda
     * @return Lista de menús coincidentes
     */
    public List<Menu> ejecutar(FiltroMenu filtroMenu){
       return menuRepository.buscarMenuPorFiltros(filtroMenu);
    }
}
