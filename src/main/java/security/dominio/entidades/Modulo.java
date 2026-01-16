package security.dominio.entidades;

import security.dominio.exceptions.SecurityValidationException;

import java.math.BigInteger;
import java.util.Objects;

/**
 * Entidad de Dominio: Modulo
 * 
 * Representa un módulo funcional en el sistema de seguridad.
 * Un módulo agrupa elementos relacionados (menús, pantallas) dentro del sistema.
 * 
 * Características principales:
 * - Tiene un identificador único
 * - Posee un nombre descriptivo
 * - Agrupa menús y pantallas relacionadas
 * - Es la unidad funcional básica del sistema de seguridad
 * 
 * Relaciones:
 * - Contiene múltiples {@link Menu}
 * - Contiene múltiples {@link Pantalla}
 * - Está asociado a múltiples {@link Perfil} a través de los menús
 * 
 * Validaciones:
 * - El nombre no puede estar vacío
 * 
 * Ejemplo:
 * Un módulo podría ser "Administración de Usuarios" que contiene
 * menús como "Crear Usuario", "Editar Usuario", etc.
 */
public class Modulo {

    /** Identificador único del módulo */
    BigInteger id;
    /** Nombre descriptivo del módulo */
    String nombre;

    /**
     * Constructor sin parámetros.
     * Utilizado por frameworks ORM (ej: Hibernate, Quarkus).
     */
    public Modulo() {
    }

    /**
     * Constructor completo.
     * 
     * @param id    Identificador único del módulo
     * @param nombre Nombre descriptivo del módulo
     */
    public Modulo(BigInteger id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Constructor sin identificador (para nuevos módulos).
     * El ID se asignará al persistir en BD.
     * 
     * @param nombre Nombre descriptivo del módulo
     */
    public Modulo(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador del módulo.
     * @return ID del módulo
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Establece el identificador del módulo.
     * @param id ID a establecer
     */
    public void setId(BigInteger id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del módulo.
     * @return nombre del módulo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del módulo.
     * @param nombre nombre a establecer
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Valida que el módulo cumpla con las reglas de negocio.
     * 
     * Reglas de validación:
     * - El nombre no puede estar vacío o en blanco
     * 
     * @throws SecurityValidationException si la validación falla
     */
    public void validar(){
        if(nombre == null || nombre.trim().isBlank()){
            throw  new SecurityValidationException("nombre", "El nombre del menu no puede ir vacio");
        }
    }
    
    /**
     * Compara dos módulos basándose en su ID.
     * @param o objeto a comparar
     * @return true si tienen el mismo ID
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Modulo modulo = (Modulo) o;
        return Objects.equals(id, modulo.id);
    }

    /**
     * Calcula el hash del módulo basándose en su ID.
     * @return código hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Representa textualmente el módulo.
     * @return String con los datos del módulo
     */
    @Override
    public String toString() {
        return "Modulo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
