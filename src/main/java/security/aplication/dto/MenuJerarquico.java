package security.aplication.dto;

import java.util.List;

/**
 * DTO de Aplicación: MenuJerarquico
 * 
 * Representa la estructura jerárquica de menús en la capa de aplicación.
 * 
 * Responsabilidad:
 * 1. Modelar nodos de menú (padre/hijos)
 * 2. Transportar datos hacia la capa de entrada
 * 
 * Patrón: DTO
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class MenuJerarquico {
    
    private Long codigo;
    private String label;
    private List<String> routerLink;
    private Integer orden;
    private String icon;
    private List<MenuJerarquico> items;
    
    public MenuJerarquico() {}
    
    public MenuJerarquico(Long codigo, String label, List<String> routerLink, Integer orden, String icon, List<MenuJerarquico> items) {
        this.codigo = codigo;
        this.label = label;
        this.routerLink = routerLink;
        this.orden = orden;
        this.icon = icon;
        this.items = items;
    }
    
    public Long getCodigo() {
        return codigo;
    }
    
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public List<String> getRouterLink() {
        return routerLink;
    }
    
    public void setRouterLink(List<String> routerLink) {
        this.routerLink = routerLink;
    }
    
    public Integer getOrden() {
        return orden;
    }
    
    public void setOrden(Integer orden) {
        this.orden = orden;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public List<MenuJerarquico> getItems() {
        return items;
    }
    
    public void setItems(List<MenuJerarquico> items) {
        this.items = items;
    }
}
