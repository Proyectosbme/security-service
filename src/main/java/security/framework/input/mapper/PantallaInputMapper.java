package security.framework.input.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import security.dominio.entidades.Modulo;
import security.dominio.entidades.Pantalla;
import security.framework.input.dto.PantallaRequestDTO;
import security.framework.input.dto.PantallaResponseDTO;

import java.math.BigInteger;

/**
 * Mapper de Entrada: PantallaInputMapper
 * 
 * Responsabilidad: Transformar datos de entrada HTTP a entidades de dominio.
 * 
 * Flujo:
 * PantallaRequestDTO (HTTP) → PantallaInputMapper → Pantalla (Dominio)
 * 
 * Patrón: MapStruct @Mapper con componentModel="cdi"
 * - Genera implementación en compile-time
 * - Se inyecta automáticamente en CDI
 * 
 * Funcionalidades:
 * 1. toDomain(PantallaRequestDTO): HTTP request → entidad dominio
 * 2. toResponseDto(Pantalla): entidad dominio → HTTP response
 */
@ApplicationScoped
@Mapper(componentModel = "cdi")
public interface PantallaInputMapper {

    /**
     * Convierte PantallaRequestDTO (HTTP) a Pantalla (Dominio).
     * 
     * @param dto PantallaRequestDTO con datos de entrada
     * @return Pantalla entidad de dominio lista para usar en casos de uso
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userc", ignore = true)
    @Mapping(target = "fechaC", ignore = true)
    @Mapping(target = "usermod", ignore = true)
    @Mapping(target = "fechamod", ignore = true)
    @Mapping(target = "modulo", source = "codModulo", qualifiedByName = "moduloFromId")
    Pantalla toDomain(PantallaRequestDTO dto);

    /**
     * Convierte Pantalla (Dominio) a PantallaResponseDTO (HTTP).
     * 
     * @param pantalla Pantalla entidad de dominio desde BD
     * @return PantallaResponseDTO serializable a JSON para cliente HTTP
     */
    @Mapping(target = "userC", source = "userc")
    @Mapping(target = "userMod", source = "usermod")
    @Mapping(target = "fechaMod", source = "fechamod")
    @Mapping(target = "codModulo", source = "modulo", qualifiedByName = "moduloToId")
    PantallaResponseDTO toResponseDto(Pantalla pantalla);

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
