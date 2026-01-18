package security.framework.output.mapper;

import security.dominio.entidades.Menu;
import security.dominio.entidades.Modulo;
import security.dominio.entidades.Pantalla;
import security.dominio.vo.Estado;
import security.framework.output.persistence.MenuJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.math.BigInteger;
/**
 * MenuOutputMapper
 * ----------------
 *
 * Mapper de salida responsable de convertir entre:
 *
 *  - {@link security.dominio.entidades.Menu} (Entidad de Dominio)
 *  - {@link security.framework.output.persistence.MenuJpaEntity} (Entidad JPA)
 *
 * Este mapper pertenece a la capa de Infraestructura (Output Adapter) y actúa
 * como traductor entre el modelo de dominio y el modelo de persistencia.
 *
 * <h3>Principios de diseño</h3>
 * <ul>
 *   <li>El dominio es completamente independiente de JPA</li>
 *   <li>Las entidades de dominio no contienen anotaciones de persistencia</li>
 *   <li>Las relaciones se representan mediante referencias por ID</li>
 *   <li>Se evita la carga innecesaria de agregados completos</li>
 * </ul>
 *
 * <h3>Conversión de relaciones</h3>
 * Las relaciones como Pantalla, Modulo y MenuPadre se manejan únicamente
 * mediante sus identificadores:
 *
 * <ul>
 *   <li>DOMINIO → JPA: se extrae el ID del objeto</li>
 *   <li>JPA → DOMINIO: se construye una referencia parcial usando el ID</li>
 * </ul>
 *
 * <h3>Estados</h3>
 * El estado del menú se convierte entre:
 * <ul>
 *   <li>{@link security.dominio.vo.Estado} (Dominio)</li>
 *   <li>Código numérico persistido en base de datos</li>
 * </ul>
 *
 * <h3>Operaciones soportadas</h3>
 * <ul>
 *   <li>Creación de entidades JPA desde dominio</li>
 *   <li>Reconstrucción del dominio desde JPA</li>
 *   <li>Actualización de entidades JPA existentes</li>
 * </ul>
 *
 * <h3>MapStruct</h3>
 * Este mapper utiliza MapStruct con integración CDI, por lo que
 * la implementación se genera automáticamente y es gestionada por Quarkus.
 *
 * @author
 * Equipo de Desarrollo
 */
@Mapper(componentModel="cdi")
public interface MenuOutputMapper {

    /**
     * Convierte un Menu del dominio en su representación JPA.
     *
     * <p>
     * Este método se utiliza en operaciones de creación (INSERT).
     * Las relaciones con Pantalla, Modulo y MenuPadre se persisten
     * únicamente mediante sus identificadores, evitando la carga
     * de agregados completos.
     * </p>
     *
     * <p>
     * El estado del menú se convierte desde el Value Object {@link Estado}
     * a su código numérico persistible.
     * </p>
     *
     * @param menu Entidad de dominio Menu
     * @return Entidad JPA lista para ser persistida
     */
    @Mapping(target = "id", source = "menuId")
    @Mapping(target = "codPantalla", source = "pantalla", qualifiedByName = "pantallaToId")
    @Mapping(target = "codModulo", source = "modulo", qualifiedByName = "moduloToId")
    @Mapping(target = "codMenuPadre", source = "menuPadre", qualifiedByName = "menuToId")
    @Mapping(target = "estado", source = "estado", qualifiedByName = "estadoToCodigoJpa")
    MenuJpaEntity toJpaEntity(Menu menu);//a entidad JPA desde Menu


    /**
     * Reconstruye una entidad de dominio Menu a partir de una entidad JPA.
     *
     * <p>
     * Las relaciones Pantalla, Modulo y MenuPadre se crean como
     * referencias parciales usando únicamente sus identificadores.
     * No se realiza carga de datos adicionales ni consultas a la base de datos.
     * </p>
     *
     * <p>
     * La carga completa de estos agregados es responsabilidad del
     * caso de uso correspondiente.
     * </p>
     *
     * @param entity Entidad JPA obtenida desde persistencia
     * @return Entidad de dominio Menu
     */
    @Mapping(target = "menuId", source = "id")
    @Mapping(target = "pantalla", source = "codPantalla", qualifiedByName = "pantallaFromId")
    @Mapping(target = "modulo", source = "codModulo", qualifiedByName = "moduloFromId")
    @Mapping(target = "menuPadre", source = "codMenuPadre", qualifiedByName = "menuFromId")
    @Mapping(target = "estado", source = "estado", qualifiedByName = "codigoToEstado")
    Menu toDomain(MenuJpaEntity entity);//a Menu  desde entidad JPA

    /**
     * Aplica los valores de un Menu de dominio sobre una entidad JPA existente.
     *
     * <p>
     * Este método se utiliza en operaciones de actualización (UPDATE).
     * No se modifica el identificador de la entidad JPA.
     * </p>
     *
     * <p>
     * La entidad JPA debe estar previamente cargada y gestionada
     * por el contexto de persistencia.
     * </p>
     *
     * @param domain Entidad de dominio con los nuevos valores
     * @param entity Entidad JPA existente a actualizar
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codPantalla", source = "pantalla", qualifiedByName = "pantallaToId")
    @Mapping(target = "codModulo", source = "modulo", qualifiedByName = "moduloToId")
    @Mapping(target = "codMenuPadre", source = "menuPadre", qualifiedByName = "menuToId")
    @Mapping(target = "estado", source = "estado", qualifiedByName = "estadoToCodigoJpa")
    void applyToEntity(Menu domain, @MappingTarget MenuJpaEntity entity);

    // =====================
    // CONSTRUCTORES PARCIALES
    // =====================

    /**
     * Construye una referencia parcial de Pantalla a partir de su identificador.
     *
     * <p>
     * No se realiza ninguna consulta a la base de datos.
     * El objeto resultante representa únicamente una referencia
     * al agregado Pantalla.
     * </p>
     *
     * @param codPantalla Identificador de la pantalla
     * @return Referencia parcial de Pantalla o {@code null} si el código es nulo
     */
    @Named("pantallaFromId")
    default Pantalla pantallaFromId(BigInteger codPantalla) {
        if (codPantalla == null) return null;
        Pantalla p = new Pantalla();
        p.setId(codPantalla.longValue());
        return p;
    }

    /**
     * Construye una referencia parcial de Modulo a partir de su identificador.
     *
     * <p>
     * Se utiliza para reconstruir el dominio sin cargar el agregado completo.
     * </p>
     *
     * @param codModulo Identificador del módulo
     * @return Referencia parcial de Modulo o {@code null} si el código es nulo
     */
    @Named("moduloFromId")
    default Modulo moduloFromId(BigInteger codModulo) {
        if (codModulo == null) return null;
        Modulo m = new Modulo();
        m.setId(codModulo);
        return m;
    }

    /**
     * Construye una referencia parcial de Menu que representa al menú padre.
     *
     * <p>
     * El menú resultante contiene únicamente su identificador
     * y se utiliza para mantener la relación jerárquica.
     * </p>
     *
     * @param codMenuPadre Identificador del menú padre
     * @return Referencia parcial de Menu o {@code null} si el código es nulo
     */
    @Named("menuFromId")
    default Menu menuFromId(BigInteger codMenuPadre) {
        if (codMenuPadre == null) return null;
        Menu m = new Menu();
        m.setMenuId(codMenuPadre);
        return m;
    }

    /**
     * Extrae el identificador de una Pantalla de dominio.
     *
     * <p>
     * Se utiliza durante la conversión de dominio a JPA
     * para persistir la relación mediante su ID.
     * </p>
     *
     * @param pantalla Objeto Pantalla del dominio
     * @return Identificador de la pantalla o {@code null} si no existe
     */

    @Named("pantallaToId")
    default BigInteger pantallaToId(Pantalla pantalla) {
        return pantalla != null ? BigInteger.valueOf(pantalla.getId()): null;
    }

    /**
     * Extrae el identificador de un Modulo de dominio.
     *
     * @param modulo Objeto Modulo del dominio
     * @return Identificador del módulo o {@code null} si no existe
     */
    /**
     * Extrae el identificador de un Modulo de dominio.
     *
     * @param modulo Objeto Modulo del dominio
     * @return Identificador del módulo o {@code null} si no existe
     */
    @Named("moduloToId")
    default BigInteger moduloToId(Modulo modulo) {
        return modulo != null ? modulo.getId(): null;
    }

    /**
     * Extrae el identificador de un Menu (menú padre) de dominio.
     * 
     * <p>
     * Se utiliza durante la conversión de dominio a JPA para persistir
     * la relación jerárquica mediante el ID del menú padre.
     * </p>
     *
     * @param menu Objeto Menu del dominio
     * @return Identificador del menú o {@code null} si no existe
     */
    @Named("menuToId")
    default BigInteger menuToId(Menu menu) {
        return menu != null ? menu.getMenuId() : null;
    }

    /**
     * Convierte un Estado de dominio a su código numérico para persistencia.
     * 
     * <p>
     * El Estado es un Value Object que encapsula un código numérico.
     * Este método extrae ese código para almacenarlo en BD.
     * </p>
     *
     * @param estado Enum Estado (ACTIVO, INACTIVO)
     * @return Código numérico del estado (1=ACTIVO, 0=INACTIVO) o {@code null}
     */
    @Named("estadoToCodigoJpa")
    default BigInteger estadoToCodigoJpa(Estado estado) {
        return estado != null
                ? new BigInteger(estado.getCodigo().toString())
                : null;
    }

    /**
     * Convierte un código numérico de BD a Estado de dominio.
     * 
     * <p>
     * Transforma el código persistido (1, 0) al enum Estado correspondiente.
     * Complemento inverso de estadoToCodigoJpa().
     * </p>
     *
     * @param codigo Código numérico del estado desde BD (1=ACTIVO, 0=INACTIVO)
     * @return Enum Estado o {@code null} si código es nulo
     */
    @Named("codigoToEstado")
    default Estado codigoToEstado(BigInteger codigo) {
        return codigo != null
                ? Estado.fromCodigo(codigo.intValue())
                : null;
    }

}
