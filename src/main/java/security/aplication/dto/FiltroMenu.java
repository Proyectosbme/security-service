package security.aplication.dto;

import java.math.BigInteger;

/**
 * DTO (Data Transfer Object): FiltroMenu
 * 
 * Representa criterios opcionales para búsqueda avanzada de menús.
 * 
 * Responsabilidad:
 * 1. Encapsular filtros de consulta
 * 2. Transportar criterios entre capas sin lógica
 * 
 * Patrón: DTO / Record Inmutable
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public record FiltroMenu(
        /** Criterio: nombre del menú (búsqueda parcial). */
        String nombre,
        /** Criterio: ID del módulo propietario. */
        BigInteger codModulo,
        /** Criterio: ID del menú padre (para submenús). */
        BigInteger codMenuPadre,
        /** Criterio: estado del menú (1=ACTIVO, 0=INACTIVO). */
        BigInteger estado
) {}