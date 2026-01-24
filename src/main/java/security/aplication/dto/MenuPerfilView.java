package security.aplication.dto;

/**
 * DTO de Aplicación: MenuPerfilView
 * 
 * Representa los datos de la vista vw_menu_perfil sin depender de JPA.
 * 
 * Responsabilidad:
 * 1. Transportar datos de menús por perfil
 * 2. Mantener independencia del framework
 * 
 * Patrón: DTO
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class MenuPerfilView {
    
    private Long idMenu;
    private Long idPerfil;
    private String nombre;
    private Integer jerarq;
    private Long menuPadre;
    private Integer orden;
    private String url;
    
    public MenuPerfilView() {
    }
    
    public MenuPerfilView(Long idMenu, Long idPerfil, String nombre, Integer jerarq, 
                          Long menuPadre, Integer orden, String url) {
        this.idMenu = idMenu;
        this.idPerfil = idPerfil;
        this.nombre = nombre;
        this.jerarq = jerarq;
        this.menuPadre = menuPadre;
        this.orden = orden;
        this.url = url;
    }

    public Long getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Long idMenu) {
        this.idMenu = idMenu;
    }

    public Long getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getJerarq() {
        return jerarq;
    }

    public void setJerarq(Integer jerarq) {
        this.jerarq = jerarq;
    }

    public Long getMenuPadre() {
        return menuPadre;
    }

    public void setMenuPadre(Long menuPadre) {
        this.menuPadre = menuPadre;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
