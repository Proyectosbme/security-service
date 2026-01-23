package security.aplication.usecase;

import security.aplication.port.output.ModuloRepository;
import security.aplication.port.output.PantallaRepository;
import security.dominio.entidades.Modulo;
import security.dominio.entidades.Pantalla;
import security.dominio.exceptions.SecurityNotFoundException;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class ObtenerPantallasUseCase {

    private final PantallaRepository pantallaRepository;
    private final ModuloRepository moduloRepository;

    public ObtenerPantallasUseCase(PantallaRepository pantallaRepository, ModuloRepository moduloRepository) {
        this.pantallaRepository = pantallaRepository;
        this.moduloRepository = moduloRepository;
    }

    public List<Pantalla> ejecutar() {
        return this.pantallaRepository.findAll()
                .stream()
                .map((p) -> {
                    if (p.getModulo() != null && p.getModulo().getId() != null) {
                        BigInteger idmod = p.getModulo().getId();
                        Modulo modulo = moduloRepository.findById(idmod.longValue()).orElseThrow(() -> new SecurityNotFoundException(idmod.toString()));
                        p.setModulo(modulo);
                    }
                    return p;
                }).collect(Collectors.toList());

    }
}
