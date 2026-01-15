package security.aplication.usecase;

import security.aplication.port.output.MenuRepository;
import security.aplication.port.output.ModuloRepository;
import security.dominio.entidades.Menu;
import security.dominio.exceptions.MenusNotFoundException;

public class CrearMenuUseCase {

    private final MenuRepository menuRepository;
    private final ModuloRepository moduloRepository;

    public CrearMenuUseCase(MenuRepository menuRepository,ModuloRepository moduloRepository) {
        this.menuRepository = menuRepository;
        this.moduloRepository= moduloRepository;
    }

    public Menu ejecutar(Menu menu){

        menu.validar();
        if(menu != null  && menu.getModulo() != null && menu.getModulo().getId()!=null){
            moduloRepository.findById(menu.getModulo().getId().longValue())
                    .orElseThrow(()->new MenusNotFoundException("No existe el modulo con id " + menu.getModulo().getId().longValue()));
        }
        return menuRepository.save(menu);
    }
}
