package security.framework.output.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repositorio JPA: PantallaJpaRepository
 * 
 * Repositorio Panache para acceso a datos de pantallas desde BD.
 * Implementa PanacheRepository<PantallaJpaEntity> para CRUD automático.
 * 
 * Responsabilidad: Proporcionar métodos de acceso a datos para pantallas.
 * 
 * Patrón: Repository Pattern (Data Access Layer)
 * 
 * Herencia de PanacheRepository<PantallaJpaEntity>:
 * Proporciona automáticamente métodos CRUD estándar:
 * - list(): Obtiene todas las pantallas
 * - listAll(): Alias de list()
 * - findById(Long): Busca pantalla por ID
 * - findByIdOptional(Long): Busca por ID retornando Optional
 * - persist(PantallaJpaEntity): Inserta nueva pantalla
 * - delete(PantallaJpaEntity): Elimina pantalla
 * - count(): Cuenta total de pantallas
 * - find(String query, Map params): Búsqueda con HQL
 * 
 * Métodos personalizados:
 * Ninguno actualmente. Pantalla es entidad simple sin búsquedas especiales.
 * 
 * Extensibilidad:
 * Si en futuro se necesitan búsquedas personalizadas (ej: por nombre, módulo),
 * se pueden agregar aquí.
 * 
 * @ApplicationScoped: Bean CDI singleton, inyectable automáticamente
 */
@ApplicationScoped
public class PantallaJpaRepository implements PanacheRepository<PantallaJpaEntity> {
}
