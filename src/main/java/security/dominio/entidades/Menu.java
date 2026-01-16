package security.dominio.entidades;
import security.dominio.exceptions.SecurityValidationException;
import security.dominio.vo.Estado;
import java.math.BigInteger;
import java.util.Objects;

/**
 * Entidad de Dominio: Menu
 * 
 * Representa un elemento de menú en el sistema de seguridad.
 * Un menú puede ser una opción principal o un submenú dentro de una jerarquía.
 * 
 * Características principales:
 * - Tiene un identificador único (menuId)
 * - Pertenece a un módulo específico
 * - Puede tener un menú padre (para submenús)
 * - Está asociado a una pantalla (interfaz de usuario)
 * - Tiene una jerarquía y orden para la visualización
 * - Puede tener un icono representativo
 * - Tiene un estado (ACTIVO o INACTIVO)
 * 
 * Relaciones:
 * - Pertenece a un {@link Modulo}
 * - Puede referenciar otro {@link Menu} como padre
 * - Está asociado a una {@link Pantalla}
 * - Puede asignarse a múltiples {@link Perfil} a través de {@link MenuPerfil}
 * 
 * Validaciones:
 * - El nombre no puede estar vacío
 * - La jerarquía no puede ser nula ni negativa
 * - El orden no puede ser nulo ni negativo
 * - Si hay una pantalla asociada, debe haber un menú padre
 * - El estado es obligatorio
 */
public class Menu {
    /** Identificador único del menú */
    private BigInteger menuId;
    /** Nombre descriptivo del menú visible al usuario */
    private String nombre;
    /** Nivel de jerarquía del menú (0 para menús raíz) */
    private BigInteger jerarquia;
    /** Posición del menú dentro de su nivel jerárquico */
    private BigInteger orden;
    /** Pantalla/interfaz de usuario asociada al menú */
    private Pantalla pantalla;
    /** Módulo al que pertenece este menú */
    private Modulo modulo;
    /** Menú padre (nulo si es un menú raíz) */
    private Menu menuPadre;
    /** Icono representativo del menú (ej: "fa-home", "fa-user") */
    private String icono;
    /** Estado actual del menú (ACTIVO o INACTIVO) */
    private Estado estado;

    /**
     * Constructor sin parámetros.
     * Utilizado por frameworks ORM (ej: Hibernate, Quarkus).
     */
    public Menu() {
    }
    
    /**
     * Constructor completo con identificador.
     * 
     * @param menuId        Identificador único del menú
     * @param nombre        Nombre del menú
     * @param jerarquia     Nivel jerárquico
     * @param orden         Posición en el nivel
     * @param pantalla      Pantalla asociada
     * @param modulo        Módulo propietario
     * @param MenuPadre     Menú padre (puede ser nulo)
     * @param estado        Estado actual
     * @param icono         Icono representativo
     */
    public Menu(BigInteger menuId,String nombre, BigInteger jerarquia, BigInteger orden, Pantalla pantalla, Modulo modulo, Menu MenuPadre,
                Estado estado, String icono) {
        this.menuId = menuId;
        this.jerarquia = jerarquia;
        this.orden = orden;
        this.pantalla = pantalla;
        this.menuPadre = MenuPadre;
        this.icono= icono;
        this.modulo=modulo;
        this.estado=estado;
        this.nombre=nombre;
    }
    
    /**
     * Constructor sin identificador (para nuevos menús).
     * El ID se asignará al persistir en BD.
     * 
     * @param nombre        Nombre del menú
     * @param jerarquia     Nivel jerárquico
     * @param orden         Posición en el nivel
     * @param pantalla      Pantalla asociada
     * @param modulo        Módulo propietario
     * @param MenuPadre     Menú padre (puede ser nulo)
     * @param estado        Estado actual
     * @param icono         Icono representativo
     */
    public Menu(String nombre,BigInteger jerarquia, BigInteger orden, Pantalla pantalla, Modulo modulo, Menu MenuPadre, Estado estado, String icono) {
        this.jerarquia = jerarquia;
        this.orden = orden;
        this.pantalla = pantalla;
        this.menuPadre = MenuPadre;
        this.icono= icono;
        this.modulo=modulo;
        this.estado=estado;
        this.nombre=nombre;
    }

    /**
     * Actualiza los datos del menú con nuevos valores.
     * 
     * Este método es utilizado en los casos de uso de actualización.
     * No modifica el identificador ni la fecha de creación.
     * 
     * @param nombre        Nuevo nombre
     * @param jerarquia     Nuevo nivel jerárquico
     * @param orden         Nueva posición
     * @param pantalla      Nueva pantalla asociada
     * @param modulo        Nuevo módulo
     * @param MenuPadre     Nuevo menú padre
     * @param estado        Nuevo estado
     * @param icono         Nuevo icono
     */
    public void actualizar(String nombre,BigInteger jerarquia, BigInteger orden, Pantalla pantalla, Modulo modulo, Menu MenuPadre, Estado estado, String icono){
        this.nombre=nombre;
        this.jerarquia = jerarquia;
        this.orden = orden;
        this.pantalla = pantalla;
        this.menuPadre = MenuPadre;
        this.icono= icono;
        this.modulo=modulo;
        this.estado=estado;
    }

    /**
     * Obtiene el nombre del menú.
     * @return nombre descriptivo del menú
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del menú.
     * @param nombre nombre a establecer
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador único del menú.
     * @return ID del menú
     */
    public BigInteger getMenuId() {
        return menuId;
    }

    /**
     * Establece el identificador del menú.
     * @param menuId ID a establecer
     */
    public void setMenuId(BigInteger menuId) {
        this.menuId = menuId;
    }

    /**
     * Obtiene el nivel jerárquico del menú.
     * @return nivel de jerarquía
     */
    public BigInteger getJerarquia() {
        return jerarquia;
    }

    /**
     * Establece el nivel jerárquico del menú.
     * @param gerarquia nivel a establecer
     */
    public void setJerarquia(BigInteger gerarquia) {
        this.jerarquia = gerarquia;
    }

    /**
     * Obtiene la posición del menú en su nivel.
     * @return orden/posición
     */
    public BigInteger getOrden() {
        return orden;
    }

    /**
     * Establece la posición del menú.
     * @param orden posición a establecer
     */
    public void setOrden(BigInteger orden) {
        this.orden = orden;
    }

    /**
     * Obtiene la pantalla asociada al menú.
     * @return pantalla UI del menú
     */
    public Pantalla getPantalla() {
        return pantalla;
    }

    /**
     * Establece la pantalla del menú.
     * @param pantalla pantalla a asociar
     */
    public void setPantalla(Pantalla pantalla) {
        this.pantalla = pantalla;
    }

    /**
     * Obtiene el menú padre (para submenús).
     * @return menú padre o null si es menú raíz
     */
    public Menu getMenuPadre() {
        return menuPadre;
    }

    /**
     * Establece el menú padre.
     * @param menuPadre menú padre a establecer
     */
    public void setMenuPadre(Menu menuPadre) {
        this.menuPadre = menuPadre;
    }

    /**
     * Obtiene el icono del menú.
     * @return nombre/ruta del icono
     */
    public String getIcono() {
        return icono;
    }

    /**
     * Establece el icono del menú.
     * @param icono icono a establecer
     */
    public void setIcono(String icono) {
        this.icono = icono;
    }

    /**
     * Obtiene el módulo propietario del menú.
     * @return módulo asociado
     */
    public Modulo getModulo() {
        return modulo;
    }

    /**
     * Establece el módulo del menú.
     * @param modulo módulo a asociar
     */
    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    /**
     * Obtiene el estado actual del menú.
     * @return estado (ACTIVO o INACTIVO)
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Establece el estado del menú.
     * @param estado estado a establecer
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     * Compara dos menús basándose en su ID.
     * @param o objeto a comparar
     * @return true si tienen el mismo ID
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(menuId, menu.menuId);
    }

    /**
     * Calcula el hash del menú basándose en su ID.
     * @return código hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(menuId);
    }

    /**
     * Representa textualmente el menú con toda su información.
     * @return String con los datos del menú
     */
    @Override
    public String toString() {
        return "Menus{" +
                "Nombre" +nombre+
                "menuId=" + menuId +
                ", jerarquia=" + jerarquia +
                ", orden=" + orden +
                ", pantalla=" + pantalla +
                ", modulo=" + modulo +
                ", menuIdPadre=" + menuPadre +
                ", icono='" + icono + '\'' +
                ", estado=" + estado +
                '}';
    }
}
