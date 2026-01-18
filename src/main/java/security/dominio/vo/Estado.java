package security.dominio.vo;

/**
 * Value Object (VO): Estado
 * 
 * Enumeración que define los posibles estados para entidades del sistema.
 * Es un Value Object que encapsula valores específicos y comportamiento relacionado.
 * 
 * Estados disponibles:
 * - ACTIVO (código: 1):    Entidad habilitada en el sistema
 * - INACTIVO (código: 0):  Entidad deshabilitada en el sistema
 * 
 * Características:
 * - Mantiene un código numérico para persistencia en BD
 * - Mantiene una descripción legible para UI
 * - Proporciona métodos para búsqueda por código o descripción
 * 
 * Uso:
 * - Menu.estado = Estado.ACTIVO
 * - Modulo.estado = Estado.INACTIVO
 * - Se usa en validaciones y filtros de búsqueda
 */
public enum Estado {

    /** Indica que la entidad está habilitada y activa en el sistema */
    ACTIVO(1, "Activo"),
    /** Indica que la entidad está deshabilitada o inactiva en el sistema */
    INACTIVO(0, "Inactivo");

    /** Código numérico del estado (para BD) */
    private final Integer codigo;
    /** Descripción legible del estado (para UI) */
    private final String descripcion;

    /**
     * Constructor del enum.
     * 
     * @param codigo      Valor numérico del estado
     * @param descripcion Texto descriptivo del estado
     */
    Estado(Integer codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el código numérico del estado.
     * @return código para persistencia en BD
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * Obtiene la descripción del estado.
     * @return descripción legible del estado
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Busca un estado por su código numérico.
     * 
     * @param codigo Código numérico a buscar
     * @return Estado correspondiente al código
     * @throws IllegalArgumentException si el código no es válido
     */
    public static Estado fromCodigo(Integer codigo) {
        if (codigo == null) {
            throw new IllegalArgumentException("Estado no puede ser nulo o vacío");
        }

        for (Estado estado : values()) {
            if (estado.codigo.compareTo(codigo)==0) {
                return estado;
            }
        }

        throw new IllegalArgumentException(
                "Estado inválido: '" + codigo + "'. Valores válidos: 1, 0"
        );
    }


    /**
     * Busca un estado por su descripción.
     * 
     * @param descripcion Descripción a buscar (insensible a mayúsculas)
     * @return Estado correspondiente a la descripción
     * @throws IllegalArgumentException si la descripción no es válida
     */
    public static Estado fromDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción del estado no puede ser nula o vacía");
        }

        for (Estado estado : values()) {
            if (estado.descripcion.equalsIgnoreCase(descripcion.trim())) {
                return estado;
            }
        }

        throw new IllegalArgumentException(
                "Estado inválido: '" + descripcion + "'. Valores válidos: Activo, Inactivo"
        );
    }
}
