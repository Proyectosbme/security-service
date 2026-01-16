package security.framework.input.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de Entrada: ModuloRequestDTO
 * 
 * Contrato para creación y actualización de módulos desde clientes HTTP.
 * Se recibe en Controllers REST de módulos.
 * 
 * Responsabilidad: Encapsular datos de módulo desde API HTTP.
 * 
 * Flujo:
 * HTTP POST/PUT /api/modulos → ModuloRequestDTO → ModuloInputMapper → Modulo (dominio) → UseCase
 * 
 * Estructura simplificada:
 * - nombre: Identificador único del módulo (administración, reportes, seguridad, etc.)
 * 
 * Ejemplo JSON:
 * {
 *   "nombre": "Administración"
 * }
 * 
 * Notas:
 * - Módulos son agrupadores funcionales de menús
 * - Cada menú pertenece a exactamente un módulo
 * - No hay validaciones adicionales en DTO (son mínimas)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModuloRequestDTO {
    
    /**
     * Nombre único del módulo.
     * Ejemplos: "Administración", "Reportes", "Seguridad", "Configuración"
     */
    String nombre;
}
