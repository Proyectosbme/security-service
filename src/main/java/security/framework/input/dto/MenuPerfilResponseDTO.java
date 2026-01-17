package security.framework.input.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de Salida: MenuPerfilResponseDTO
 * 
 * Respuesta HTTP con datos de relación menú-perfil al cliente.
 * Se genera desde entidad MenuPerfil (dominio) mediante MenuPerfilInputMapper.toResponseDto().
 * 
 * Responsabilidad: Encapsular datos de menú-perfil para serializar a JSON.
 * 
 * Flujo:
 * UseCase retorna MenuPerfil → MenuPerfilInputMapper.toResponseDto() → MenuPerfilResponseDTO → JSON HTTP Response
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
public class MenuPerfilResponseDTO {
    
    /**
     * ID del menú asignado.
     */
    private Long menuId;
    
    /**
     * ID del perfil al que se asignó el menú.
     */
    private Long perfilId;
}
