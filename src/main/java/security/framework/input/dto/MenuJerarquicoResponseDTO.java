package security.framework.input.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO de Salida: MenuJerarquicoResponseDTO
 * 
 * Respuesta HTTP con estructura jerárquica de menús para componentes de frontend.
 * Compatible con PrimeNG MenuModel.
 * 
 * Estructura recursiva que representa árbol de menús con navegación anidada.
 * 
 * Ejemplo JSON:
 * {
 *   "codigo": 1,
 *   "label": "1-Usuarios",
 *   "routerLink": ["/admin/usuarios"],
 *   "orden": 10,
 *   "icon": "pi pi-fw pi-desktop",
 *   "items": [...]
 * }
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuJerarquicoResponseDTO {
    
    /**
     * Código único del menú
     */
    private Long codigo;
    
    /**
     * Etiqueta visible del menú
     */
    private String label;
    
    /**
     * Ruta de navegación (para menús con pantalla)
     */
    private List<String> routerLink;
    
    /**
     * Orden de visualización
     */
    private Integer orden;
    
    /**
     * Icono del menú (ej: "pi pi-fw pi-users")
     */
    private String icon;
    
    /**
     * Submenús (menús hijos)
     */
    private List<MenuJerarquicoResponseDTO> items;
}
