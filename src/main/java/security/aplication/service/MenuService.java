package security.aplication.service;

import security.aplication.dto.FiltroMenu;
import security.aplication.port.input.MenuInputPort;
import security.aplication.port.output.MenuRepository;
import security.aplication.port.output.ModuloRepository;
import security.aplication.port.output.PantallaRepository;
import security.aplication.usecase.*;
import security.dominio.entidades.Menu;
import java.util.List;

/**
 * Servicio de Aplicación: MenuService
 * 
 * Orquesta operaciones de menú como implementación de {@link MenuInputPort}.
 * 
 * Responsabilidad:
 * 1. Instanciar casos de uso de menú
 * 2. Delegar operaciones de creación, consulta, actualización y eliminación
 * 3. Exponer un API de aplicación estable
 * 
 * Patrón: Application Service / Facade
 * 
 * Flujo:
 * Controller → MenuInputPort → MenuService → UseCases → Repositorios
 * 
 * Excepciones:
 * - Se propagan las excepciones de los casos de uso
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class MenuService implements MenuInputPort {

    private final CrearMenuUseCase crearMenuUseCase;
    private final BuscarMenuPorIdUseCase buscarMenuPorIdUseCase;
    private final ActualizarMenuUseCase actualizarMenuUseCase;
    private final EliminarMenuUseCase eliminarMenuUseCase;
    private final BuscarMenuPorFiltros buscarMenuPorFiltros;

    /**
     * Constructor que inyecta dependencias de repositorios.
     * Instancia todos los casos de uso.
     * 
     * @param menuRepository Repositorio de menús
     * @param moduloRepository Repositorio de módulos
     * @param pantallaRepository Repositorio de pantallas
     */
    public MenuService(MenuRepository menuRepository, 
                      ModuloRepository moduloRepository,
                      PantallaRepository pantallaRepository) {
        this.crearMenuUseCase = new CrearMenuUseCase(menuRepository, moduloRepository, pantallaRepository);
        this.buscarMenuPorIdUseCase = new BuscarMenuPorIdUseCase(menuRepository);
        this.actualizarMenuUseCase = new ActualizarMenuUseCase(menuRepository, moduloRepository, pantallaRepository);
        this.eliminarMenuUseCase = new EliminarMenuUseCase(menuRepository);
        this.buscarMenuPorFiltros = new BuscarMenuPorFiltros(menuRepository);
    }

    /**
     * Crea nuevo menú.
     * 
     * @param menu Menu a crear
     * @return Menu creado con ID asignado
     */
    @Override
    public Menu crear(Menu menu) {
        return crearMenuUseCase.ejecutar(menu);
    }

    /**
     * Busca menú por ID.
     * 
     * @param id ID del menú
     * @return Menu encontrado
     */
    @Override
    public Menu buscarPorId(Long id) {
        return buscarMenuPorIdUseCase.ejecutar(id);
    }

    /**
     * Elimina menú por ID.
     * 
     * @param id ID del menú a eliminar
     */
    @Override
    public void eliminar(Long id) {
        eliminarMenuUseCase.ejecutar(id);
    }

    /**
     * Actualiza menú existente.
     * 
     * @param id ID del menú a actualizar
     * @param menu Menu con nuevos datos
     * @return Menu actualizado
     */
    @Override
    public Menu acualizar(Long id, Menu menu) {
        return actualizarMenuUseCase.ejecutar(id, menu);
    }

    /**
     * Busca menús por filtros.
     * 
     * @param filtroMenu Criterios de búsqueda
     * @return Lista de menús coincidentes
     */
    @Override
    public List<Menu> buscarPorFiltros(FiltroMenu filtroMenu) {
        return buscarMenuPorFiltros.ejecutar(filtroMenu);
    }
}
