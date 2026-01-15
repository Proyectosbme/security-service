package security.aplication.usecase;

import security.aplication.port.output.ModuloRepository;
import security.dominio.entidades.Modulo;

public class CrearModuloUseCase {

    private final ModuloRepository moduloRepository;

    public CrearModuloUseCase(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    public Modulo ejecutar(Modulo modulo){
        modulo.validar();
      return moduloRepository.save(modulo);
    }
}
