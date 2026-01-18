package security.dominio.entidades;

import java.math.BigInteger;
import java.util.Objects;

/**
 * Entidad de Dominio: MenuPerfil
 * 
 * Representa la relación muchos-a-muchos entre Menús y Perfiles.
 * Define qué menús están disponibles para cada perfil de usuario.
 * 
 * Características principales:
 * - Es una entidad de asociación entre {@link Menu} y {@link Perfil}
 * - Permite definir permisos de acceso a menús por perfil
 * - La clave primaria es compuesta (menu + perfil)
 * 
 * Ejemplo:
 * Un MenuPerfil podría indicar:
 * - El menú "Crear Usuario" está disponible para el perfil "Administrador"
 * - El menú "Cambiar Contraseña" está disponible para todos los usuarios
 * - El menú "Eliminar Usuario" está disponible solo para "Administrador"
 * 
 * Relaciones:
 * - Referencias un {@link Menu}
 * - Referencias un {@link Perfil}
 */
public class MenuPerfil {
    /** ID del menú */
    BigInteger menuId;
    /** ID del perfil */
    BigInteger perfilId;

    /**
     * Constructor sin parámetros.
     * Utilizado por frameworks ORM (ej: Hibernate, Quarkus).
     */
    public MenuPerfil() {
    }

    /**
     * Constructor completo.
     * 
     * @param menuId   ID del menú
     * @param perfilId ID del perfil
     */
    public MenuPerfil(BigInteger menuId, BigInteger perfilId) {
        this.menuId = menuId;
        this.perfilId = perfilId;
    }


    /**
     * Obtiene el ID del menú.
     * @return ID del menú
     */
    public BigInteger getMenuId() {
        return menuId;
    }

    /**
     * Establece el ID del menú.
     * @param menuId ID del menú
     */
    public void setMenuId(BigInteger menuId) {
        this.menuId = menuId;
    }

    /**
     * Obtiene el ID del perfil.
     * @return ID del perfil
     */
    public BigInteger getPerfilId() {
        return perfilId;
    }

    /**
     * Establece el ID del perfil.
     * @param perfilId ID del perfil
     */
    public void setPerfilId(BigInteger perfilId) {
        this.perfilId = perfilId;
    }

    /**
     * Compara dos MenuPerfil basándose en menuId y perfilId.
     * @param o objeto a comparar
     * @return true si tienen el mismo menuId y perfilId
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuPerfil that = (MenuPerfil) o;
        return Objects.equals(menuId, that.menuId) && Objects.equals(perfilId, that.perfilId);
    }

    /**
     * Calcula el hash basándose en menuId y perfilId.
     * @return código hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(menuId, perfilId);
    }

    /**
     * Representa textualmente la asociación MenuPerfil.
     * @return String con los datos de la asociación
     */
    @Override
    public String toString() {
        return "MenuPerfil{" +
                "menuId=" + menuId +
                ", perfilId=" + perfilId +
                '}';
    }
}
