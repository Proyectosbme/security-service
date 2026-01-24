package security.aplication.port.input;

import security.aplication.dto.FiltroMenu;
import security.dominio.entidades.Menu;

import java.util.List;

/**
 * Puerto de Entrada (Input Port): MenuInputPort
 * 
 * Define el contrato de aplicación para la gestión de menús.
 * 
 * Responsabilidad:
 * 1. Exponer operaciones de menú para la capa de entrada
 * 2. Mantener la independencia de la infraestructura
 * 
 * Patrón: Input Port (Arquitectura Hexagonal)
 * 
 * Flujo:
 * Controller → MenuInputPort → MenuService → UseCases → Repositorios
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public interface MenuInputPort {
    /**
     * Crea un nuevo menú.
     *
     * @param menu menú a crear (sin ID)
     * @return menú creado con ID asignado
     */
    Menu crear(Menu menu);

    /**
     * Busca un menú por su ID.
     *
     * @param id identificador del menú
     * @return menú encontrado
     */
    Menu buscarPorId(Long id);

    /**
     * Elimina un menú del sistema.
     *
     * @param id identificador del menú a eliminar
     */
    void eliminar(Long id);

    /**
     * Actualiza un menú existente.
     *
     * @param id identificador del menú
     * @param menu nuevos datos del menú
     * @return menú actualizado
     */
    Menu acualizar(Long id, Menu menu);

    /**
     * Busca menús con criterios de filtro.
     *
     * @param filtroMenu criterios de búsqueda
     * @return lista de menús que cumplen criterios
     */
    List<Menu> buscarPorFiltros(FiltroMenu filtroMenu);
}
