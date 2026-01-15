package security.aplication.service;

import security.aplication.port.input.ModuloInputPort;
import security.aplication.port.output.ModuloRepository;
import security.aplication.usecase.CrearModuloUseCase;
import security.dominio.entidades.Modulo;

import java.util.List;

public class ModuloService implements ModuloInputPort {

    private final CrearModuloUseCase crearModuloUseCase;

    public ModuloService(ModuloRepository moduloRepository) {
        this.crearModuloUseCase = new CrearModuloUseCase(moduloRepository);
    }

    @Override
    public Modulo crear(Modulo modulo) {
        return crearModuloUseCase.ejecutar(modulo);
    }

    @Override
    public Modulo buscarPorId(Long id) {
        return null;
    }

    @Override
    public void eliminar(Long id) {

    }

    @Override
    public List<Modulo> obtenerTodas() {
        return null;
    }

    @Override
    public Modulo acualizar(Long id, Modulo Modulo) {
        return null;
    }
}
