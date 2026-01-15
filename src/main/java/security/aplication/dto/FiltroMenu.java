package security.aplication.dto;

import java.math.BigInteger;
import java.util.Objects;

public class FiltroMenu {
    private String nombre;
    private BigInteger codModulo;
    private BigInteger codMenuPadre;
    private BigInteger estado;

    public FiltroMenu() {
    }


    public FiltroMenu( String nombre, BigInteger codModulo, BigInteger codMenuPadre, BigInteger estado) {
        this.nombre = nombre;
        this.codModulo = codModulo;
        this.codMenuPadre = codMenuPadre;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getCodModulo() {
        return codModulo;
    }

    public void setCodModulo(BigInteger codModulo) {
        this.codModulo = codModulo;
    }

    public BigInteger getCodMenuPadre() {
        return codMenuPadre;
    }

    public void setCodMenuPadre(BigInteger codMenuPadre) {
        this.codMenuPadre = codMenuPadre;
    }

    public BigInteger getEstado() {
        return estado;
    }

    public void setEstado(BigInteger estado) {
        this.estado = estado;
    }


}
