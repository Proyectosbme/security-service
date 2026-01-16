package security.framework.input.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de Entrada: PerfilRequestDTO
 * 
 * Contrato para creación y actualización de perfiles desde clientes HTTP.
 * Se recibe en Controllers REST y se valida automáticamente con Jakarta Validation.
 * 
 * Responsabilidad: Encapsular datos de perfil desde API HTTP.
 * 
 * Flujo:
 * HTTP POST/PUT /api/perfil → PerfilRequestDTO (con validación) → PerfilInputMapper → Perfil (dominio) → UseCase
 * 
 * Validaciones (Jakarta Validation):
 * - nombre: No vacío (@NotBlank)
 * 
 * Ejemplo JSON:
 * {
 *   "nombre": "Administrador"
 * }
 * 
 * @author Security Team
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerfilRequestDTO {

    /**
     * Nombre descriptivo del perfil
     * Ejemplos: "Administrador", "Supervisor", "Operario", "Invitado"
     * 
     * Validación: @NotBlank (no vacío ni espacios)
     */
    @NotBlank(message = "El nombre del perfil no puede estar vacío")
    private String nombre;
}
