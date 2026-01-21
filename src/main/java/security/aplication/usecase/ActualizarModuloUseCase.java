package security.aplication.usecase;

import security.aplication.port.output.ModuloRepository;
import security.dominio.entidades.Modulo;
import security.dominio.exceptions.SecurityNotFoundException;

public class ActualizarModuloUseCase {
    private final ModuloRepository moduloRepository;

    public ActualizarModuloUseCase(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    public Modulo ejecutar(Long id, Modulo datosNuevos){

        datosNuevos.validar();
        if (!moduloRepository.findById(id).isPresent()) {
            throw new SecurityNotFoundException("Módulo no encontrado con id: " + id);
        }
       return moduloRepository.update(id, datosNuevos);
    }
}
