package security.framework.output.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import security.dominio.entidades.Modulo;
import security.dominio.entidades.Pantalla;
import security.framework.output.persistence.PantallaJpaEntity;

import java.math.BigInteger;

/**
 * Mapper de Salida: PantallaOutputMapper
 * 
 * Responsabilidad: Transformar entidades de dominio ↔ entidades JPA.
 * Puente entre capa de dominio y capa de persistencia.
 * 
 * Flujo Bidireccional:
 * 1. CREATE: Pantalla (dominio) → toJpaEntity() → PantallaJpaEntity → persistir en BD
 * 2. READ: PantallaJpaEntity (BD) → toDomain() → Pantalla (dominio) → retornar a UseCase
 * 3. UPDATE: Pantalla (dominio) → applyToEntity() → PantallaJpaEntity (merge en BD)
 * 
 * Patrón: MapStruct @Mapper con componentModel="cdi"
 * - Genera implementación en compile-time
 * - Se inyecta automáticamente en CDI
 * 
 * Responsabilidades:
 * - toJpaEntity(): Convierte Pantalla → PantallaJpaEntity (para CREATE)
 * - toDomain(): Convierte PantallaJpaEntity → Pantalla (para READ)
 * - applyToEntity(): Aplica cambios a entidad existente (para UPDATE)
 */
@ApplicationScoped
@Mapper(componentModel = "cdi")
public interface PantallaOutputMapper {
    
    /**
     * Convierte Pantalla (dominio) a PantallaJpaEntity (JPA).
     * Se usa al crear nuevas pantallas.
     * 
     * Los campos userc y fechaC ya vienen asignados desde CrearPantallaUseCase,
     * así que se mapean normalmente. Se ignoran userMod y fechaMod que son null en creación.
     * 
     * @param pantalla Pantalla entidad de dominio
     * @return PantallaJpaEntity lista para persistir en BD
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userC", source = "userc")
    @Mapping(target = "userMod", ignore = true)
    @Mapping(target = "fechaMod", ignore = true)
    @Mapping(target = "codModulo", source = "modulo", qualifiedByName = "moduloToId")
    PantallaJpaEntity toJpaEntity(Pantalla pantalla);
    
    /**
     * Convierte PantallaJpaEntity (JPA) a Pantalla (dominio).
     * Se usa al leer pantallas desde BD.
     * 
     * @param pantallaJpaEntity PantallaJpaEntity desde BD
     * @return Pantalla entidad de dominio lista para usar en casos de uso
     */
    @Mapping(target = "userc", source = "userC")
    @Mapping(target = "usermod", source = "userMod")
    @Mapping(target = "fechamod", source = "fechaMod")
    @Mapping(target = "modulo", source = "codModulo", qualifiedByName = "moduloFromId")
    Pantalla toDomain(PantallaJpaEntity pantallaJpaEntity);
    
    /**
     * Aplica cambios de Pantalla (dominio) a PantallaJpaEntity (JPA) existente.
     * Se usa al actualizar pantallas en BD.
     * 
     * Preserva userC y fechaC (auditoría de creación).
     * Mapea userMod y fechaMod (ya fueron asignados en ActualizarPantallaUseCase).
     * 
     * @param domain Pantalla con nuevos datos
     * @param entity PantallaJpaEntity existente que se modificará
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userC", ignore = true)
    @Mapping(target = "fechaC", ignore = true)
    @Mapping(target = "userMod", source = "usermod")
    @Mapping(target = "fechaMod", source = "fechamod")
    @Mapping(target = "codModulo", source = "modulo", qualifiedByName = "moduloToId")
    void applyToEntity(Pantalla domain, @MappingTarget PantallaJpaEntity entity);

    /**
     * Crea objeto Modulo con solo el ID.
     * 
     * @param id ID del módulo
     * @return Modulo con ID asignado, o null si id es null
     */
    @Named("moduloFromId")
    default Modulo moduloFromId(BigInteger id) {
        if (id == null) return null;
        Modulo m = new Modulo();
        m.setId(id);
        return m;
    }

    /**
     * Extrae ID de objeto Modulo.
     * 
     * @param modulo Modulo entidad
     * @return ID del módulo, o null si modulo es null
     */
    @Named("moduloToId")
    default BigInteger moduloToId(Modulo modulo) {
        return modulo != null ? modulo.getId() : null;
    }
}
