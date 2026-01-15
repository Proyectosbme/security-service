package security.aplication.usecase;

import security.aplication.port.output.MenuRepository;
import security.dominio.entidades.Menu;
import security.dominio.exceptions.MenusNotFoundException;

public class EliminarMenuUseCase {

    private final MenuRepository menuRepository;

    public EliminarMenuUseCase(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void ejecutar(Long id){
        boolean existe = menuRepository.findById(id).isPresent();

        if(!existe){
            throw new MenusNotFoundException(id);
        }
        menuRepository.deleteById(id);
    }
}
