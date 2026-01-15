package security.dominio.entidades;

import security.dominio.exceptions.SecurityValidationException;
import security.dominio.vo.Estado;

import java.math.BigInteger;
import java.util.Objects;

public class Menu {
    private BigInteger menuId;
    private String nombre;
    private BigInteger jerarquia;
    private BigInteger orden;
    private Pantalla pantalla;
    private Modulo modulo;
    private Menu menuPadre;
    private String icono;
    private Estado estado;

    public Menu() {
    }
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getMenuId() {
        return menuId;
    }

    public void setMenuId(BigInteger menuId) {
        this.menuId = menuId;
    }

    public BigInteger getJerarquia() {
        return jerarquia;
    }

    public void setJerarquia(BigInteger gerarquia) {
        this.jerarquia = gerarquia;
    }

    public BigInteger getOrden() {
        return orden;
    }

    public void setOrden(BigInteger orden) {
        this.orden = orden;
    }

    public Pantalla getPantalla() {
        return pantalla;
    }

    public void setPantalla(Pantalla pantalla) {
        this.pantalla = pantalla;
    }

    public Menu getMenuPadre() {
        return menuPadre;
    }

    public void setMenuPadre(Menu menuPadre) {
        this.menuPadre = menuPadre;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(menuId, menu.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId);
    }

    public void validar() {
        if(nombre == null || nombre.trim().isBlank()){
            throw new SecurityValidationException("nombre", "Ingrese un nombre valido");
        }
        if (jerarquia == null || jerarquia.compareTo(BigInteger.ZERO)<0) {
            throw new SecurityValidationException("jerarquia", "No estar vacia o ser menor a cero");
        }

        if (orden == null || orden.compareTo(BigInteger.ZERO)<0) {
            throw new SecurityValidationException("orden", "No puede estar vacio o el orden no puede ser menor a cero");
        }

        if(pantalla != null && (menuPadre == null || menuPadre.getMenuId() == null)){
            throw new SecurityValidationException("menu padre", "Si ingresa una pantalla seleccione el menu padre");
        }
        // Validar apellido
        if (estado == null) {
            throw new SecurityValidationException("estado", "El estado no puede ser null");
        }

    }

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
