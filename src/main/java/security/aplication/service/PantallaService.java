package security.aplication.service;

import jakarta.inject.Inject;
import security.aplication.port.input.PantallaInputPort;
import security.aplication.port.output.PantallaRepository;
import security.aplication.usecase.ActualizarPantallaUseCase;
import security.aplication.usecase.BuscarPantallaPorIdUseCase;
import security.aplication.usecase.CrearPantallaUseCase;
import security.aplication.usecase.EliminarPantallaUseCase;
import security.dominio.entidades.Pantalla;
import security.dominio.exceptions.SecurityNotFoundException;
import security.dominio.exceptions.SecurityValidationException;

import java.util.List;

/**
 * Servicio: PantallaService
 * 
 * Responsabilidad: Orquestar los casos de uso de pantalla como implementación del puerto de entrada.
 * 
 * Patrón Hexagonal:
 * - Implementa PantallaInputPort (puerto de entrada)
 * - Orquesta CrearPantallaUseCase, BuscarPantallaPorIdUseCase, ActualizarPantallaUseCase, EliminarPantallaUseCase
 * - Depende de PantallaRepository (puerto de salida) inyectado
 * - Se expone a través de ApplicationConfig como bean CDI
 * 
 * Flujo de Inyección:
 * 1. ApplicationConfig.pantallasInputPort() produce instancia de PantallaService
 * 2. PantallaService inyecta PantallaRepository (inyectado por ApplicationConfig)
 * 3. PantallaController inyecta PantallaInputPort (que es PantallaService)
 * 4. Llamadas HTTP → Controller → Service → UseCases → Repository
 * 
 * Auditoría:
 * - Crear: Registra userC y fechaC (creador y fecha de creación)
 * - Actualizar: Preserva userC/fechaC, registra userMod y fechaMod (modificador y fecha)
 * - Eliminar: Elimina registro (sin auditoría de eliminación en esta versión)
 * 
 * Excepciones:
 * - SecurityValidationException: Datos inválidos o violación de reglas de negocio
 * - SecurityNotFoundException: Pantalla no encontrada
 * 
 * Scopes:
 * - Se produce como bean mediante @Produces en ApplicationConfig (evita ambigüedad)
 * 
 * @author Security Team
 * @version 1.0
 */
public class PantallaService implements PantallaInputPort {

    
    // Casos de uso inyectables
    private final CrearPantallaUseCase crearUseCase;
    private final BuscarPantallaPorIdUseCase buscarUseCase;
    private final ActualizarPantallaUseCase actualizarUseCase;
    private final EliminarPantallaUseCase eliminarUseCase;

    /**
     * Constructor con inyección de repositorio.
     * 
     * Los casos de uso se inicializan con el repositorio inyectado.
     * 
     * @param pantallaRepository Repositorio de pantallas (inyectado por CDI)
     */
    @Inject
    public PantallaService(PantallaRepository pantallaRepository) {
        // Inicializar casos de uso con repositorio
        this.crearUseCase = new CrearPantallaUseCase(pantallaRepository);
        this.buscarUseCase = new BuscarPantallaPorIdUseCase(pantallaRepository);
        this.actualizarUseCase = new ActualizarPantallaUseCase(pantallaRepository);
        this.eliminarUseCase = new EliminarPantallaUseCase(pantallaRepository);
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
        return List.of();
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
