package security.aplication.usecase;

import security.aplication.port.output.MenuRepository;
import security.aplication.port.output.ModuloRepository;
import security.dominio.entidades.Menu;
import security.dominio.entidades.Modulo;
import security.dominio.exceptions.MenusNotFoundException;

public class BuscarModuloPorIdUseCase {
    private final ModuloRepository moduloRepository;

    public BuscarModuloPorIdUseCase(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    public Modulo ejecutar(Long id){
        return moduloRepository.findById(id).orElseThrow(()->new MenusNotFoundException(id));
    }
}
