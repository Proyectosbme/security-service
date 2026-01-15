package security.aplication.usecase;

import security.aplication.dto.FiltroMenu;
import security.aplication.port.output.MenuRepository;
import security.dominio.entidades.Menu;

import java.util.List;

public class BuscarMenuPorFiltros {

    private final MenuRepository menuRepository;

    public BuscarMenuPorFiltros(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> ejecutar(FiltroMenu filtroMenu){
       return menuRepository.buscarMenuPorFiltros(filtroMenu);
    }
}
