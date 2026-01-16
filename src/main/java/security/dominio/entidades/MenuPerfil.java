package security.dominio.entidades;

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
    /** Menú asociado a este perfil */
    Menu menu;
    /** Perfil con acceso a este menú */
    Perfil perfil;

    /**
     * Constructor sin parámetros.
     * Utilizado por frameworks ORM (ej: Hibernate, Quarkus).
     */
    public MenuPerfil() {
    }

    /**
     * Constructor completo.
     * 
     * @param menu   Menú a asociar
     * @param perfil Perfil a asociar
     */
    public MenuPerfil(Menu menu, Perfil perfil) {
        this.menu = menu;
        this.perfil = perfil;
    }


    /**
     * Obtiene el menú asociado.
     * @return menú de esta asociación
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * Establece el menú asociado.
     * @param menu menú a establecer
     */
    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    /**
     * Obtiene el perfil asociado.
     * @return perfil de esta asociación
     */
    public Perfil getPerfil() {
        return perfil;
    }

    /**
     * Establece el perfil asociado.
     * @param perfil perfil a establecer
     */
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    /**
     * Compara dos MenuPerfil basándose en el menú y perfil.
     * @param o objeto a comparar
     * @return true si tienen el mismo menú y perfil
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuPerfil that = (MenuPerfil) o;
        return Objects.equals(menu, that.menu) && Objects.equals(perfil, that.perfil);
    }

    /**
     * Calcula el hash basándose en el menú y perfil.
     * @return código hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(menu, perfil);
    }

    /**
     * Representa textualmente la asociación MenuPerfil.
     * @return String con los datos de la asociación
     */
    @Override
    public String toString() {
        return "MenuPerfil{" +
                "menu=" + menu +
                ", perfil=" + perfil +
                '}';
    }
}
