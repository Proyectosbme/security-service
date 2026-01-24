package security.aplication.usecase;

import security.aplication.port.output.ModuloRepository;
import security.dominio.entidades.Modulo;
import java.util.List;

/**
 * Caso de Uso: ObtenerModulosUseCase
 * 
 * Orquesta la consulta de todos los módulos disponibles.
 * 
 * Responsabilidad:
 * 1. Consultar repositorio
 * 2. Retornar listado completo
 * 
 * Patrón: Use Case / Query Pattern
 * 
 * Flujo:
 * Consultar → Retornar
 * 
 * Excepciones:
 * - Ninguna
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class ObtenerModulosUseCase {

    private final ModuloRepository moduloRepository;

    /**
     * Constructor con inyección del repositorio.
     *
     * @param moduloRepository repositorio de módulos
     */
    public ObtenerModulosUseCase(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    /**
     * Ejecuta la consulta de todos los módulos.
     *
     * @return lista de módulos
     */
    public List<Modulo> ejecutar(){
      return  moduloRepository.findAll();
    }
}
