package security.framework.output.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

/**
 * Entidad JPA: ModuloJpaEntity
 * 
 * Mapeo ORM para la tabla "modulos" en BD.
 * Representa un módulo funcional de aplicación persistido en BD.
 * 
 * Responsabilidad: Encapsular datos de módulo en formato JPA/Hibernate.
 * 
 * Ciclo de Vida:
 * CREATE: ModuloRepositoryAdapter.save() → ModuloJpaEntity → persist() → BD
 * READ: BD → ModuloJpaEntity → ModuloRepositoryAdapter.findById() → Modulo (dominio)
 * UPDATE: ModuloRepositoryAdapter.update() → ModuloJpaEntity.merge() → BD
 * DELETE: ModuloRepositoryAdapter.deleteById() → ModuloJpaEntity.delete() → BD
 * 
 * Anotaciones JPA:
 * - @Entity: Indica que es entidad JPA
 * - @Table(name="modulos", schema="kafka"): Mapea a tabla "modulos" en schema "kafka"
 * - @Id: Campo clave primaria
 * - @GeneratedValue: ID auto-incrementado por BD
 * 
 * Lombok:
 * - @Getter, @Setter: Genera getters/setters automáticamente
 * - @NoArgsConstructor: Constructor sin argumentos
 * - @AllArgsConstructor: Constructor con todos los campos
 * 
 * Estructura Simplificada:
 * Módulo es la entidad más simple: solo id y nombre
 * No tiene relaciones complejas ni conversiones especiales
 */
@Entity
@Table(name = "modulos", schema = "kafka")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModuloJpaEntity {
    
    /**
     * ID único del módulo (clave primaria)
     * Auto-incrementado por BD (GenerationType.IDENTITY)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    /**
     * Nombre único del módulo funcional
     * Ejemplos: "Administración", "Reportes", "Seguridad"
     */
    String nombre;
}
