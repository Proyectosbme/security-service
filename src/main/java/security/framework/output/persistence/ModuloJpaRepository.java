package security.framework.output.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repositorio JPA: ModuloJpaRepository
 * 
 * Repositorio Panache para acceso a datos de módulos desde BD.
 * Implementa PanacheRepository<ModuloJpaEntity> para CRUD automático.
 * 
 * Responsabilidad: Proporcionar métodos de acceso a datos para módulos.
 * 
 * Patrón: Repository Pattern (Data Access Layer)
 * 
 * Herencia de PanacheRepository<ModuloJpaEntity>:
 * Proporciona automáticamente métodos CRUD estándar:
 * - list(): Obtiene todos los módulos
 * - listAll(): Alias de list()
 * - findById(Long): Busca módulo por ID
 * - findByIdOptional(Long): Busca por ID retornando Optional
 * - persist(ModuloJpaEntity): Inserta nuevo módulo
 * - delete(ModuloJpaEntity): Elimina módulo
 * - count(): Cuenta total de módulos
 * - find(String query, Map params): Búsqueda con HQL
 * 
 * Métodos personalizados:
 * Ninguno actualmente. Módulo es entidad simple sin búsquedas especiales.
 * 
 * Extensibilidad:
 * Si en futuro se necesitan búsquedas personalizadas (ej: por nombre),
 * se pueden agregar aquí.
 * 
 * @ApplicationScoped: Bean CDI singleton, inyectable automáticamente
 */
@ApplicationScoped
public class ModuloJpaRepository implements PanacheRepository<ModuloJpaEntity> {
}
