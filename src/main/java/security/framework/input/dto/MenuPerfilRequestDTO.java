package security.framework.input.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de Entrada: MenuPerfilRequestDTO
 * 
 * Contrato para asignar menús a perfiles desde clientes HTTP.
 * Se recibe en Controllers REST y se valida automáticamente con Jakarta Validation.
 * 
 * Responsabilidad: Encapsular datos para crear relación menú-perfil desde API HTTP.
 * 
 * Flujo:
 * HTTP POST /api/menu-perfil → MenuPerfilRequestDTO (con validación) → UseCase
 * 
 * Validaciones (Jakarta Validation):
 * - menuId: No nulo y > 0 (@NotNull, @Positive)
 * - perfilId: No nulo y > 0 (@NotNull, @Positive)
 * 
 * Ejemplo JSON:
 * {
 *   "menuId": 42,
 *   "perfilId": 5
 * }
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuPerfilRequestDTO {
    
    /**
     * ID del menú a asignar.
     * Debe existir en la tabla menus.
     */
    @NotNull(message = "El ID del menú es obligatorio")
    @Positive(message = "El ID del menú debe ser positivo")
    private Long menuId;
    
    /**
     * ID del perfil al que se asigna el menú.
     * Debe existir en la tabla perfiles.
     */
    @NotNull(message = "El ID del perfil es obligatorio")
    @Positive(message = "El ID del perfil debe ser positivo")
    private Long perfilId;
}
