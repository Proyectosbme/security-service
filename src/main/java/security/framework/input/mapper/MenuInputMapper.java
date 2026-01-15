package security.framework.input.mapper;

import security.dominio.entidades.Menu;
import security.dominio.vo.Estado;
import security.framework.input.dto.MenuRequestDTO;
import security.framework.input.dto.MenuResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigInteger;

@Mapper(componentModel="cdi")
public interface MenuInputMapper {

    // =====================
    // REQUEST → DOMINIO
    // =====================

    @Mapping(target = "menuId", ignore = true)
    @Mapping(target = "pantalla", source = "codPantalla", qualifiedByName = "pantallaFromId")
    @Mapping(target = "modulo", source = "codModulo", qualifiedByName = "moduloFromId")
    @Mapping(target = "menuPadre", source = "codMenuPadre", qualifiedByName = "menuFromId")
    @Mapping(target = "estado", source = "estado", qualifiedByName = "codigoToEstado")
    Menu toDomain(MenuRequestDTO dto);

    // =====================
    // DOMINIO → RESPONSE
    // =====================

    @Mapping(target = "id", source = "menuId")
    @Mapping(target = "codPantalla", source = "pantalla", qualifiedByName = "pantallaToId")
    @Mapping(target = "codModulo", source = "modulo", qualifiedByName = "moduloToId")
    @Mapping(target = "codMenuPadre", source = "menuPadre", qualifiedByName = "menuToId")
    @Mapping(target = "estado", source = "estado", qualifiedByName = "estadoToDescripcion")
    MenuResponseDTO toResponseDto(Menu menu);

    // =====================
    // CONSTRUCTORES PARCIALES (INPUT)
    // =====================

    @Named("pantallaFromId")
    default security.dominio.entidades.Pantalla pantallaFromId(BigInteger id) {
        if (id == null) return null;
        security.dominio.entidades.Pantalla p = new security.dominio.entidades.Pantalla();
        p.setCodigo(id);
        return p;
    }

    @Named("moduloFromId")
    default security.dominio.entidades.Modulo moduloFromId(BigInteger id) {
        if (id == null) return null;
        security.dominio.entidades.Modulo m = new security.dominio.entidades.Modulo();
        m.setId(id);
        return m;
    }

    @Named("menuFromId")
    default Menu menuFromId(BigInteger id) {
        if (id == null) return null;
        Menu m = new Menu();
        m.setMenuId(id);
        return m;
    }

    // =====================
    // CONVERSIONES
    // =====================

    @Named("codigoToEstado")
    default Estado codigoToEstado(BigInteger codigo) {
        return codigo != null ? Estado.fromCodigo(codigo.intValue()) : null;
    }

    @Named("estadoToDescripcion")
    default String estadoToDescripcion(Estado estado) {
        return estado != null ? estado.getDescripcion() : "";
    }

    @Named("pantallaToId")
    default BigInteger pantallaToId(security.dominio.entidades.Pantalla pantalla) {
        return pantalla != null ? pantalla.getCodigo() : null;
    }

    @Named("moduloToId")
    default BigInteger moduloToId(security.dominio.entidades.Modulo modulo) {
        return modulo != null ? modulo.getId() : null;
    }

    @Named("menuToId")
    default BigInteger menuToId(Menu menu) {
        return menu != null ? menu.getMenuId() : null;
    }

}
