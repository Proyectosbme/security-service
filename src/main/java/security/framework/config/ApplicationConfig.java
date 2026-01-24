package security.framework.config;

import security.aplication.port.input.MenuInputPort;
import security.aplication.port.input.ModuloInputPort;
import security.aplication.port.input.PantallaInputPort;
import security.aplication.port.input.PerfilInputPort;
import security.aplication.port.output.MenuRepository;
import security.aplication.port.output.ModuloRepository;
import security.aplication.port.output.PantallaRepository;
import security.aplication.port.output.PerfilRepository;
import security.aplication.service.MenuService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import security.aplication.service.ModuloService;
import security.aplication.service.PantallaService;
import security.aplication.service.PerfilService;
import security.aplication.usecase.ActualizarPerfilUseCase;
import security.aplication.usecase.BuscarPerfilPorIdUseCase;
import security.aplication.usecase.CrearPerfilUseCase;
import security.aplication.usecase.EliminarPerfilUseCase;

/**
 * Configuración de aplicación para el contenedor CDI de Quarkus.
 * <p>
 * Esta clase actúa como fábrica/producer de servicios de la capa de aplicación,
 * resolviendo dependencias y exponiendo instancias singleton para inyección.
 * </p>
 * <p>
 * Responsabilidades principales:
 * </p>
 * <ul>
 *   <li>Inyectar implementaciones de repositorios.</li>
 *   <li>Producir servicios como puertos de entrada.</li>
 *   <li>Gestionar dependencias entre capas (hexagonal).</li>
 * </ul>
 * <p>
 * Flujo: Quarkus CDI → ApplicationConfig → servicios producidos → controladores.
 * </p>
 */
@ApplicationScoped
public class ApplicationConfig {
    /** Repositorio de menús inyectado por CDI. */
    private final MenuRepository menuRepository;
    /** Repositorio de módulos inyectado por CDI. */
    private final ModuloRepository moduloRepository;
    /** Repositorio de pantallas inyectado por CDI. */
    private final PantallaRepository pantallaRepository;
    /** Repositorio de perfiles inyectado por CDI. */
    private final PerfilRepository perfilRepository;

    /**
     * Constructor con inyección de repositorios.
     * <p>
     * Quarkus CDI inyecta automáticamente las implementaciones disponibles.
     * </p>
     *
     * @param menuRepository implementación del repositorio de menús
     * @param moduloRepository implementación del repositorio de módulos
     * @param pantallaRepository implementación del repositorio de pantallas
     * @param perfilRepository implementación del repositorio de perfiles
     */
    public ApplicationConfig(MenuRepository menuRepository, ModuloRepository moduloRepository, PantallaRepository pantallaRepository, PerfilRepository perfilRepository) {
        this.menuRepository = menuRepository;
        this.moduloRepository = moduloRepository;
        this.pantallaRepository = pantallaRepository;
        this.perfilRepository = perfilRepository;
    }

    /**
     * Produce instancia singleton de {@link MenuInputPort} ({@link MenuService}).
     * <p>
     * Patrón hexagonal:
     * </p>
     * <ul>
     *   <li>{@link MenuService} implementa el puerto de entrada {@link MenuInputPort}.</li>
     *   <li>Depende de {@link MenuRepository}, {@link ModuloRepository} y {@link PantallaRepository}.</li>
     * </ul>
     *
     * @return servicio configurado con sus dependencias
     */
    @Produces
    @ApplicationScoped
    public MenuInputPort menuService() {
        return new MenuService(menuRepository, moduloRepository, pantallaRepository);
    }

    /**
     * Produce instancia singleton de {@link ModuloInputPort} ({@link ModuloService}).
     *
     * @return servicio configurado con su repositorio
     */
    @Produces
    @ApplicationScoped
    public ModuloInputPort moduloService(){
        return new ModuloService(moduloRepository);
    }

    /**
     * Produce instancia singleton de {@link PantallaInputPort} ({@link PantallaService}).
     * <p>
     * Patrón hexagonal: el servicio implementa el puerto de entrada y depende del
     * repositorio como puerto de salida.
     * </p>
     *
     * @return servicio configurado con sus repositorios
     */
    @Produces
    @ApplicationScoped
    public PantallaInputPort pantallaService() {
        return new PantallaService(pantallaRepository, moduloRepository);
    }

    /**
     * Produce instancia singleton de {@link PerfilInputPort} ({@link PerfilService}).
     * <p>
     * Patrón hexagonal: el servicio implementa el puerto de entrada y depende del
     * repositorio como puerto de salida.
     * </p>
     *
     * @return servicio configurado con su repositorio
     */
    @Produces
    @ApplicationScoped
    public PerfilInputPort perfilService() {
        return new PerfilService(perfilRepository);
    }
}
