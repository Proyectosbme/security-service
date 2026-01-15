package security.framework.input.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestDTO {

    private BigInteger jerarquia;
    private String nombre;
    private BigInteger orden;
    private BigInteger codPantalla;
    private BigInteger codModulo;
    private BigInteger codMenuPadre;
    private String icono;
    private BigInteger estado;
}
