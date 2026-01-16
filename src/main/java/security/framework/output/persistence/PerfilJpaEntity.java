package security.framework.output.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

/**
 * Entidad JPA: PerfilJpaEntity
 * 
 * Mapeo ORM para la tabla "perfiles" en BD.
 * Representa un perfil o rol de usuario persistido en BD.
 * 
 * Responsabilidad: Encapsular datos de perfil en formato JPA/Hibernate.
 * 
 * Ciclo de Vida:
 * CREATE: PerfilRepositoryAdapter.save() → PerfilJpaEntity → persist() → BD
 * READ: BD → PerfilJpaEntity → PerfilRepositoryAdapter.findById() → Perfil (dominio)
 * UPDATE: PerfilRepositoryAdapter.update() → PerfilJpaEntity.merge() → BD
 * DELETE: PerfilRepositoryAdapter.deleteById() → PerfilJpaEntity.delete() → BD
 * 
 * Anotaciones JPA:
 * - @Entity: Indica que es entidad JPA
 * - @Table(name="perfiles", schema="kafka"): Mapea a tabla "perfiles" en schema "kafka"
 * - @Id: Campo clave primaria
 * - @GeneratedValue: ID auto-incrementado por BD
 * 
 * Lombok:
 * - @Getter, @Setter: Genera getters/setters automáticamente
 * - @NoArgsConstructor: Constructor sin argumentos
 * - @AllArgsConstructor: Constructor con todos los campos
 * 
 * @author Security Team
 * @version 1.0
 */
@Entity
@Table(name = "perfiles", schema = "kafka")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerfilJpaEntity {
    
    /**
     * ID único del perfil (clave primaria)
     * Auto-incrementado por BD (GenerationType.IDENTITY)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    
    /**
     * Nombre descriptivo del perfil
     * Ejemplos: "Administrador", "Supervisor", "Operario", "Invitado"
     */
    private String nombre;
}
