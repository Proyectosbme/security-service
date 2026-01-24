package security.aplication.usecase;

import security.aplication.port.output.ModuloRepository;
import security.aplication.port.output.PantallaRepository;
import security.dominio.entidades.Modulo;
import security.dominio.entidades.Pantalla;
import security.dominio.exceptions.SecurityNotFoundException;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Caso de Uso: ObtenerPantallasUseCase
 * 
 * Orquesta la consulta de pantallas y resuelve su módulo asociado cuando aplica.
 * 
 * Responsabilidad:
 * 1. Obtener pantallas del repositorio
 * 2. Resolver módulo asociado cuando existe referencia
 * 3. Retornar listado enriquecido
 * 
 * Patrón: Use Case / Query Pattern
 * 
 * Flujo:
 * Consultar → Resolver módulo → Retornar
 * 
 * Excepciones:
 * - SecurityNotFoundException: si el módulo asociado no existe
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class ObtenerPantallasUseCase {

    private final PantallaRepository pantallaRepository;
    private final ModuloRepository moduloRepository;

    /**
     * Constructor con inyección de repositorios.
     *
     * @param pantallaRepository repositorio de pantallas
     * @param moduloRepository repositorio de módulos
     */
    public ObtenerPantallasUseCase(PantallaRepository pantallaRepository, ModuloRepository moduloRepository) {
        this.pantallaRepository = pantallaRepository;
        this.moduloRepository = moduloRepository;
    }

    /**
     * Ejecuta la consulta de pantallas, incluyendo el módulo asociado cuando aplica.
     *
     * @return lista de pantallas
     * @throws SecurityNotFoundException si el módulo asociado no existe
     */
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
