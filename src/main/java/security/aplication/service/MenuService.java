package security.aplication.service;

import security.aplication.dto.FiltroMenu;
import security.aplication.port.input.MenuInputPort;
import security.aplication.port.output.MenuRepository;
import security.aplication.port.output.ModuloRepository;
import security.aplication.usecase.*;
import security.dominio.entidades.Menu;

import java.util.List;

public class MenuService implements MenuInputPort {

    private final CrearMenuUseCase crearMenuUseCase;
    private final BuscarMenuPorIdUseCase buscarMenuPorIdUseCase;
    private final ActualizarMenuUseCase actualizarMenuUseCase;
    private final EliminarMenuUseCase eliminarMenuUseCase;
    private final BuscarMenuPorFiltros buscarMenuPorFiltros;

        public MenuService(MenuRepository menuRepository, ModuloRepository moduloRepository) {
        this.crearMenuUseCase = new CrearMenuUseCase(menuRepository,moduloRepository);
        this.buscarMenuPorIdUseCase = new BuscarMenuPorIdUseCase(menuRepository);
        this.actualizarMenuUseCase = new ActualizarMenuUseCase(menuRepository);
        this.eliminarMenuUseCase = new EliminarMenuUseCase(menuRepository);
        this.buscarMenuPorFiltros = new BuscarMenuPorFiltros(menuRepository);

    }

    @Override
    public Menu crear(Menu menu) {
        return crearMenuUseCase.ejecutar(menu);
    }

    @Override
    public Menu buscarPorId(Long id) {
        return buscarMenuPorIdUseCase.ejecutar(id);

    }

    @Override
    public void eliminar(Long id) {            eliminarMenuUseCase.ejecutar(id);; }

    @Override
    public List<Menu> obtenerTodas() {
        return null;
    }

    @Override
    public Menu acualizar(Long id,Menu menu) {
            return actualizarMenuUseCase.ejecutar(id,menu);
    }

    @Override
    public List<Menu> buscarPorFiltros(FiltroMenu filtroMenu) {
        return buscarMenuPorFiltros.ejecutar(filtroMenu);
    }
}
