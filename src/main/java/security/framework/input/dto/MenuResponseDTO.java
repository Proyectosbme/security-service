package security.framework.input.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

/**
 * DTO de Salida: MenuResponseDTO
 * 
 * Respuesta HTTP con datos de menú al cliente.
 * Se genera desde entidad Menu (dominio) mediante MenuInputMapper.toResponseDto().
 * 
 * Responsabilidad: Encapsular datos de menú para serializar a JSON.
 * 
 * Flujo:
 * UseCase retorna Menu → MenuInputMapper.toResponseDto() → MenuResponseDTO → JSON HTTP Response
 * 
 * Diferencias respecto a MenuRequestDTO:
 * - Incluye ID (asignado por BD)
 * - estado es String "ACTIVO"/"INACTIVO" en lugar de código numérico
 * - Todos los campos son de lectura (no validación)
 * 
 * Ejemplo JSON:
 * {
 *   "id": 42,
 *   "nombre": "Gestión de Usuarios",
 *   "jerarquia": 1,
 *   "orden": 10,
 *   "codPantalla": 5,
 *   "codModulo": 2,
 *   "codMenuPadre": null,
 *   "icono": "users-icon",
 *   "estado": "ACTIVO"
 * }
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponseDTO {

    /**
     * ID del menú asignado por base de datos.
     * Único identificador para operaciones posteriores (update, delete).
     */
    private Long id;
    
    /**
     * Nombre o etiqueta del menú visible en interfaz.
     */
    private String nombre;
    
    /**
     * Nivel de jerarquía del menú en estructura árbol.
     */
    private BigInteger jerarquia;
    
    /**
     * Número de orden para visualización en UI.
     */
    private BigInteger orden;
    
    /**
     * ID de la pantalla/página asociada al menú.
     */
    private BigInteger codPantalla;
    
    /**
     * ID del módulo funcional que contiene este menú.
     */
    private BigInteger codModulo;
    
    /**
     * ID del menú padre (para menús anidados).
     * null para menús de nivel raíz.
     */
    private BigInteger codMenuPadre;
    
    /**
     * Código o clase de ícono para representación visual.
     */
    private String icono;
    
    /**
     * Estado del menú en formato legible: "ACTIVO" o "INACTIVO".
     * Nota: En MenuRequestDTO es código numérico (1/0), aquí es descripción.
     */
    private String estado;
}
