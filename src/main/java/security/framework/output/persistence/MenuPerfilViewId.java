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
 * Clave primaria compuesta para MenuPerfilViewEntity
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MenuPerfilViewId implements Serializable {
    
    @Column(name = "id_menu")
    private Long idMenu;
    
    @Column(name = "id_perfil")
    private Long idPerfil;
}
