package security.framework.input.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

/**
 * DTO de Salida: PerfilResponseDTO
 * 
 * Respuesta HTTP con datos de perfil al cliente.
 * Se genera desde entidad Perfil (dominio) mediante PerfilInputMapper.toResponseDto().
 * 
 * Responsabilidad: Encapsular datos de perfil para serializar a JSON.
 * 
 * Flujo:
 * UseCase retorna Perfil → PerfilInputMapper.toResponseDto() → PerfilResponseDTO → JSON HTTP Response
 * 
 * Ejemplo JSON:
 * {
 *   "id": 1,
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
public class PerfilResponseDTO {

    /**
     * ID único del perfil
     * Asignado por BD
     */
    private BigInteger id;
    
    /**
     * Nombre descriptivo del perfil
     * Ejemplos: "Administrador", "Supervisor", "Operario"
     */
    private String nombre;
}
