package security.framework.input.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

/**
 * DTO de Salida: ModuloResponseDTO
 * 
 * Respuesta HTTP con datos de módulo al cliente.
 * Se genera desde entidad Modulo (dominio) mediante ModuloInputMapper.toResponseDto().
 * 
 * Responsabilidad: Encapsular datos de módulo para serializar a JSON.
 * 
 * Flujo:
 * UseCase retorna Modulo → ModuloInputMapper.toResponseDto() → ModuloResponseDTO → JSON HTTP Response
 * 
 * Estructura:
 * - id: Identificador único asignado por BD
 * - nombre: Identificador funcional del módulo
 * 
 * Ejemplo JSON:
 * {
 *   "id": 1,
 *   "nombre": "Administración"
 * }
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModuloResponseDTO {
    
    /**
     * ID del módulo asignado por base de datos.
     * Único identificador para operaciones posteriores (update, delete).
     */
    BigInteger id;
    
    /**
     * Nombre único del módulo.
     * Ejemplos: "Administración", "Reportes", "Seguridad"
     */
    String nombre;
}
