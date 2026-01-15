package security.aplication.port.input;

import security.aplication.dto.FiltroMenu;
import security.dominio.entidades.Menu;

import java.util.List;

public interface MenuInputPort {
    Menu crear(Menu menu);
    Menu buscarPorId(Long id);
    void eliminar(Long id);
    List<Menu> obtenerTodas();
    Menu acualizar(Long id,Menu menu);
    List<Menu> buscarPorFiltros(FiltroMenu filtroMenu);


}
