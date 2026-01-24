package security.aplication.usecase;

import security.aplication.dto.FiltroMenu;
import security.aplication.port.output.MenuRepository;
import security.dominio.entidades.Menu;

import java.util.List;

/**
 * Caso de Uso: BuscarMenuPorFiltros
 * 
 * Orquesta la búsqueda avanzada de menús usando criterios de filtro.
 * 
 * Responsabilidad:
 * 1. Recibir criterios de búsqueda
 * 2. Delegar consulta al repositorio
 * 3. Retornar coincidencias
 * 
 * Patrón: Use Case / Query Pattern
 * 
 * Flujo:
 * Filtrar → Consultar → Retornar
 * 
 * Excepciones:
 * - Ninguna
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
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
