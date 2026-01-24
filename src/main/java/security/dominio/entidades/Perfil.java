package security.dominio.entidades;

import security.dominio.exceptions.SecurityValidationException;

import java.math.BigInteger;

/**
 * Entidad de Dominio: Perfil
 * 
 * Representa un rol o perfil de usuario en el sistema de seguridad.
 * Un perfil define los permisos y acceso a menús que tiene un usuario.
 * 
 * Características principales:
 * - Tiene un identificador único
 * - Posee un nombre descriptivo (ej: "Administrador", "Usuario", "Operario")
 * - Define los permisos y menús accesibles para usuarios con ese perfil
 * 
 * Relaciones:
 * - Está asociado a múltiples {@link Menu} a través de {@link MenuPerfil}
 * - Es asignado a múltiples usuarios en el sistema
 * 
 * Ejemplos de Perfiles:
 * - Administrador: acceso a todas las funciones
 * - Supervisor: acceso a funciones de supervisión
 * - Operario: acceso solo a funciones específicas
 * - Invitado: acceso limitado a consultas
 */
public class Perfil {
    /** Identificador único del perfil */
    BigInteger id;
    /** Nombre descriptivo del perfil */
    String nombre;

    /**
     * Constructor sin parámetros.
     * Utilizado por frameworks ORM (ej: Hibernate, Quarkus).
     */
    public Perfil() {
    }

    /**
     * Constructor completo.
     * 
     * @param id     Identificador único del perfil
     * @param nombre Nombre descriptivo del perfil
     */
    public Perfil(BigInteger id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }


    /**
     * Constructor sin identificador (para nuevos perfiles).
     * El ID se asignará al persistir en BD.
     * 
     * @param nombre Nombre descriptivo del perfil
     */
    public Perfil(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el ID del perfil.
     * @return ID del perfil
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Establece el ID del perfil.
     * @param id ID a asignar
     */
    public void setId(BigInteger id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del perfil.
     * @return nombre del perfil
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del perfil.
     * @param nombre nombre a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Valida que el perfil cumpla con las reglas de negocio.
     *
     * Reglas de validación:
     * - El nombre no puede ser nulo o vacío
     *
     * @throws SecurityValidationException si la validación falla
     */
    public void validar() {
        if (nombre == null || nombre.trim().isBlank()) {
            throw new SecurityValidationException("nombre", "El nombre del perfil no puede ir vacío");
        }
    }
}
