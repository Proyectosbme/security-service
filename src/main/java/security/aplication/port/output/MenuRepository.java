package security.aplication.port.output;

import security.aplication.dto.FiltroMenu;
import security.dominio.entidades.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuRepository {
    Menu save(Menu menu);
    Optional<Menu> findById(Long id);
    List<Menu> findAll();
    boolean deleteById(Long id);
    Menu update(Long id,Menu menu);
    void delete(Long id);
    List<Menu> buscarMenuPorFiltros(FiltroMenu filtroMenu);
}
