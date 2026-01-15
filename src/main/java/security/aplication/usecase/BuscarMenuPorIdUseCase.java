package security.aplication.usecase;

import security.aplication.port.output.MenuRepository;
import security.dominio.entidades.Menu;
import security.dominio.exceptions.MenusNotFoundException;

public class BuscarMenuPorIdUseCase {
    private final MenuRepository menuRepository;

    public BuscarMenuPorIdUseCase(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Menu ejecutar(Long id){
        return menuRepository.findById(id).orElseThrow(()->new MenusNotFoundException(id));
    }
}
