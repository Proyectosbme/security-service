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
     * - pantalla se construye con solo el ID (relación lazy)
     * - modulo se construye con solo el ID (relación lazy)
     * - menuPadre se construye con solo el ID (relación lazy)
     * - estado se convierte de código numérico a enum Estado
     * 
     * @param dto MenuRequestDTO con datos de entrada
     * @return Menu entidad de dominio lista para usar en casos de uso
     */
    @Mapping(target = "menuId", ignore = true)
    @Mapping(target = "pantalla", source = "codPantalla", qualifiedByName = "pantallaFromId")
    @Mapping(target = "modulo", source = "codModulo", qualifiedByName = "moduloFromId")
    @Mapping(target = "menuPadre", source = "codMenuPadre", qualifiedByName = "menuFromId")
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
     * - pantalla → codPantalla (solo ID, sin relación completa)
     * - modulo → codModulo (solo ID)
     * - menuPadre → codMenuPadre (solo ID)
     * - estado → descripción legible (ej: "ACTIVO" en lugar de enum)
     * 
     * @param menu Menu entidad de dominio desde BD
     * @return MenuResponseDTO serializable a JSON para cliente HTTP
     */
    @Mapping(target = "id", source = "menuId")
    @Mapping(target = "codPantalla", source = "pantalla", qualifiedByName = "pantallaToId")
    @Mapping(target = "codModulo", source = "modulo", qualifiedByName = "moduloToId")
    @Mapping(target = "codMenuPadre", source = "menuPadre", qualifiedByName = "menuToId")
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
    // CONSTRUCTORES PARCIALES (@Named)
    // =====================

    /**
     * Crea objeto Pantalla con solo el ID.
     * Útil para relaciones de solo lectura sin cargar entidad completa.
     * 
     * @param id Código de pantalla
     * @return Pantalla con ID asignado, o null si id es null
     */
    @Named("pantallaFromId")
    default security.dominio.entidades.Pantalla pantallaFromId(BigInteger id) {
        if (id == null) return null;
        security.dominio.entidades.Pantalla p = new security.dominio.entidades.Pantalla();
        p.setId(id.longValue());
        return p;
    }

    /**
     * Crea objeto Modulo con solo el ID.
     * Útil para relaciones sin cargar entidad completa.
     * 
     * @param id ID del módulo
     * @return Modulo con ID asignado, o null si id es null
     */
    @Named("moduloFromId")
    default security.dominio.entidades.Modulo moduloFromId(BigInteger id) {
        if (id == null) return null;
        security.dominio.entidades.Modulo m = new security.dominio.entidades.Modulo();
        m.setId(id);
        return m;
    }

    /**
     * Crea objeto Menu con solo el ID.
     * Útil para referencias a menú padre sin cargar entidad completa.
     * 
     * @param id ID del menú padre
     * @return Menu con ID asignado, o null si id es null
     */
    @Named("menuFromId")
    default Menu menuFromId(BigInteger id) {
        if (id == null) return null;
        Menu m = new Menu();
        m.setMenuId(id);
        return m;
    }

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

    /**
     * Extrae ID de objeto Pantalla.
     * Inverso de pantallaFromId.
     * 
     * @param pantalla Pantalla entidad
     * @return Código de pantalla, o null si pantalla es null
     */
    @Named("pantallaToId")
    default BigInteger pantallaToId(security.dominio.entidades.Pantalla pantalla) {
        return pantalla != null ? BigInteger.valueOf(pantalla.getId()) : null;
    }

    /**
     * Extrae ID de objeto Modulo.
     * Inverso de moduloFromId.
     * 
     * @param modulo Modulo entidad
     * @return ID del módulo, o null si modulo es null
     */
    @Named("moduloToId")
    default BigInteger moduloToId(security.dominio.entidades.Modulo modulo) {
        return modulo != null ? modulo.getId() : null;
    }

    /**
     * Extrae ID de objeto Menu.
     * Inverso de menuFromId.
     * 
     * @param menu Menu entidad
     * @return ID del menú, o null si menu es null
     */
    @Named("menuToId")
    default BigInteger menuToId(Menu menu) {
        return menu != null ? menu.getMenuId() : null;
    }

}
