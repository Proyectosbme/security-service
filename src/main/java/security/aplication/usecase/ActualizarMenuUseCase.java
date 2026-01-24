package security.aplication.usecase;

import security.aplication.port.output.MenuRepository;
import security.aplication.port.output.ModuloRepository;
import security.aplication.port.output.PantallaRepository;
import security.dominio.entidades.Menu;
import security.dominio.exceptions.SecurityNotFoundException;

/**
 * Caso de Uso: ActualizarMenuUseCase
 * 
 * Orquesta actualización de menús existentes con validación y verificación de entidades relacionadas.
 * 
 * Responsabilidad:
 * 1. Validar datos del menú (dominio)
 * 2. Verificar que Pantalla existe (si viene seteada)
 * 3. Verificar que Módulo existe (si viene seteado)
 * 4. Verificar que MenuPadre existe (si viene seteado)
 * 5. Actualizar menú en BD
 * 
 * Patrón: Use Case / Command Pattern
 * 
 * Flujo:
 * Validar → Verificar Pantalla → Verificar Módulo → Verificar MenuPadre → Actualizar
 * 
 * Excepciones:
 * - SecurityValidationException: si datos del menú son inválidos
 * - SecurityNotFoundException: si Pantalla, Módulo, MenuPadre o el Menu a actualizar no existen
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class ActualizarMenuUseCase {
    
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
    public ActualizarMenuUseCase(MenuRepository menuRepository,
                                ModuloRepository moduloRepository,
                                PantallaRepository pantallaRepository) {
        this.menuRepository = menuRepository;
        this.moduloRepository = moduloRepository;
        this.pantallaRepository = pantallaRepository;
    }

    /**
     * Ejecuta actualización de menú validando datos y verificando entidades relacionadas.
     * 
     * @param id Identificador del menú a actualizar
     * @param datos Nuevos datos del menú
     * @return Menu actualizado
     * @throws SecurityValidationException si datos son inválidos
     * @throws SecurityNotFoundException si Pantalla, Módulo, MenuPadre o el Menu no existen
     */
    public Menu ejecutar(Long id, Menu datos) {
        // 1. Validación de datos del menú (reglas de negocio en dominio)
        datos.validar();
        
        // 2. Verificar que Pantalla existe (si viene seteada)
        if (datos.getPantallaId() != null) {
            pantallaRepository.findById(datos.getPantallaId().longValue())
                    .orElseThrow(() -> new SecurityNotFoundException(
                    "Pantalla no encontrada con ID: " + datos.getPantallaId()));
        }
        
        // 3. Verificar que Módulo existe (si viene seteado)
        if (datos.getModuloId() != null) {
            moduloRepository.findById(datos.getModuloId().longValue())
                    .orElseThrow(() -> new SecurityNotFoundException(
                    "Módulo no encontrado con ID: " + datos.getModuloId()));
        }
        
        // 4. Verificar que MenuPadre existe (si viene seteado)
        if (datos.getMenuPadreId() != null) {
            menuRepository.findById(datos.getMenuPadreId().longValue())
                    .orElseThrow(() -> new SecurityNotFoundException(
                    "Menú Padre no encontrado con ID: " + datos.getMenuPadreId()));
        }
        
        // 5. Actualizar menú en BD
        return menuRepository.update(id, datos);
    }
}
