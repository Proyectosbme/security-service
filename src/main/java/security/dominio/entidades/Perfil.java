package security.dominio.entidades;

import java.math.BigInteger;

public class Perfil {
    BigInteger id;
    String nombre;

    public Perfil() {
    }

    public Perfil(BigInteger id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Perfil(String nombre) {
        this.nombre = nombre;
    }
}
