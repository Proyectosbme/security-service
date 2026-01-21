package security.aplication.usecase;

import security.aplication.port.output.ModuloRepository;
import security.dominio.entidades.Modulo;
import java.util.List;

public class ObtenerModulosUseCase {

    private final ModuloRepository moduloRepository;

    public ObtenerModulosUseCase(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    public List<Modulo> ejecutar(){
      return  moduloRepository.findAll();
    }
}
