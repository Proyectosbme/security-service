package security.dominio.entidades;

import security.dominio.exceptions.SecurityValidationException;

import java.math.BigInteger;
import java.util.Objects;

public class Modulo {

    BigInteger id;
    String nombre;

    public Modulo() {
    }

    public Modulo(BigInteger id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Modulo(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void validar(){
        if(nombre == null || nombre.trim().isBlank()){
            throw  new SecurityValidationException("nombre", "El nombre del menu no puede ir vacio");
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Modulo modulo = (Modulo) o;
        return Objects.equals(id, modulo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Modulo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
