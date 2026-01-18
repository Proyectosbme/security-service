package security.dominio.entidades;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;
import security.dominio.exceptions.SecurityValidationException;

/**
 * Entidad de Dominio: Pantalla
 * 
 * Representa una pantalla o interfaz de usuario en el sistema de seguridad.
 * Una pantalla es un componente visual del sistema que está asociado a menús.
 * 
 * Características principales:
 * - Pertenece a un módulo específico
 * - Tiene un nombre y URL de acceso
 * - Guarda auditoría completa (creación y modificación)
 * 
 * Auditoría:
 * - userC: Usuario que creó la pantalla (inmutable después de crear)
 * - fechaC: Fecha de creación (inmutable después de crear)
 * - userMod: Usuario que modificó la pantalla (actualizado en cada cambio)
 * - fechaMod: Fecha de última modificación (actualizada en cada cambio)
 * 
 * Relaciones:
 * - Pertenece a un {@link Modulo}
 * - Es referenciada por uno o múltiples {@link Menu}
 * 
 * Ejemplo:
 * Una pantalla "Gestión de Usuarios" podría tener:
 * - ID: 1
 * - Nombre: "GestionUsuarios"
 * - URL: "/admin/usuarios"
 * - Módulo: Administración
 * - userC: "admin" | fechaC: 2025-01-16T10:30:00
 * - userMod: "admin2" | fechaMod: 2025-01-20T14:45:30
 */
public class Pantalla {
    /** ID único (PK) de la pantalla */
    Long id;
    /** Módulo al que pertenece la pantalla */
    Modulo modulo;
    /** Nombre o identificador de la pantalla */
    String nombre;
    /** URL o ruta de acceso a la pantalla */
    String url;
    /** Usuario que creó la pantalla (auditoría - inmutable) */
    String userc;
    /** Fecha de creación de la pantalla (auditoría - inmutable) */
    LocalDateTime fechaC;
    /** Usuario que modificó la pantalla (auditoría - mutable) */
    String usermod;
    /** Fecha de última modificación (auditoría - mutable) */
    LocalDateTime fechamod;

    /**
     * Constructor sin parámetros.
     * Utilizado por frameworks ORM (ej: Hibernate, Quarkus).
     */
    public Pantalla() {
    }

    /**
     * Constructor completo con todos los campos.
     * 
     * @param id ID único de la pantalla
     * @param modulo Módulo propietario
     * @param nome Nombre de la pantalla
     * @param url URL o ruta de acceso
     * @param userc Usuario que creó la pantalla
     * @param fechaC Fecha de creación
     * @param usermod Usuario que modificó la pantalla
     * @param fechamod Fecha de última modificación
     */
    public Pantalla(Long id, Modulo modulo, String nombre, String url, 
                   String userc, LocalDateTime fechaC, String usermod, LocalDateTime fechamod) {
        this.id = id;
        this.modulo = modulo;
        this.nombre = nombre;
        this.url = url;
        this.userc = userc;
        this.fechaC = fechaC;
        this.usermod = usermod;
        this.fechamod = fechamod;
    }

    /**
     * Constructor sin ID (para nuevas pantallas - creación).
     * El ID se asignará al persistir en BD.
     * 
     * @param modulo Módulo propietario
     * @param nome Nombre de la pantalla
     * @param url URL o ruta de acceso
     * @param userc Usuario que creó la pantalla
     * @param fechaC Fecha de creación
     */
    public Pantalla(Modulo modulo, String nombre, String url, 
                   String userc, LocalDateTime fechaC) {
        this.modulo = modulo;
        this.nombre = nombre;
        this.url = url;
        this.userc = userc;
        this.fechaC = fechaC;
    }

    /**
     * Obtiene el ID único de la pantalla.
     * @return ID de la pantalla
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID de la pantalla.
     * @param id ID a establecer
     */
    public void setId(Long id) {
        this.id = id;
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
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la pantalla.
     * @param nombre nombre a establecer
     */
    public void setNombre(String nombre) {
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
     * Obtiene el usuario que creó la pantalla (auditoría - inmutable).
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
     * Obtiene la fecha de creación de la pantalla (auditoría - inmutable).
     * @return fecha de creación
     */
    public LocalDateTime getFechaC() {
        return fechaC;
    }

    /**
     * Establece la fecha de creación de la pantalla.
     * @param fechaC fecha a establecer
     */
    public void setFechaC(LocalDateTime fechaC) {
        this.fechaC = fechaC;
    }

    /**
     * Obtiene el usuario que modificó la pantalla (auditoría - mutable).
     * @return usuario modificador
     */
    public String getUsermod() {
        return usermod;
    }

    /**
     * Establece el usuario modificador de la pantalla.
     * @param usermod usuario a establecer
     */
    public void setUsermod(String usermod) {
        this.usermod = usermod;
    }

    /**
     * Obtiene la fecha de última modificación (auditoría - mutable).
     * @return fecha de modificación
     */
    public LocalDateTime getFechamod() {
        return fechamod;
    }

    /**
     * Establece la fecha de última modificación.
     * @param fechamod fecha a establecer
     */
    public void setFechamod(LocalDateTime fechamod) {
        this.fechamod = fechamod;
    }

    /**
     * Valida los campos obligatorios de la pantalla según reglas de negocio.
     * 
     * Validaciones:
     * 1. Nombre (nombre) no puede ser nulo o vacío
     * 2. URL no puede ser nula o vacía
     * 3. Módulo no puede ser nulo (pantalla debe pertenecer a un módulo)
     * 
     * Nota: Los campos de auditoría (userc, fechaC, usermod, fechaMod) no se validan aquí
     * porque son asignados por los casos de uso, no por el usuario.
     * 
     * @throws SecurityValidationException si alguna validación falla
     */
    public void validar() {
        if (nombre == null || nombre.isBlank()) {
            throw new SecurityValidationException("Pantalla: El nombre no puede ser nulo o vacío");
        }
        
        if (url == null || url.isBlank()) {
            throw new SecurityValidationException("Pantalla: La URL no puede ser nula o vacía");
        }
        
        if (modulo == null) {
            throw new SecurityValidationException("Pantalla: El módulo es obligatorio");
        }
    }

    /**
     * Compara dos pantallas basándose en su ID.
     * @param o objeto a comparar
     * @return true si tienen el mismo ID
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pantalla pantalla = (Pantalla) o;
        return Objects.equals(id, pantalla.id);
    }

    /**
     * Calcula el hash de la pantalla basándose en su ID.
     * @return código hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Representa textualmente la pantalla.
     * @return String con los datos completos de la pantalla
     */
    @Override
    public String toString() {
        return "Pantalla{" +
                "id=" + id +
                ", modulo=" + modulo +
                ", nombre='" + nombre + '\'' +
                ", url='" + url + '\'' +
                ", userc='" + userc + '\'' +
                ", fechaC=" + fechaC +
                ", usermod='" + usermod + '\'' +
                ", fechamod=" + fechamod +
                '}';
    }
}
