package security.framework.output.persistence;

import security.aplication.dto.FiltroMenu;
import security.aplication.port.output.MenuRepository;
import security.dominio.entidades.Menu;
import security.dominio.exceptions.SecurityNotFoundException;
import security.framework.output.mapper.MenuOutputMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador de Repositorio: MenuRepositoryAdapter
 * 
 * Implementa el puerto de salida MenuRepository usando JPA/Panache.
 * 
 * Patrón: Adapter (Hexagonal Architecture)
 * Responsabilidad: Adaptar puerto abstract a tecnología concreta (JPA)
 * 
 * Flujo:
 * UseCase → MenuRepository (puerto) → MenuRepositoryAdapter (adaptador) → MenuJpaRepository (JPA)
 * 
 * Características:
 * - @ApplicationScoped: Singleton inyectado en CDI
 * - MenuJpaRepository: Repositorio Panache para acceso a BD
 * - MenuOutputMapper: Convierte entre JPA ↔ Dominio
 * 
 * Operaciones:
 * - save(Menu): CREATE - Persiste menú nuevo
 * - findById(Long): READ - Busca menú por ID
 * - update(Long, Menu): UPDATE - Actualiza menú existente
 * - delete(Long): DELETE - Elimina menú
 * - buscarMenuPorFiltros(FiltroMenu): SEARCH - Búsqueda avanzada
 */
@ApplicationScoped
public class MenuRepositoryAdapter implements MenuRepository {

    /** Repositorio JPA/Panache para acceso a base de datos */
    private final MenuJpaRepository menuJpaRepository;
    
    /** Mapper para conversiones entre JPA ↔ Dominio */
    private final MenuOutputMapper menuOutputMapper;

    /**
     * Constructor con inyección de dependencias.
     * CDI automáticamente inyecta menJpaRepository y menuOutputMapper.
     * 
     * @param menuJpaRepository Repositorio JPA de Quarkus Panache
     * @param menuOutputMapper Mapper para transformaciones
     */
    public MenuRepositoryAdapter(MenuJpaRepository menuJpaRepository, MenuOutputMapper menuOutputMapper) {
        this.menuJpaRepository = menuJpaRepository;
        this.menuOutputMapper = menuOutputMapper;
    }

    /**
     * Crea un nuevo menú en BD.
     * 
     * Flujo:
     * 1. Convierte Menu (dominio) → MenuJpaEntity (JPA)
     * 2. Persiste en BD usando menuJpaRepository.persist()
     * 3. Convierte MenuJpaEntity → Menu (dominio) con ID asignado
     * 
     * @param menu Menu entidad de dominio a persistir
     * @return Menu persistido con ID asignado por BD
     */
    @Override
    public Menu save(Menu menu) {
      MenuJpaEntity menuJpaEntity = menuOutputMapper.toJpaEntity(menu);
      menuJpaRepository.persist(menuJpaEntity);
      return menuOutputMapper.toDomain(menuJpaEntity);
    }

    /**
     * Busca un menú por su ID.
     * 
     * Flujo:
     * 1. Busca MenuJpaEntity por ID usando findByIdOptional()
     * 2. Si existe, convierte a Menu (dominio)
     * 3. Devuelve Optional con el resultado
     * 
     * @param id ID del menú a buscar
     * @return Optional con Menu si existe, Optional.empty() si no
     */
    @Override
    public Optional<Menu> findById(Long id) {
        return this.menuJpaRepository.findByIdOptional(id)
                .map(menuOutputMapper::toDomain);
    }

    /**
     * Elimina un menú por su ID.
     * 
     * Flujo:
     * 1. Busca MenuJpaEntity por ID
     * 2. Si existe, lo elimina de BD
     * 3. Devuelve true si se eliminó, false si no existía
     * 
     * @param id ID del menú a eliminar
     * @return true si se eliminó, false si no existía
     */
    @Override
    public boolean deleteById(Long id) {
        return menuJpaRepository.findByIdOptional(id)
                .map(entity -> {
                    menuJpaRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Actualiza un menú existente.
     * 
     * Flujo:
     * 1. Busca MenuJpaEntity existente por ID
     * 2. Si no existe, lanza SecurityNotFoundException
     * 3. Aplica cambios usando menuOutputMapper.applyToEntity()
     * 4. Persiste cambios en BD usando persist()
     * 5. Convierte MenuJpaEntity actualizado → Menu (dominio)
     * 
     * @param id ID del menú a actualizar
     * @param menu Menu con nuevos datos
     * @return Menu actualizado con cambios aplicados
     * @throws SecurityNotFoundException si no existe menú con ese ID
     */
    @Override
    public Menu update(Long id, Menu menu) {
        // 1. Buscar menú en BD
        MenuJpaEntity entity = menuJpaRepository.findById(id);
        
        // 2. Verificar que existe
        if (entity == null) {
            throw new SecurityNotFoundException("Menú no encontrado con ID: " + id);
        }
        
        // 3. Aplicar cambios
        menuOutputMapper.applyToEntity(menu, entity);
        
        // 4. Persistir cambios (JPA hace merge automáticamente)
        menuJpaRepository.persist(entity);
        
        // 5. Convertir a dominio y retornar
        return menuOutputMapper.toDomain(entity);
    }

    /**
     * Elimina un menú por su ID (variante sin retorno).
     * 
     * Alternativa a deleteById() sin valor de retorno.
     * Útil para operaciones donde solo importa el efecto, no si existía.
     * 
     * @param id ID del menú a eliminar
     */
    @Override
    public void delete(Long id) {
       boolean resp = menuJpaRepository.findByIdOptional(id)
               .map(entity-> {
                   menuJpaRepository.delete(entity);
                   return true;
               })
               .orElse(false);
    }

    /**
     * Busca menús aplicando filtros avanzados.
     * 
     * Flujo:
     * 1. Ejecuta búsqueda en MenuJpaRepository.buscarMenusPorFiltros()
     * 2. Convierte cada MenuJpaEntity → Menu (dominio)
     * 3. Devuelve lista de menús coincidentes
     * 
     * Filtros soportados (en FiltroMenu):
     * - nombre: búsqueda parcial insensible a mayúsculas
     * - estado: activo/inactivo
     * - módulo: filtrar por módulo
     * - pantalla: filtrar por pantalla asociada
     * 
     * @param filtroMenu Criterios de búsqueda con filtros opcionales
     * @return List<Menu> menús coincidentes con filtros
     */
    @Override
    public List<Menu> buscarMenuPorFiltros(FiltroMenu filtroMenu) {
        return menuJpaRepository.buscarMenusPorFiltros(filtroMenu).stream().map(
                (menuOutputMapper::toDomain)
                        ).toList();
    }
}
