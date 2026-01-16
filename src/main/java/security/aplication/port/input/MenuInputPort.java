package security.aplication.port.input;

import security.aplication.dto.FiltroMenu;
import security.dominio.entidades.Menu;

import java.util.List;

/**
 * Puerto de Entrada (Input Port): MenuInputPort
 * Interface que define los casos de uso para gestión de Menús.
 * Arquitectura Hexagonal: lado de entrada (primario)
 * Controller → MenuInputPort → MenuService → UseCases → Repository
 */
public interface MenuInputPort {
    /**
     * Crea un nuevo menú validando y verificando el módulo.
     * @param menu Menú a crear (sin ID)
     * @return Menu creado con ID asignado
     * @throws SecurityValidationException si es inválido
     * @throws SecurityNotFoundException si el módulo no existe
     */
    Menu crear(Menu menu);

    /**
     * Busca un menú por su ID.
     * @param id Identificador del menú
     * @return Menu encontrado
     * @throws SecurityNotFoundException si no existe
     */
    Menu buscarPorId(Long id);

    /**
     * Elimina un menú del sistema.
     * @param id Identificador del menú a eliminar
     * @throws SecurityNotFoundException si no existe
     */
    void eliminar(Long id);

    /**
     * Actualiza un menú existente validando los nuevos datos.
     * @param id Identificador del menú
     * @param menu Nuevos datos del menú
     * @return Menu actualizado
     * @throws SecurityValidationException si datos son inválidos
     */
    Menu acualizar(Long id, Menu menu);

    /**
     * Busca menús con criterios de filtro (AND).
     * @param filtroMenu Criterios de búsqueda (parámetros opcionales)
     * @return Lista de menús que cumplen criterios
     */
    List<Menu> buscarPorFiltros(FiltroMenu filtroMenu);
}
