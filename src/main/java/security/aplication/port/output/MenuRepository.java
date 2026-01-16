package security.aplication.port.output;

import security.aplication.dto.FiltroMenu;
import security.dominio.entidades.Menu;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de Salida (Output Port): MenuRepository
 * Interface que define operaciones de persistencia para Menús.
 * Arquitectura Hexagonal: lado de salida (secundario)
 * UseCase → MenuRepository → JPA/Database
 * Implementación: MenuRepositoryQuarkus (framework)
 */
public interface MenuRepository {
    /**
     * Persiste un nuevo menú en la BD.
     * @param menu Menú a guardar (sin ID)
     * @return Menu guardado con ID asignado
     */
    Menu save(Menu menu);

    /**
     * Busca un menú por ID.
     * @param id Identificador a buscar
     * @return Optional con el menú si existe
     */
    Optional<Menu> findById(Long id);

    /**
     * Elimina un menú por ID.
     * @param id Identificador a eliminar
     * @return true si se eliminó, false si no existía
     */
    boolean deleteById(Long id);

    /**
     * Actualiza un menú existente.
     * @param id Identificador del menú
     * @param menu Nuevos datos
     * @return Menu actualizado
     */
    Menu update(Long id, Menu menu);

    /**
     * Elimina un menú (alternativa a deleteById).
     * @param id Identificador a eliminar
     */
    void delete(Long id);

    /**
     * Busca menús con criterios de filtro.
     * @param filtroMenu Criterios de búsqueda
     * @return Lista de menús coincidentes
     */
    List<Menu> buscarMenuPorFiltros(FiltroMenu filtroMenu);
}
