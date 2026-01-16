package security.framework.input.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import security.dominio.entidades.Modulo;
import security.framework.input.dto.ModuloRequestDTO;
import security.framework.input.dto.ModuloResponseDTO;

/**
 * Mapper de Entrada: ModuloInputMapper
 * 
 * Responsabilidad: Transformar datos de entrada HTTP a entidades de dominio de módulos.
 * 
 * Flujo:
 * ModuloRequestDTO (HTTP) → ModuloInputMapper → Modulo (Dominio)
 * 
 * Patrón: MapStruct @Mapper con componentModel="cdi"
 * - Genera implementación en compile-time
 * - Se inyecta automáticamente en CDI
 * 
 * Funcionalidades:
 * 1. toDomain(ModuloRequestDTO): HTTP request → entidad dominio
 * 2. toResponseDto(Modulo): entidad dominio → HTTP response
 * 
 * Notas:
 * - Mapeo simple ya que Modulo solo tiene dos campos (id, nombre)
 * - Sin conversiones de tipos complejas ni relaciones anidadas
 */
@Mapper(componentModel = "cdi")
public interface ModuloInputMapper {

    /**
     * Convierte Modulo (Dominio) a ModuloResponseDTO (HTTP).
     * 
     * Mapeo directo: id → id, nombre → nombre
     * 
     * @param modulo Modulo entidad de dominio desde BD
     * @return ModuloResponseDTO serializable a JSON para cliente HTTP
     */
    ModuloResponseDTO toResponseDto(Modulo modulo);

    /**
     * Convierte ModuloRequestDTO (HTTP) a Modulo (Dominio).
     * 
     * Mapeo:
     * - id se ignora (se asigna en BD, no en request)
     * - nombre se copia directamente
     * 
     * @param dto ModuloRequestDTO con datos de entrada
     * @return Modulo entidad de dominio lista para usar en casos de uso
     */
    @Mapping(target = "id", ignore = true)
    Modulo toDomain(ModuloRequestDTO dto);
}
