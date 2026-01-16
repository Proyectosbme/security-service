package security.framework.input.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

/**
 * DTO de Entrada: MenuRequestDTO
 * 
 * Contrato para creación y actualización de menús desde clientes HTTP.
 * Se recibe en Controllers REST y se valida automáticamente con Jakarta Validation.
 * 
 * Responsabilidad: Encapsular datos de menú desde API HTTP.
 * 
 * Flujo:
 * HTTP POST/PUT /api/menus → MenuRequestDTO (con validación) → MenuInputMapper → Menu (dominio) → UseCase
 * 
 * Validaciones (Jakarta Validation):
 * - jerarquia: No nula (@NotNull)
 * - nombre: No vacío (@NotBlank)
 * - orden: No nulo y > 0 (@NotNull, @Min)
 * - codPantalla: Opcional (puede ser null)
 * - codModulo: No nulo y > 0 (@NotNull, @Positive)
 * - codMenuPadre: Opcional para menús raíz
 * - icono: No vacío (@NotBlank)
 * - estado: No nulo (@NotNull) - código del estado (1=ACTIVO, 0=INACTIVO)
 * 
 * Ejemplo JSON:
 * {
 *   "nombre": "Gestión de Usuarios",
 *   "jerarquia": 1,
 *   "orden": 10,
 *   "codPantalla": 5,
 *   "codModulo": 2,
 *   "codMenuPadre": null,
 *   "icono": "users-icon",
 *   "estado": 1
 * }
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestDTO {

    /**
     * Nivel de jerarquía del menú.
     * Determina su posición en el árbol de menús.
     * 
     * Validación: @NotNull (requerido)
     */
    @NotNull(message = "La jerarquía no puede ser nula")
    private BigInteger jerarquia;

    /**
     * Nombre o etiqueta del menú visible en interfaz.
     * Ejemplo: "Gestión de Usuarios", "Reportes", "Configuración"
     * 
     * Validación: @NotBlank (no vacío ni espacios)
     */
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    /**
     * Número de orden para visualización en UI.
     * Determina el ranking/posición en lista de menús.
     * Valores más bajos aparecen primero.
     * 
     * Validación: @NotNull, @Min(0)
     */
    @NotNull(message = "El orden no puede ser nulo")
    @Min(value = 0, message = "El codigo de orden debe ser mayor a 0")
    private BigInteger orden;

    /**
     * ID de la pantalla/página asociada al menú.
     * Opcional: null para menús sin pantalla directa (solo agrupadores).
     * 
     * Validación: Opcional
     */
    private BigInteger codPantalla;

    /**
     * ID del módulo funcional que contiene este menú.
     * Ejemplo: 1=Administración, 2=Reportes, 3=Seguridad
     * 
     * Validación: @NotNull, @Positive (debe existir módulo con este ID)
     */
    @NotNull(message = "El código de módulo no puede ser nulo")
    @Positive(message = "El código de módulo debe ser mayor a cero")
    private BigInteger codModulo;

    /**
     * ID del menú padre (para menús anidados).
     * null para menús de nivel raíz.
     * 
     * Validación: Opcional (puede ser null)
     */
    private BigInteger codMenuPadre;

    /**
     * Código o clase de ícono para representación visual.
     * Ejemplo: "users-icon", "chart-bar", "settings-gear"
     * 
     * Validación: @NotBlank (no vacío)
     */
    @NotBlank(message = "El ícono no puede estar vacío")
    private String icono;

    /**
     * Estado del menú: 1=ACTIVO, 0=INACTIVO
     * Determina si el menú es visible y accesible en interfaz.
     * 
     * Validación: @NotNull (requerido)
     */
    @NotNull(message = "El estado no puede ser nulo")
    private BigInteger estado;
}
