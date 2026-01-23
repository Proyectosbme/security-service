package security.aplication.service;

import security.aplication.port.input.PerfilInputPort;
import security.aplication.port.output.PerfilRepository;
import security.aplication.usecase.*;
import security.dominio.entidades.Perfil;
import security.dominio.exceptions.SecurityNotFoundException;

import java.math.BigInteger;
import java.util.List;

/**
 * Servicio de Aplicación: PerfilService
 * 
 * Implementa PerfilInputPort para orquestar operaciones CRUD sobre perfiles.
 * 
 * Patrón: Application Service (Hexagonal Architecture)
 * Responsabilidad: Coordinar casos de uso y delegar a repositorio
 * 
 * Flujo:
 * Controller → PerfilInputPort → PerfilService → PerfilRepository → BD
 * 
 * Características:
 * - Validación de existencia antes de operaciones (buscar, eliminar, actualizar)
 * - Lanza SecurityNotFoundException cuando perfil no existe
 * - Delega creación a CrearPerfilUseCase (patrón UseCase)
 * - Operaciones directas al repositorio para CRUD básico
 * 
 * @see PerfilInputPort Puerto de entrada implementado
 * @see PerfilRepository Puerto de salida para persistencia
 * @see CrearPerfilUseCase Caso de uso de creación
 */
public class PerfilService implements PerfilInputPort {
    
    private final CrearPerfilUseCase crearPerfilUseCase;
    private final ObtenerPerfilesUseCase obtenerPerfilesUseCase;
    private final EliminarPerfilUseCase eliminarPerfilUseCase;
    private final ActualizarPerfilUseCase actualizarPerfilUseCase;
    private final BuscarPerfilPorIdUseCase buscarPerfilPorIdUseCase;

    /**
     * Constructor con inyección de dependencias.
     * 
     * Inicializa el caso de uso de creación y guarda referencia al repositorio
     * para operaciones directas de lectura, actualización y eliminación.
     * 
     * @param perfilRepository Repositorio de persistencia de perfiles
     */
    public PerfilService(PerfilRepository perfilRepository) {
        this.crearPerfilUseCase = new CrearPerfilUseCase(perfilRepository);
        this.obtenerPerfilesUseCase = new ObtenerPerfilesUseCase(perfilRepository);
        this.eliminarPerfilUseCase = new EliminarPerfilUseCase(perfilRepository);
        this.actualizarPerfilUseCase = new ActualizarPerfilUseCase(perfilRepository);
        this.buscarPerfilPorIdUseCase = new BuscarPerfilPorIdUseCase(perfilRepository);


    }

    /**
     * Crea un nuevo perfil en el sistema.
     * 
     * Delega la lógica de creación al caso de uso CrearPerfilUseCase,
     * que puede contener validaciones de negocio adicionales.
     * 
     * @param perfil Perfil a crear (sin ID)
     * @return Perfil creado con ID asignado por BD
     */
    @Override
    public Perfil crear(Perfil perfil) {
        return crearPerfilUseCase.ejecutar(perfil);
    }

    /**
     * Busca un perfil por su identificador.
     * 
     * @param id Identificador del perfil
     * @return Perfil encontrado
     * @throws SecurityNotFoundException Si el perfil no existe
     */
    @Override
    public Perfil buscarPorId(BigInteger id) {
        return buscarPerfilPorIdUseCase.ejecutar(id);
    }

    /**
     * Actualiza un perfil existente.
     * 
     * Valida que el perfil exista antes de aplicar cambios.
     * 
     * @param id Identificador del perfil a actualizar
     * @param perfil Perfil con nuevos datos
     * @return Perfil actualizado
     * @throws SecurityNotFoundException Si el perfil no existe
     */
    @Override
    public Perfil actualizar(BigInteger id, Perfil perfil) {
      return actualizarPerfilUseCase.ejecutar(id, perfil);
    }

    /**
     * Elimina un perfil del sistema.
     * 
     * Valida que el perfil exista antes de eliminarlo.
     * 
     * @param id Identificador del perfil a eliminar
     * @throws SecurityNotFoundException Si el perfil no existe
     */
    @Override
    public void eliminar(BigInteger id) {
       this.eliminarPerfilUseCase.ejecutar(id);
    }

    @Override
    public List<Perfil> obtenerTodos() {
        return obtenerPerfilesUseCase.ejecutar();
    }
}
