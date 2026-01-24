package security.framework.output.mapper;

import security.dominio.entidades.Menu;
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
 * Las relaciones se manejan únicamente mediante sus identificadores:
 *
 * <ul>
 *   <li>DOMINIO → JPA: se asignan los IDs directamente</li>
 *   <li>JPA → DOMINIO: se asignan los IDs directamente</li>
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
    @Mapping(target = "codPantalla", source = "pantallaId")
    @Mapping(target = "codModulo", source = "moduloId")
    @Mapping(target = "codMenuPadre", source = "menuPadreId")
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
    @Mapping(target = "pantallaId", source = "codPantalla")
    @Mapping(target = "moduloId", source = "codModulo")
    @Mapping(target = "menuPadreId", source = "codMenuPadre")
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
    @Mapping(target = "codPantalla", source = "pantallaId")
    @Mapping(target = "codModulo", source = "moduloId")
    @Mapping(target = "codMenuPadre", source = "menuPadreId")
    @Mapping(target = "estado", source = "estado", qualifiedByName = "estadoToCodigoJpa")
    void applyToEntity(Menu domain, @MappingTarget MenuJpaEntity entity);

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
