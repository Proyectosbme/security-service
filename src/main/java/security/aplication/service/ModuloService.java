package security.aplication.service;

import security.aplication.port.input.ModuloInputPort;
import security.aplication.port.output.ModuloRepository;
import security.aplication.usecase.CrearModuloUseCase;
import security.dominio.entidades.Modulo;

import java.util.List;

/**
 * Servicio de Aplicación: ModuloService
 * Implementa ModuloInputPort y orquesta casos de uso de módulos.
 * Patrón: Service Locator / Facade
 * Nota: Métodos pendientes de implementación (retornan null/vacío)
 */
public class ModuloService implements ModuloInputPort {

    private final CrearModuloUseCase crearModuloUseCase;

    /**
     * Constructor que inyecta dependencia del repositorio.
     */
    public ModuloService(ModuloRepository moduloRepository) {
        this.crearModuloUseCase = new CrearModuloUseCase(moduloRepository);
    }

    @Override
    public Modulo crear(Modulo modulo) {
        return crearModuloUseCase.ejecutar(modulo);
    }

    @Override
    public Modulo buscarPorId(Long id) {
        return null; // TODO: Implementar en próxima versión
    }

    @Override
    public void eliminar(Long id) {
        // TODO: Implementar eliminación de módulos
    }

    @Override
    public List<Modulo> obtenerTodas() {
        return null; // TODO: Implementar en próxima versión
    }

    @Override
    public Modulo acualizar(Long id, Modulo Modulo) {
        return null; // TODO: Implementar actualización de módulos
    }
}
