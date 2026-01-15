package security.dominio.entidades;

import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

public class Pantalla {
    BigInteger codigo;
    Modulo modulo;
    BigInteger nombre;
    String url;
    String userc;
    Date fecha;

    public Pantalla() {
    }

    public Pantalla(BigInteger codigo, Modulo modulo, BigInteger nombre, String url, String userc, Date fecha) {
        this.codigo = codigo;
        this.modulo = modulo;
        this.nombre = nombre;
        this.url=url;
        this.userc = userc;
        this.fecha = fecha;
    }

    public Pantalla(Modulo modulo, BigInteger nombre,String url, String userc, Date fecha) {
        this.modulo = modulo;
        this.nombre = nombre;
        this.url=url;
        this.userc = userc;
        this.fecha = fecha;
    }

    public BigInteger getCodigo() {
        return codigo;
    }

    public void setCodigo(BigInteger codigo) {
        this.codigo = codigo;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public BigInteger getNombre() {
        return nombre;
    }

    public void setNombre(BigInteger nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserc() {
        return userc;
    }

    public void setUserc(String userc) {
        this.userc = userc;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pantalla pantalla = (Pantalla) o;
        return Objects.equals(codigo, pantalla.codigo) && Objects.equals(modulo, pantalla.modulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, modulo);
    }

    @Override
    public String toString() {
        return "Pantalla{" +
                "codigo=" + codigo +
                ", modulo=" + modulo +
                ", nombre=" + nombre +
                ", url='" + url + '\'' +
                ", userc='" + userc + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
