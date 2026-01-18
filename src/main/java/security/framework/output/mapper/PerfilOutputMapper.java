package security.framework.output.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import security.dominio.entidades.Perfil;
import security.framework.output.persistence.PerfilJpaEntity;

/**
 * Mapper de Salida: PerfilOutputMapper
 * 
 * Responsabilidad: Transformar entidades de dominio ↔ entidades JPA.
 * Puente entre capa de dominio y capa de persistencia.
 * 
 * Flujo Bidireccional:
 * 1. CREATE: Perfil (dominio) → toJpaEntity() → PerfilJpaEntity → persistir en BD
 * 2. READ: PerfilJpaEntity (BD) → toDomain() → Perfil (dominio) → retornar a UseCase
 * 3. UPDATE: Perfil (dominio) → applyToEntity() → PerfilJpaEntity (merge en BD)
 * 
 * Patrón: MapStruct @Mapper con componentModel="cdi"
 * - Genera implementación en compile-time
 * - Se inyecta automáticamente en CDI
 * 
 * Responsabilidades:
 * - toJpaEntity(): Convierte Perfil → PerfilJpaEntity (para CREATE)
 * - toDomain(): Convierte PerfilJpaEntity → Perfil (para READ)
 * - applyToEntity(): Aplica cambios a entidad existente (para UPDATE)
 * 
 * @author Security Team
 * @version 1.0
 */
@ApplicationScoped
@Mapper(componentModel = "cdi")
public interface PerfilOutputMapper {
    
    /**
     * Convierte Perfil (dominio) a PerfilJpaEntity (JPA).
     * Se usa al crear nuevos perfiles.
     * 
     * ID se ignora porque BD lo genera automáticamente.
     * 
     * @param perfil Perfil entidad de dominio
     * @return PerfilJpaEntity lista para persistir en BD
     */
    @Mapping(target = "id", ignore = true)
    PerfilJpaEntity toJpaEntity(Perfil perfil);
    
    /**
     * Convierte PerfilJpaEntity (JPA) a Perfil (dominio).
     * Se usa al leer perfiles desde BD.
     * 
     * @param perfilJpaEntity PerfilJpaEntity desde BD
     * @return Perfil entidad de dominio lista para usar en casos de uso
     */
    Perfil toDomain(PerfilJpaEntity perfilJpaEntity);
    
    /**
     * Aplica cambios de Perfil (dominio) a PerfilJpaEntity (JPA) existente.
     * Se usa al actualizar perfiles en BD.
     * 
     * ID se ignora para no modificar la clave primaria.
     * 
     * @param domain Perfil con nuevos datos
     * @param entity PerfilJpaEntity existente que se modificará
     */
    @Mapping(target = "id", ignore = true)
    void applyToEntity(Perfil domain, @MappingTarget PerfilJpaEntity entity);
}
