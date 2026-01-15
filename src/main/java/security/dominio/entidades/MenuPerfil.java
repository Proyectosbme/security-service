package security.dominio.entidades;

import java.util.Objects;

public class MenuPerfil {
    Menu menu;
    Perfil perfil;

    public MenuPerfil() {
    }

    public MenuPerfil(Menu menu, Perfil perfil) {
        this.menu = menu;
        this.perfil = perfil;
    }


    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuPerfil that = (MenuPerfil) o;
        return Objects.equals(menu, that.menu) && Objects.equals(perfil, that.perfil);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menu, perfil);
    }

    @Override
    public String toString() {
        return "MenuPerfil{" +
                "menu=" + menu +
                ", perfil=" + perfil +
                '}';
    }
}
