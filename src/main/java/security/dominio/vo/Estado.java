package security.dominio.vo;

public enum Estado {

    ACTIVO(1, "Activo"),
    INACTIVO(0, "Inactivo");

    private final Integer codigo;      // Valor en BD: "M" o "F"
    private final String descripcion; // Valor legible

    Estado(Integer codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

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
