package security.aplication.usecase;

import security.aplication.port.output.MenuRepository;
import security.dominio.entidades.Menu;


public class ActualizarMenuUseCase {
    private final MenuRepository menuRepository;

    public ActualizarMenuUseCase(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }


    public Menu ejecutar(Long id, Menu datos){
        // 1. Validación de negocio
        datos.validar();

        // 2. Orquestación (no persistencia)
        return menuRepository.update(id, datos);
    }
}
