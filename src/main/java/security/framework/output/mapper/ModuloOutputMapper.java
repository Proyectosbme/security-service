package security.framework.output.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import security.dominio.entidades.Modulo;
import security.framework.output.persistence.ModuloJpaEntity;

/**
 * Mapper de Salida: ModuloOutputMapper
 * 
 * Responsabilidad: Transformar entidades de dominio ↔ entidades JPA.
 * Puente entre capa de dominio y capa de persistencia.
 * 
 * Flujo Bidireccional:
 * 1. CREATE: Modulo (dominio) → toJpaEntity() → ModuloJpaEntity → persistir en BD
 * 2. READ: ModuloJpaEntity (BD) → toDomain() → Modulo (dominio) → retornar a UseCase
 * 3. UPDATE: Modulo (dominio) → applyToEntity() → ModuloJpaEntity (merge en BD)
 * 
 * Patrón: MapStruct @Mapper con componentModel="cdi"
 * - Genera implementación en compile-time
 * - Se inyecta automáticamente en CDI
 * - Sin reflexión ni overhead en runtime
 * 
 * Responsabilidades:
 * - toJpaEntity(): Convierte Modulo → ModuloJpaEntity (para CREATE)
 * - toDomain(): Convierte ModuloJpaEntity → Modulo (para READ)
 * - applyToEntity(): Aplica cambios a entidad existente (para UPDATE)
 * 
 * Notas:
 * - Mapeo simple ya que Modulo solo tiene dos campos (id, nombre)
 * - Sin conversiones complejas ni relaciones anidadas
 * - Idempotente: múltiples conversiones produce mismo resultado
 */
@Mapper(componentModel = "cdi")
public interface ModuloOutputMapper {
    
    /**
     * Convierte Modulo (dominio) a ModuloJpaEntity (JPA).
     * Se usa al crear nuevos módulos.
     * 
     * Mapeo:
     * - id → id
     * - nombre → nombre
     * 
     * @param modulo Modulo entidad de dominio
     * @return ModuloJpaEntity lista para persistir en BD
     */
    ModuloJpaEntity toJpaEntity(Modulo modulo);
    
    /**
     * Convierte ModuloJpaEntity (JPA) a Modulo (dominio).
     * Se usa al leer módulos desde BD.
     * 
     * Mapeo:
     * - id → id
     * - nombre → nombre
     * 
     * @param moduloJpaEntity ModuloJpaEntity desde BD
     * @return Modulo entidad de dominio lista para usar en casos de uso
     */
    Modulo toDomain(ModuloJpaEntity moduloJpaEntity);
    
    /**
     * Aplica cambios de Modulo (dominio) a ModuloJpaEntity (JPA) existente.
     * Se usa al actualizar módulos en BD.
     * 
     * @param domain Modulo con nuevos datos
     * @param entity ModuloJpaEntity existente que se modificará
     */
    @Mapping(target = "id", ignore = true)
    void applyToEntity(Modulo domain, @MappingTarget ModuloJpaEntity entity);
}
