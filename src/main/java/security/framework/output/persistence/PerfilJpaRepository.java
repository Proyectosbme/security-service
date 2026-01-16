package security.framework.output.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigInteger;

/**
 * Repositorio JPA: PerfilJpaRepository
 * 
 * Repositorio Panache para acceso a datos de perfiles desde BD.
 * Implementa PanacheRepositoryBase<PerfilJpaEntity, BigInteger> para CRUD automático.
 * 
 * Responsabilidad: Proporcionar métodos de acceso a datos para perfiles.
 * 
 * Patrón: Repository Pattern (Data Access Layer)
 * 
 * Herencia de PanacheRepositoryBase<PerfilJpaEntity, BigInteger>:
 * Proporciona automáticamente métodos CRUD estándar:
 * - list(): Obtiene todos los perfiles
 * - listAll(): Alias de list()
 * - findById(BigInteger): Busca perfil por ID
 * - findByIdOptional(BigInteger): Busca por ID retornando Optional
 * - persist(PerfilJpaEntity): Inserta nuevo perfil
 * - delete(PerfilJpaEntity): Elimina perfil
 * - count(): Cuenta total de perfiles
 * - find(String query, Map params): Búsqueda con HQL
 * 
 * Nota sobre tipo de ID:
 * - Usa PanacheRepositoryBase (no PanacheRepository) porque ID es BigInteger (no Long)
 * - Permite usar tipos custom como clave primaria
 * 
 * Métodos personalizados:
 * Ninguno actualmente. Perfil es entidad simple sin búsquedas especiales.
 * 
 * Extensibilidad:
 * Si en futuro se necesitan búsquedas personalizadas (ej: por nombre),
 * se pueden agregar aquí.
 * 
 * @ApplicationScoped: Bean CDI singleton, inyectable automáticamente
 * 
 * @author Security Team
 * @version 1.0
 */
@ApplicationScoped
public class PerfilJpaRepository implements PanacheRepositoryBase<PerfilJpaEntity, BigInteger> {
}
