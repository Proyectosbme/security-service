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
 * Orquesta operaciones de perfil como implementación de {@link PerfilInputPort}.
 * 
 * Responsabilidad:
 * 1. Instanciar casos de uso de perfil
 * 2. Delegar operaciones de creación, consulta, actualización y eliminación
 * 3. Exponer un API de aplicación estable
 * 
 * Patrón: Application Service / Facade
 * 
 * Flujo:
 * Controller → PerfilInputPort → PerfilService → UseCases → Repositorio
 * 
 * Excepciones:
 * - SecurityNotFoundException: cuando el perfil no existe
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
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
