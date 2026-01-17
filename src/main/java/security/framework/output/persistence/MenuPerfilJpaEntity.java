package security.framework.output.persistence;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA: MenuPerfilJpaEntity
 * 
 * Tabla intermedia que relaciona menús con perfiles.
 * Representa qué menús tiene acceso cada perfil.
 */
@Entity
@Table(name = "menu_perfil", schema = "kafka")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuPerfilJpaEntity {
    
    @EmbeddedId
    private MenuPerfilId id;
}
