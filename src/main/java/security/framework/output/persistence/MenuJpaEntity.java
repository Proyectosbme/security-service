package security.framework.output.persistence;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
//prueba
@Entity
@Table(name = "menus", schema = "kafka")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private BigInteger jerarquia;
    private BigInteger orden;
    private BigInteger codPantalla;
    private BigInteger codModulo;
    private BigInteger codMenuPadre;
    private String icono;
    private BigInteger estado;
}
