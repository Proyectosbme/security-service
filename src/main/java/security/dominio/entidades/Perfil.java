package security.dominio.entidades;

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
}
