package security.framework.output.persistence;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

/**
 * Entidad JPA: MenuJpaEntity
 * 
 * Mapeo ORM para la tabla "menus" en BD.
 * Representa un menú de aplicación persistido en BD.
 * 
 * Responsabilidad: Encapsular datos de menú en formato JPA/Hibernate.
 * 
 * Ciclo de Vida:
 * CREATE: MenuRepositoryAdapter.save() → MenuJpaEntity → persist() → BD
 * READ: BD → MenuJpaEntity → MenuRepositoryAdapter.findById() → Menu (dominio)
 * UPDATE: MenuRepositoryAdapter.update() → MenuJpaEntity.merge() → BD
 * DELETE: MenuRepositoryAdapter.deleteById() → MenuJpaEntity.delete() → BD
 * 
 * Anotaciones JPA:
 * - @Entity: Indica que es entidad JPA
 * - @Table(name="menus", schema="kafka"): Mapea a tabla "menus" en schema "kafka"
 * - @Id: Campo clave primaria
 * - @GeneratedValue: ID auto-incrementado por BD
 * 
 * Lombok:
 * - @Getter, @Setter: Genera getters/setters automáticamente
 * - @NoArgsConstructor: Constructor sin argumentos
 * - @AllArgsConstructor: Constructor con todos los campos
 */
@Entity
@Table(name = "menus", schema = "kafka")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuJpaEntity {
    
    /**
     * ID único del menú (clave primaria)
     * Auto-incrementado por BD (GenerationType.IDENTITY)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Nombre o etiqueta del menú visible en interfaz
     */
    private String nombre;
    
    /**
     * Nivel de jerarquía en estructura de árbol de menús
     */
    private BigInteger jerarquia;
    
    /**
     * Número de orden para visualización en UI
     */
    private BigInteger orden;
    
    /**
     * ID de pantalla/página asociada al menú
     */
    private BigInteger codPantalla;
    
    /**
     * ID del módulo funcional que contiene este menú
     */
    private BigInteger codModulo;
    
    /**
     * ID del menú padre (para menús anidados)
     */
    private BigInteger codMenuPadre;
    
    /**
     * Código o clase de ícono para representación visual
     */
    private String icono;
    
    /**
     * Estado del menú: 1=ACTIVO, 0=INACTIVO
     */
    private BigInteger estado;
}
