package security.framework.config;

import security.aplication.port.input.MenuInputPort;
import security.aplication.port.input.ModuloInputPort;
import security.aplication.port.input.PantallaInputPort;
import security.aplication.port.output.MenuRepository;
import security.aplication.port.output.ModuloRepository;
import security.aplication.port.output.PantallaRepository;
import security.aplication.service.MenuService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import security.aplication.service.ModuloService;
import security.aplication.service.PantallaService;

/**
 * Configuración de Aplicación: ApplicationConfig
 * 
 * Clase de configuración de Quarkus CDI que:
 * - Inyecta las implementaciones de repositorios
 * - Produce instancias de servicios singleton
 * - Gestiona las dependencias entre capas
 * 
 * Patrón: Factory / Producer
 * Responsabilidad: Crear e inyectar servicios en el contenedor CDI
 * 
 * Flujo:
 * Quarkus CDI → ApplicationConfig → Produces Services → Controllers
 */
@ApplicationScoped
public class ApplicationConfig {
    /** Repositorio de menús inyectado por CDI */
    private final MenuRepository menuRepository;
    /** Repositorio de módulos inyectado por CDI */
    private final ModuloRepository moduloRepository;
    /** Repositorio de pantallas inyectado por CDI */
    private final PantallaRepository pantallaRepository;

    /**
     * Constructor con inyección de repositorios.
     * Quarkus CDI automáticamente inyecta las implementaciones.
     * 
     * @param menuRepository Implementación del repositorio de menús
     * @param moduloRepository Implementación del repositorio de módulos
     * @param pantallaRepository Implementación del repositorio de pantallas
     */
    public ApplicationConfig(MenuRepository menuRepository, ModuloRepository moduloRepository, PantallaRepository pantallaRepository) {
        this.menuRepository = menuRepository;
        this.moduloRepository = moduloRepository;
        this.pantallaRepository = pantallaRepository;
    }

    /**
     * Produce instancia singleton de MenuInputPort (MenuService).
     * Anotada con @Produces para inyección en CDI.
     * 
     * @return MenuService configurado con sus repositorios
     */
    @Produces
    @ApplicationScoped
    public MenuInputPort menuService() {
        return new MenuService(menuRepository,moduloRepository);
    }

    /**
     * Produce instancia singleton de ModuloInputPort (ModuloService).
     * Anotada con @Produces para inyección en CDI.
     * 
     * @return ModuloService configurado con su repositorio
     */
    @Produces
    @ApplicationScoped
    public ModuloInputPort moduloService(){
        return new ModuloService(moduloRepository);
    }

    /**
     * Produce instancia singleton de PantallaInputPort (PantallaService).
     * Anotada con @Produces para inyección en CDI.
     * 
     * Patrón Hexagonal:
     * - PantallaService implementa PantallaInputPort (puerto de entrada)
     * - Depende de PantallaRepository (puerto de salida)
     * - Se inyecta en PantallaController y en clientes del servicio
     * 
     * @return PantallaService configurado con su repositorio
     */
    @Produces
    @ApplicationScoped
    public PantallaInputPort pantallaService() {
        return new PantallaService(pantallaRepository);
    }
}
