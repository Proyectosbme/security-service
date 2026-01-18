package security.framework.output.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * Entidad JPA: PantallaJpaEntity
 * 
 * Mapeo ORM para la tabla "pantallas" en BD.
 * Representa una pantalla o interfaz de usuario persistida en BD.
 * 
 * Responsabilidad: Encapsular datos de pantalla en formato JPA/Hibernate.
 * 
 * Ciclo de Vida:
 * CREATE: PantallaRepositoryAdapter.save() → PantallaJpaEntity → persist() → BD
 * READ: BD → PantallaJpaEntity → PantallaRepositoryAdapter.findById() → Pantalla (dominio)
 * UPDATE: PantallaRepositoryAdapter.update() → PantallaJpaEntity.merge() → BD
 * DELETE: PantallaRepositoryAdapter.deleteById() → PantallaJpaEntity.delete() → BD
 * 
 * Anotaciones JPA:
 * - @Entity: Indica que es entidad JPA
 * - @Table(name="pantallas", schema="kafka"): Mapea a tabla "pantallas" en schema "kafka"
 * - @Id: Campo clave primaria
 * - @GeneratedValue: ID auto-incrementado por BD
 * 
 * Auditoría:
 * - userC: Usuario que creó el registro (no modificable después de CREATE)
 * - fechaC: Fecha de creación (no modificable después de CREATE)
 * - userMod: Usuario que realizó la última modificación
 * - fechaMod: Fecha de la última modificación
 * 
 * Lombok:
 * - @Getter, @Setter: Genera getters/setters automáticamente
 * - @NoArgsConstructor: Constructor sin argumentos
 * - @AllArgsConstructor: Constructor con todos los campos
 */
@Entity
@Table(name = "pantallas", schema = "kafka")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PantallaJpaEntity {
    
    /**
     * ID único de la pantalla (clave primaria)
     * Auto-incrementado por BD (GenerationType.IDENTITY)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * ID del módulo al que pertenece la pantalla
     */
    private BigInteger codModulo;
    
    /**
     * Nombre o identificador de la pantalla
     */
    private String nombre;
    
    /**
     * URL o ruta de acceso a la pantalla
     * Ejemplo: "/admin/usuarios", "/reportes/ventas"
     */
    private String url;
    
    /**
     * Usuario que creó la pantalla (auditoría de creación)
     * Inmutable después de CREATE
     */
    private String userC;
    
    /**
     * Fecha de creación de la pantalla (auditoría de creación)
     * Inmutable después de CREATE
     */
    private LocalDateTime fechaC;
    
    /**
     * Usuario que realizó la última modificación (auditoría de actualización)
     * Se actualiza en cada UPDATE
     */
    private String userMod;
    
    /**
     * Fecha de la última modificación (auditoría de actualización)
     * Se actualiza en cada UPDATE
     */
    private LocalDateTime fechaMod;
}
