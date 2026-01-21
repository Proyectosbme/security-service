package security.aplication.service;

import security.aplication.port.input.ModuloInputPort;
import security.aplication.port.output.ModuloRepository;
import security.aplication.usecase.*;
import security.dominio.entidades.Modulo;
import security.dominio.exceptions.SecurityNotFoundException;

import java.util.List;

/**
 * Servicio de Aplicación: ModuloService
 * 
 * Implementa ModuloInputPort para orquestar operaciones CRUD sobre módulos.
 * 
 * Patrón: Application Service (Hexagonal Architecture)
 * Responsabilidad: Coordinar casos de uso y delegar a repositorio
 * 
 * Flujo:
 * Controller → ModuloInputPort → ModuloService → ModuloRepository → BD
 * 
 * Características:
 * - Validación de existencia antes de operaciones (buscar, eliminar, actualizar)
 * - Lanza SecurityNotFoundException cuando módulo no existe
 * - Delega creación a CrearModuloUseCase (patrón UseCase)
 * - Operaciones directas al repositorio para CRUD básico
 * 
 * @see ModuloInputPort Puerto de entrada implementado
 * @see ModuloRepository Puerto de salida para persistencia
 * @see CrearModuloUseCase Caso de uso de creación
 */
public class ModuloService implements ModuloInputPort {

    private final CrearModuloUseCase crearModuloUseCase;
    private final BuscarModuloPorIdUseCase buscarModuloPorIdUseCase;
    private final EliminarModuloUseCase eliminarModuloUseCase;
    private final ObtenerModulosUseCase obtenerModulosUseCase;
    private final ActualizarModuloUseCase actualizarModuloUseCase;


    /**
     * Constructor con inyección de dependencias.
     * 
     * Inicializa el caso de uso de creación y guarda referencia al repositorio
     * para operaciones directas de lectura, actualización y eliminación.
     * 
     * @param moduloRepository Repositorio de persistencia de módulos
     */
    public ModuloService(ModuloRepository moduloRepository) {
        this.crearModuloUseCase = new CrearModuloUseCase(moduloRepository);
        this.buscarModuloPorIdUseCase = new BuscarModuloPorIdUseCase(moduloRepository);
        this.eliminarModuloUseCase = new EliminarModuloUseCase(moduloRepository);
        this.obtenerModulosUseCase = new ObtenerModulosUseCase(moduloRepository);
        this.actualizarModuloUseCase = new ActualizarModuloUseCase(moduloRepository);
    }

    /**
     * Crea un nuevo módulo en el sistema.
     * 
     * Delega la lógica de creación al caso de uso CrearModuloUseCase,
     * que puede contener validaciones de negocio adicionales.
     * 
     * @param modulo Módulo a crear (sin ID)
     * @return Módulo creado con ID asignado por BD
     */
    @Override
    public Modulo crear(Modulo modulo) {

        return crearModuloUseCase.ejecutar(modulo);
    }

    /**
     * Busca un módulo por su identificador.
     * 
     * @param id Identificador del módulo
     * @return Módulo encontrado
     * @throws SecurityNotFoundException Si el módulo no existe
     */
    @Override
    public Modulo buscarPorId(Long id) {
        return buscarModuloPorIdUseCase.ejecutar(id);
    }

    /**
     * Elimina un módulo del sistema.
     * 
     * Valida que el módulo exista antes de eliminarlo.
     * 
     * @param id Identificador del módulo a eliminar
     * @throws SecurityNotFoundException Si el módulo no existe
     */
    @Override
    public void eliminar(Long id) {
       eliminarModuloUseCase.ejecutar(id);
    }

    /**
     * Obtiene todos los módulos del sistema.
     *
     * @return Lista completa de módulos (puede estar vacía)
     */
    @Override
    public List<Modulo> obtenerTodas() {

        return obtenerModulosUseCase.ejecutar();
    }

    /**
     * Actualiza un módulo existente.
     * 
     * Valida que el módulo exista antes de aplicar cambios.
     * 
     * @param id Identificador del módulo a actualizar
     * @param Modulo Módulo con nuevos datos
     * @return Módulo actualizado
     * @throws SecurityNotFoundException Si el módulo no existe
     */
    @Override
    public Modulo acualizar(Long id, Modulo modulo) {
      return actualizarModuloUseCase.ejecutar(id,modulo);
    }
}
