package security.framework.output.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import security.aplication.dto.FiltroMenu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repositorio JPA: MenuJpaRepository
 * 
 * Repositorio Panache para acceso a datos de menús desde BD.
 * Implementa PanacheRepository<MenuJpaEntity> para CRUD automático.
 * 
 * Responsabilidad: Proporcionar métodos de acceso a datos especializados.
 * 
 * Patrón: Repository Pattern (Data Access Layer)
 * 
 * Herencia de PanacheRepository<MenuJpaEntity>:
 * - list(): Obtiene todos los menús
 * - listAll(): Alias de list()
 * - findById(Long): Busca por ID
 * - findByIdOptional(Long): Busca por ID retornando Optional
 * - persist(MenuJpaEntity): Inserta nuevo menú
 * - delete(MenuJpaEntity): Elimina menú
 * - count(): Cuenta total de menús
 * - find(String query, Map params): Búsqueda con HQL
 * 
 * Métodos personalizados:
 * - buscarMenusPorFiltros(FiltroMenu): Búsqueda avanzada con criterios dinámicos
 * 
 * @ApplicationScoped: Bean CDI singleton, inyectable automáticamente
 */
@ApplicationScoped
public class MenuJpaRepository implements PanacheRepository<MenuJpaEntity> {
    
    /**
     * Busca menús aplicando filtros dinámicos.
     * 
     * Construye query HQL dinámicamente basada en filtros no-null.
     * 
     * Filtros soportados (en FiltroMenu):
     * - nombre: Búsqueda parcial insensible a mayúsculas (LIKE)
     * - codModulo: Igualdad exacta
     * - estado: Igualdad exacta (1=ACTIVO, 0=INACTIVO)
     * 
     * Comportamiento:
     * - Si todos los filtros son null, retorna todos los menús
     * - Si algún filtro está presente, aplica AND entre ellos
     * - Búsqueda nombre usa lowercase para insensibilidad
     * 
     * @param filtro FiltroMenu con criterios de búsqueda (campos opcionales)
     * @return List<MenuJpaEntity> menús que coinciden con filtros
     * 
     * Ejemplo:
     * FiltroMenu filtro = new FiltroMenu("usuario", BigInteger.valueOf(2), null);
     * List<MenuJpaEntity> resultados = buscarMenusPorFiltros(filtro);
     * // Retorna menús con nombre contiene "usuario" Y módulo=2
     */
    public List<MenuJpaEntity> buscarMenusPorFiltros(FiltroMenu filtro) {
        StringBuilder query = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        boolean whereAdded = false;

        if (filtro.nombre() != null) {
            query.append(whereAdded ? " and " : "");
            query.append("lower(nombre) like :nombre");
            params.put("nombre", "%" + filtro.nombre().toLowerCase() + "%");
            whereAdded = true;
        }

        if (filtro.codModulo() != null) {
            query.append(whereAdded ? " and " : "");
            query.append("codModulo = :codModulo");
            params.put("codModulo", filtro.codModulo());
            whereAdded = true;
        }

        if (filtro.estado() != null) {
            query.append(whereAdded ? " and " : "");
            query.append("estado = :estado");
            params.put("estado", filtro.estado());
            whereAdded = true;
        }

        // Si no hay filtros, devuelve todo
        if (query.isEmpty()) {
            return listAll();
        }
        System.out.println(query.toString());

        return find(query.toString(), params).list();
    }
}
