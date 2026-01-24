package security.aplication.service;

import jakarta.inject.Inject;
import security.aplication.port.input.PantallaInputPort;
import security.aplication.port.output.ModuloRepository;
import security.aplication.port.output.PantallaRepository;
import security.aplication.usecase.*;
import security.dominio.entidades.Pantalla;
import security.dominio.exceptions.SecurityNotFoundException;
import security.dominio.exceptions.SecurityValidationException;

import java.util.List;

/**
 * Servicio de Aplicación: PantallaService
 * 
 * Orquesta operaciones de pantalla como implementación de {@link PantallaInputPort}.
 * 
 * Responsabilidad:
 * 1. Instanciar casos de uso de pantalla
 * 2. Delegar operaciones de creación, consulta, actualización y eliminación
 * 3. Exponer un API de aplicación estable
 * 
 * Patrón: Application Service / Facade
 * 
 * Flujo:
 * Controller → PantallaInputPort → PantallaService → UseCases → Repositorios
 * 
 * Excepciones:
 * - SecurityValidationException: datos inválidos
 * - SecurityNotFoundException: pantalla no encontrada
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class PantallaService implements PantallaInputPort {

    
    // Casos de uso inyectables
    private final CrearPantallaUseCase crearUseCase;
    private final BuscarPantallaPorIdUseCase buscarUseCase;
    private final ActualizarPantallaUseCase actualizarUseCase;
    private final EliminarPantallaUseCase eliminarUseCase;
    private final ObtenerPantallasUseCase obtenerPantallasUseCase;

    /**
     * Constructor con inyección de repositorio.
     * 
     * Los casos de uso se inicializan con el repositorio inyectado.
     * 
     * @param pantallaRepository Repositorio de pantallas (inyectado por CDI)
     */
    @Inject
    public PantallaService(PantallaRepository pantallaRepository, ModuloRepository moduloRepository) {
        // Inicializar casos de uso con repositorio
        this.crearUseCase = new CrearPantallaUseCase(pantallaRepository);
        this.buscarUseCase = new BuscarPantallaPorIdUseCase(pantallaRepository);
        this.actualizarUseCase = new ActualizarPantallaUseCase(pantallaRepository);
        this.eliminarUseCase = new EliminarPantallaUseCase(pantallaRepository);
        this.obtenerPantallasUseCase = new ObtenerPantallasUseCase(pantallaRepository,moduloRepository);
    }

    /**
     * Crea nueva pantalla.
     * 
     * Flujo:
     * 1. Delega a CrearPantallaUseCase
     * 2. UseCase valida datos y registra auditoría de creación
     * 3. Persiste en BD mediante repositorio
     * 4. Retorna pantalla con ID asignado
     * 
     * @param pantalla Pantalla a crear (sin ID, sin auditoría)
     * @return Pantalla creada con ID asignado por BD
     * @throws SecurityValidationException si datos inválidos
     */
    @Override
    public Pantalla crear(Pantalla pantalla) {
        return crearUseCase.ejecutar(pantalla);
    }

    /**
     * Busca pantalla por ID.
     * 
     * Flujo:
     * 1. Delega a BuscarPantallaPorIdUseCase
     * 2. UseCase consulta BD y valida existencia
     * 3. Retorna pantalla encontrada
     * 
     * @param id ID de pantalla a buscar
     * @return Pantalla encontrada
     * @throws SecurityNotFoundException si pantalla no existe
     */
    @Override
    public Pantalla buscarPorId(Long id) {
        return buscarUseCase.ejecutar(id);
    }

    @Override
    public List<Pantalla> obtenerTodas() {

        return obtenerPantallasUseCase.ejecutar();
    }

    /**
     * Actualiza pantalla existente.
     * 
     * Flujo:
     * 1. Delega a ActualizarPantallaUseCase
     * 2. UseCase verifica existencia y valida nuevos datos
     * 3. Preserva auditoría de creación (userC, fechaC)
     * 4. Registra auditoría de modificación (userMod, fechaMod)
     * 5. Persiste cambios en BD
     * 6. Retorna pantalla actualizada
     * 
     * @param id ID de pantalla a actualizar
     * @param pantalla Pantalla con datos actualizados (sin auditoría)
     * @return Pantalla actualizada con nueva auditoría de modificación
     * @throws SecurityNotFoundException si pantalla no existe
     * @throws SecurityValidationException si datos inválidos
     */
    @Override
    public Pantalla actualizar(Long id, Pantalla pantalla) {
        return actualizarUseCase.ejecutar(id, pantalla);
    }

    /**
     * Elimina pantalla.
     * 
     * Flujo:
     * 1. Delega a EliminarPantallaUseCase
     * 2. UseCase verifica existencia
     * 3. Elimina de BD
     * 
     * @param id ID de pantalla a eliminar
     * @throws SecurityNotFoundException si pantalla no existe
     */
    @Override
    public void eliminar(Long id) {
        eliminarUseCase.ejecutar(id);
    }
}
