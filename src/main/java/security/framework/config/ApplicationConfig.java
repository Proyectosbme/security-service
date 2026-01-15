package security.framework.config;

import security.aplication.port.input.MenuInputPort;
import security.aplication.port.input.ModuloInputPort;
import security.aplication.port.output.MenuRepository;
import security.aplication.port.output.ModuloRepository;
import security.aplication.service.MenuService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import security.aplication.service.ModuloService;

@ApplicationScoped
public class ApplicationConfig {
    private final MenuRepository menuRepository;
    private final ModuloRepository moduloRepository;

    public ApplicationConfig(MenuRepository menuRepository,ModuloRepository moduloRepository ) {
        this.menuRepository = menuRepository;
        this.moduloRepository=moduloRepository;
    }

    @Produces
    @ApplicationScoped
    public MenuInputPort menuService() {
        return new MenuService(menuRepository,moduloRepository);
    }

    @Produces
    @ApplicationScoped
    public ModuloInputPort moduloService(){
        return new ModuloService(moduloRepository);
    }
}
