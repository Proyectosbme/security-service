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
    /** Repositorio de perfiles inyectado por CDI */
    private final PerfilRepository perfilRepository;

    /**
     * Constructor con inyección de repositorios.
     * Quarkus CDI automáticamente inyecta las implementaciones.
     * 
     * @param menuRepository Implementación del repositorio de menús
     * @param moduloRepository Implementación del repositorio de módulos
     * @param pantallaRepository Implementación del repositorio de pantallas
     * @param perfilRepository Implementación del repositorio de perfiles
     */
    public ApplicationConfig(MenuRepository menuRepository, ModuloRepository moduloRepository, PantallaRepository pantallaRepository, PerfilRepository perfilRepository) {
        this.menuRepository = menuRepository;
        this.moduloRepository = moduloRepository;
        this.pantallaRepository = pantallaRepository;
        this.perfilRepository = perfilRepository;
    }

    /**
     * Produce instancia singleton de MenuInputPort (MenuService).
     * Anotada con @Produces para inyección en CDI.
     * 
     * Patrón Hexagonal:
     * - MenuService implementa MenuInputPort (puerto de entrada)
     * - Depende de MenuRepository, ModuloRepository, PantallaRepository (puertos de salida)
     * - Crea instancias de casos de uso con todas sus dependencias
     * 
     * Casos de Uso:
     * - CrearMenuUseCase: Valida Pantalla, Módulo, MenuPadre y crea menú
     * - BuscarMenuPorIdUseCase: Busca menú por ID
     * - ActualizarMenuUseCase: Valida Pantalla, Módulo, MenuPadre y actualiza menú
     * - EliminarMenuUseCase: Elimina menú por ID
     * - BuscarMenuPorFiltros: Búsqueda avanzada con criterios
     * 
     * @return MenuService configurado con sus repositorios y casos de uso
     */
    @Produces
    @ApplicationScoped
    public MenuInputPort menuService() {
        return new MenuService(menuRepository, moduloRepository, pantallaRepository);
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

    /**
     * Produce instancia singleton de PerfilInputPort (PerfilService).
     * Anotada con @Produces para inyección en CDI.
     * 
     * Patrón Hexagonal:
     * - PerfilService implementa PerfilInputPort (puerto de entrada)
     * - Depende de PerfilRepository (puerto de salida)
     * - Se inyecta en PerfilController y en clientes del servicio
     * 
     * @return PerfilService configurado con su repositorio
     */
    @Produces
    @ApplicationScoped
    public PerfilInputPort perfilService() {
        return new PerfilService(perfilRepository);
    }
}
