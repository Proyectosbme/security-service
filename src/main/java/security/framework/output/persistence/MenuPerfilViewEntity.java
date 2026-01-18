package security.framework.output.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

/**
 * Entidad JPA: MenuPerfilViewEntity
 * 
 * Mapea la vista VW_MENU_PERFIL de PostgreSQL.
 * Vista de solo lectura que combina menús, perfiles y pantallas.
 */
@Entity
@Table(name = "vw_menu_perfil", schema = "kafka")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuPerfilViewEntity {
    
    @EmbeddedId
    private MenuPerfilViewId id;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "jerarq")
    private Integer jerarq;
    
    @Column(name = "menu_padre")
    private Long menuPadre;
    
    @Column(name = "orden")
    private Integer orden;
    
    @Column(name = "url")
    private String url;
}
