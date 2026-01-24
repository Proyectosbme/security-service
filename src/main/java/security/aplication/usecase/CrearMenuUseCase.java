package security.aplication.usecase;

import security.aplication.port.output.MenuRepository;
import security.aplication.port.output.ModuloRepository;
import security.aplication.port.output.PantallaRepository;
import security.dominio.entidades.Menu;
import security.dominio.exceptions.SecurityNotFoundException;

/**
 * Caso de Uso: CrearMenuUseCase
 * 
 * Orquesta creación de menús con validación y verificación de entidades relacionadas.
 * 
 * Responsabilidad: 
 * 1. Validar datos del menú (dominio)
 * 2. Verificar que Pantalla existe (si viene seteada)
 * 3. Verificar que Módulo existe (si viene seteado)
 * 4. Verificar que MenuPadre existe (si viene seteado)
 * 5. Persistir menú en BD
 * 
 * Patrón: Use Case / Command Pattern
 * 
 * Flujo: 
 * Validar → Verificar Pantalla → Verificar Módulo → Verificar MenuPadre → Persistir
 * 
 * Excepciones:
 * - SecurityValidationException: si datos del menú son inválidos
 * - SecurityNotFoundException: si Pantalla, Módulo o MenuPadre no existen
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class CrearMenuUseCase {

    private final MenuRepository menuRepository;
    private final ModuloRepository moduloRepository;
    private final PantallaRepository pantallaRepository;

    /**
     * Constructor con inyección de dependencias.
     * 
     * @param menuRepository Repositorio de menús
     * @param moduloRepository Repositorio de módulos
     * @param pantallaRepository Repositorio de pantallas
     */
    public CrearMenuUseCase(MenuRepository menuRepository, 
                           ModuloRepository moduloRepository,
                           PantallaRepository pantallaRepository) {
        this.menuRepository = menuRepository;
        this.moduloRepository = moduloRepository;
        this.pantallaRepository = pantallaRepository;
    }

    /**
     * Ejecuta creación de menú validando datos y verificando entidades relacionadas.
     * 
     * @param menu Menú a crear (sin ID)
     * @return Menu creado con ID asignado por BD
     * @throws SecurityValidationException si incumple reglas de negocio
     * @throws SecurityNotFoundException si Pantalla, Módulo o MenuPadre no existen
     */
    public Menu ejecutar(Menu menu) {
        // 1. Validar datos del menú (reglas de negocio en dominio)
        menu.validar();
        
        // 2. Verificar que Pantalla existe (si viene seteada)
        if (menu.getPantallaId() != null) {
            pantallaRepository.findById(menu.getPantallaId().longValue())
                    .orElseThrow(() -> new SecurityNotFoundException(
                    "Pantalla no encontrada con ID: " + menu.getPantallaId()));
        }
        
        // 3. Verificar que Módulo existe (si viene seteado)
        if (menu.getModuloId() != null) {
            moduloRepository.findById(menu.getModuloId().longValue())
                    .orElseThrow(() -> new SecurityNotFoundException(
                    "Módulo no encontrado con ID: " + menu.getModuloId()));
        }
        
        // 4. Verificar que MenuPadre existe (si viene seteado)
        if (menu.getMenuPadreId() != null) {
            menuRepository.findById(menu.getMenuPadreId().longValue())
                    .orElseThrow(() -> new SecurityNotFoundException(
                    "Menú Padre no encontrado con ID: " + menu.getMenuPadreId()));
        }
        
        // 5. Persistir menú en BD
        return menuRepository.save(menu);
    }
}
