package security.dominio.entidades;

import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

/**
 * Entidad de Dominio: Pantalla
 * 
 * Representa una pantalla o interfaz de usuario en el sistema de seguridad.
 * Una pantalla es un componente visual del sistema que está asociado a menús.
 * 
 * Características principales:
 * - Tiene un código único identificador
 * - Pertenece a un módulo específico
 * - Tiene un nombre y URL de acceso
 * - Guarda auditoría (usuario que creó, fecha de creación)
 * 
 * Relaciones:
 * - Pertenece a un {@link Modulo}
 * - Es referenciada por uno o múltiples {@link Menu}
 * 
 * Ejemplo:
 * Una pantalla "Gestión de Usuarios" podría tener:
 * - Código: 101
 * - Nombre: "GestionUsuarios"
 * - URL: "/admin/usuarios"
 * - Módulo: Administración
 * - Usuario: "admin"
 * - Fecha: 2025-01-16
 */
public class Pantalla {
    /** Código único de la pantalla */
    BigInteger codigo;
    /** Módulo al que pertenece la pantalla */
    Modulo modulo;
    /** Nombre o identificador de la pantalla */
    BigInteger nombre;
    /** URL o ruta de acceso a la pantalla */
    String url;
    /** Usuario que creó la pantalla (auditoría) */
    String userc;
    /** Fecha de creación de la pantalla (auditoría) */
    Date fecha;

    /**
     * Constructor sin parámetros.
     * Utilizado por frameworks ORM (ej: Hibernate, Quarkus).
     */
    public Pantalla() {
    }

    /**
     * Constructor completo.
     * 
     * @param codigo Código único de la pantalla
     * @param modulo Módulo propietario
     * @param nombre Nombre de la pantalla
     * @param url    URL o ruta de acceso
     * @param userc  Usuario que creó la pantalla
     * @param fecha  Fecha de creación
     */
    public Pantalla(BigInteger codigo, Modulo modulo, BigInteger nombre, String url, String userc, Date fecha) {
        this.codigo = codigo;
        this.modulo = modulo;
        this.nombre = nombre;
        this.url=url;
        this.userc = userc;
        this.fecha = fecha;
    }

    /**
     * Constructor sin código (para nuevas pantallas).
     * El código se asignará al persistir en BD.
     * 
     * @param modulo Módulo propietario
     * @param nombre Nombre de la pantalla
     * @param url    URL o ruta de acceso
     * @param userc  Usuario que creó la pantalla
     * @param fecha  Fecha de creación
     */
    public Pantalla(Modulo modulo, BigInteger nombre,String url, String userc, Date fecha) {
        this.modulo = modulo;
        this.nombre = nombre;
        this.url=url;
        this.userc = userc;
        this.fecha = fecha;
    }

    /**
     * Obtiene el código único de la pantalla.
     * @return código de la pantalla
     */
    public BigInteger getCodigo() {
        return codigo;
    }

    /**
     * Establece el código de la pantalla.
     * @param codigo código a establecer
     */
    public void setCodigo(BigInteger codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el módulo propietario de la pantalla.
     * @return módulo asociado
     */
    public Modulo getModulo() {
        return modulo;
    }

    /**
     * Establece el módulo de la pantalla.
     * @param modulo módulo a establecer
     */
    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    /**
     * Obtiene el nombre de la pantalla.
     * @return nombre de la pantalla
     */
    public BigInteger getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la pantalla.
     * @param nombre nombre a establecer
     */
    public void setNombre(BigInteger nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la URL o ruta de acceso a la pantalla.
     * @return URL de la pantalla
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece la URL de la pantalla.
     * @param url URL a establecer
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Obtiene el usuario que creó la pantalla (auditoría).
     * @return usuario creador
     */
    public String getUserc() {
        return userc;
    }

    /**
     * Establece el usuario creador de la pantalla.
     * @param userc usuario a establecer
     */
    public void setUserc(String userc) {
        this.userc = userc;
    }

    /**
     * Obtiene la fecha de creación de la pantalla (auditoría).
     * @return fecha de creación
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de creación de la pantalla.
     * @param fecha fecha a establecer
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Compara dos pantallas basándose en su código y módulo.
     * @param o objeto a comparar
     * @return true si tienen el mismo código y módulo
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pantalla pantalla = (Pantalla) o;
        return Objects.equals(codigo, pantalla.codigo) && Objects.equals(modulo, pantalla.modulo);
    }

    /**
     * Calcula el hash de la pantalla basándose en su código y módulo.
     * @return código hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(codigo, modulo);
    }

    /**
     * Representa textualmente la pantalla.
     * @return String con los datos de la pantalla
     */
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
