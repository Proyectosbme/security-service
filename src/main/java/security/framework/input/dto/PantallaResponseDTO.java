package security.framework.input.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * DTO de Salida: PantallaResponseDTO
 * 
 * Respuesta HTTP con datos de pantalla al cliente.
 * Se genera desde entidad Pantalla (dominio) mediante PantallaInputMapper.toResponseDto().
 * 
 * Responsabilidad: Encapsular datos de pantalla para serializar a JSON.
 * 
 * Flujo:
 * UseCase retorna Pantalla → PantallaInputMapper.toResponseDto() → PantallaResponseDTO → JSON HTTP Response
 * 
 * Incluye información de auditoría (usuario y fechas de creación/modificación).
 * 
 * Ejemplo JSON:
 * {
 *   "id": 1,
 *   "nombre": "GestionUsuarios",
 *   "url": "/admin/usuarios",
 *   "codModulo": 2,
 *   "userC": "admin",
 *   "fechaC": "2025-01-16T10:30:00",
 *   "userMod": "supervisor",
 *   "fechaMod": "2025-01-16T15:45:30"
 * }
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PantallaResponseDTO {

    /**
     * ID único de la pantalla
     * Asignado por BD
     */
    private Long id;
    
    /**
     * Nombre o identificador de la pantalla
     */
    private String nombre;
    
    /**
     * URL o ruta de acceso a la pantalla
     */
    private String url;
    
    /**
     * ID del módulo al que pertenece la pantalla
     */
    private BigInteger codModulo;
    
    /**
     * Usuario que creó la pantalla (auditoría)
     */
    private String userC;
    
    /**
     * Fecha de creación de la pantalla (auditoría)
     */
    private LocalDateTime fechaC;
    
    /**
     * Usuario que realizó la última modificación (auditoría)
     */
    private String userMod;
    
    /**
     * Fecha de la última modificación (auditoría)
     */
    private LocalDateTime fechaMod;
}
