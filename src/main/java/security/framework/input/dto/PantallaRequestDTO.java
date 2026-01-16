package security.framework.input.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

/**
 * DTO de Entrada: PantallaRequestDTO
 * 
 * Contrato para creación y actualización de pantallas desde clientes HTTP.
 * Se recibe en Controllers REST y se valida automáticamente con Jakarta Validation.
 * 
 * Responsabilidad: Encapsular datos de pantalla desde API HTTP.
 * 
 * Flujo:
 * HTTP POST/PUT /api/pantallas → PantallaRequestDTO (con validación) → PantallaInputMapper → Pantalla (dominio) → UseCase
 * 
 * Validaciones (Jakarta Validation):
 * - nombre: No nulo (@NotNull)
 * - url: No vacío (@NotBlank)
 * - codModulo: No nulo y > 0 (@NotNull, @Positive)
 * 
 * Ejemplo JSON:
 * {
 *   "nombre": "GestionUsuarios",
 *   "url": "/admin/usuarios",
 *   "codModulo": 2
 * }
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PantallaRequestDTO {

    /**
     * Nombre o identificador de la pantalla
     * 
     * Validación: @NotNull (requerido)
     */
    @NotNull(message = "El nombre de la pantalla no puede ser nulo")
    private String nombre;

    /**
     * URL o ruta de acceso a la pantalla
     * Ejemplo: "/admin/usuarios", "/reportes/ventas"
     * 
     * Validación: @NotBlank (no vacío ni espacios)
     */
    @NotBlank(message = "La URL de la pantalla no puede estar vacía")
    private String url;

    /**
     * ID del módulo al que pertenece la pantalla
     * 
     * Validación: @NotNull, @Positive (debe existir módulo con este ID)
     */
    @NotNull(message = "El código de módulo no puede ser nulo")
    @Positive(message = "El código de módulo debe ser mayor a cero")
    private BigInteger codModulo;
}
