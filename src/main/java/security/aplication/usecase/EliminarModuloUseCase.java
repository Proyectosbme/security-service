package security.aplication.usecase;

import security.aplication.port.output.ModuloRepository;
import security.dominio.exceptions.SecurityNotFoundException;

public class EliminarModuloUseCase {
    private final ModuloRepository moduloRepository;

    public EliminarModuloUseCase(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    public void ejecutar(Long id){
        // 1. Validar ID
        if (id == null || id <= 0) {
            throw new SecurityNotFoundException("ID de Modulo inválido: " + id);
        }

        // 2. Verificar que pantalla existe antes de eliminar
        if (!moduloRepository.findById(id).isPresent()) {
            throw new SecurityNotFoundException("Modulo no encontrada con ID: " + id);
        }
        moduloRepository.delete(id);
    }
}
