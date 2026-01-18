package security.framework.output.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Clave primaria compuesta para MenuPerfilJpaEntity
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MenuPerfilId implements Serializable {
    
    @Column(name = "menu_id")
    private Long menuId;
    
    @Column(name = "perfil_id")
    private Long perfilId;
}
