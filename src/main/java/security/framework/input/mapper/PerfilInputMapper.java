package security.framework.input.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import security.dominio.entidades.Perfil;
import security.framework.input.dto.PerfilRequestDTO;
import security.framework.input.dto.PerfilResponseDTO;

/**
 * Mapper de Entrada: PerfilInputMapper
 * 
 * Responsabilidad: Transformar datos de entrada HTTP a entidades de dominio.
 * 
 * Flujo:
 * PerfilRequestDTO (HTTP) → PerfilInputMapper → Perfil (Dominio)
 * 
 * Patrón: MapStruct @Mapper con componentModel="cdi"
 * - Genera implementación en compile-time
 * - Se inyecta automáticamente en CDI
 * 
 * Funcionalidades:
 * 1. toDomain(PerfilRequestDTO): HTTP request → entidad dominio
 * 2. toResponseDto(Perfil): entidad dominio → HTTP response
 * 
 * @author Security Team
 * @version 1.0
 */
@ApplicationScoped
@Mapper(componentModel = "cdi")
public interface PerfilInputMapper {

    /**
     * Convierte PerfilRequestDTO (HTTP) a Perfil (Dominio).
     * 
     * @param dto PerfilRequestDTO con datos de entrada
     * @return Perfil entidad de dominio lista para usar en casos de uso
     */
    @Mapping(target = "id", ignore = true)
    Perfil toDomain(PerfilRequestDTO dto);

    /**
     * Convierte Perfil (Dominio) a PerfilResponseDTO (HTTP).
     * 
     * @param perfil Perfil entidad de dominio desde BD
     * @return PerfilResponseDTO serializable a JSON para cliente HTTP
     */
    PerfilResponseDTO toResponseDto(Perfil perfil);
}
