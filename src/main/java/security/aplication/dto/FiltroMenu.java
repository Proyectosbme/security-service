package security.aplication.dto;

import java.math.BigInteger;

/**
 * DTO (Data Transfer Object): FiltroMenu
 * Objeto para búsqueda avanzada de menús con criterios opcionales.
 * Es un Record inmutable que transporta criterios entre capas.
 */
public record FiltroMenu(
        /** Criterio: nombre del menú (búsqueda parcial) */
        String nombre,
        /** Criterio: ID del módulo propietario */
        BigInteger codModulo,
        /** Criterio: ID del menú padre (para submenús) */
        BigInteger codMenuPadre,
        /** Criterio: estado del menú (1=ACTIVO, 0=INACTIVO) */
        BigInteger estado
) {}