package security.framework.input.mapper;

import security.aplication.dto.FiltroMenu;
import security.dominio.entidades.Menu;
import security.dominio.vo.Estado;
import security.framework.input.dto.MenuRequestDTO;
import security.framework.input.dto.MenuResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigInteger;

/**
 * Mapper de Entrada: MenuInputMapper
 * 
 * Responsabilidad: Transformar datos de entrada HTTP a entidades de dominio.
 * 
 * Flujo:
 * MenuRequestDTO (HTTP) → MenuInputMapper → Menu (Dominio)
 * 
 * Patrón: MapStruct @Mapper con componentModel="cdi"
 * - Genera implementación en compile-time
 * - Se inyecta automáticamente en CDI
 * - Sin reflexión ni overhead en runtime
 * 
 * Funcionalidades:
 * 1. toDomain(MenuRequestDTO): HTTP request → entidad dominio
 * 2. toResponseDto(Menu): entidad dominio → HTTP response
 * 3. toFiltro(MenuRequestDTO): HTTP request → FiltroMenu (búsqueda)
 * 4. Métodos auxiliares: conversión de IDs, Estados, relaciones
 */
@Mapper(componentModel="cdi")
public interface MenuInputMapper {

    // =====================
    // REQUEST → DOMINIO (CREATE/UPDATE)
    // =====================

    /**
     * Convierte MenuRequestDTO (HTTP) a Menu (Dominio).
     * 
     * Mappeos:
     * - menuId se ignora (se asigna en BD, no en request)
    * - pantallaId se asigna directamente
    * - moduloId se asigna directamente
    * - menuPadreId se asigna directamente
     * - estado se convierte de código numérico a enum Estado
     * 
     * @param dto MenuRequestDTO con datos de entrada
     * @return Menu entidad de dominio lista para usar en casos de uso
     */
    @Mapping(target = "menuId", ignore = true)
    @Mapping(target = "pantallaId", source = "codPantalla")
    @Mapping(target = "moduloId", source = "codModulo")
    @Mapping(target = "menuPadreId", source = "codMenuPadre")
    @Mapping(target = "estado", source = "estado", qualifiedByName = "codigoToEstado")
    Menu toDomain(MenuRequestDTO dto);

    // =====================
    // DOMINIO → RESPONSE (READ)
    // =====================

    /**
     * Convierte Menu (Dominio) a MenuResponseDTO (HTTP).
     * 
     * Mappeos:
     * - menuId → id (simplificar nombre en API)
    * - pantallaId → codPantalla
    * - moduloId → codModulo
    * - menuPadreId → codMenuPadre
     * - estado → descripción legible (ej: "ACTIVO" en lugar de enum)
     * 
     * @param menu Menu entidad de dominio desde BD
     * @return MenuResponseDTO serializable a JSON para cliente HTTP
     */
    @Mapping(target = "id", source = "menuId")
    @Mapping(target = "codPantalla", source = "pantallaId")
    @Mapping(target = "codModulo", source = "moduloId")
    @Mapping(target = "codMenuPadre", source = "menuPadreId")
    @Mapping(target = "estado", source = "estado", qualifiedByName = "estadoToDescripcion")
    MenuResponseDTO toResponseDto(Menu menu);

    /**
     * Convierte MenuRequestDTO a FiltroMenu (DTO de búsqueda).
     * Mapeo directo de campos comunes para búsquedas avanzadas.
     * 
     * @param dto MenuRequestDTO con criterios de búsqueda
     * @return FiltroMenu Record con parámetros de búsqueda
     */
    FiltroMenu toFiltro(MenuRequestDTO dto);

    // =====================
    // CONVERSIONES DE TIPOS
    // =====================

    /**
     * Convierte código numérico a enum Estado.
     * Usa Estado.fromCodigo() para mantener lógica centralizada.
     * 
     * @param codigo Código numérico del estado (1=ACTIVO, 0=INACTIVO)
     * @return Estado enum, o null si codigo es null
     */
    @Named("codigoToEstado")
    default Estado codigoToEstado(BigInteger codigo) {
        return codigo != null ? Estado.fromCodigo(codigo.intValue()) : null;
    }

    /**
     * Convierte enum Estado a descripción legible.
     * Devuelve "ACTIVO" o "INACTIVO" en lugar del enum.
     * 
     * @param estado Estado enum
     * @return Descripción del estado, o "" si estado es null
     */
    @Named("estadoToDescripcion")
    default String estadoToDescripcion(Estado estado) {
        return estado != null ? estado.getDescripcion() : "";
    }

}
