package security.aplication.usecase;

import security.aplication.port.output.ModuloRepository;
import security.dominio.entidades.Modulo;
import security.dominio.exceptions.SecurityNotFoundException;

/**
 * Caso de Uso: BuscarModuloPorIdUseCase
 * 
 * Orquesta la búsqueda de un módulo por su identificador.
 * 
 * Responsabilidad:
 * 1. Consultar el repositorio por ID
 * 2. Retornar el módulo si existe
 * 3. Lanzar excepción si no existe
 * 
 * Patrón: Use Case / Query Pattern
 * 
 * Flujo:
 * Buscar → Validar existencia → Retornar
 * 
 * Excepciones:
 * - SecurityNotFoundException: si el módulo no existe
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class BuscarModuloPorIdUseCase {
    private final ModuloRepository moduloRepository;

    /**
     * Constructor con inyección de dependencia.
     */
    public BuscarModuloPorIdUseCase(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    /**
     * Ejecuta búsqueda de módulo por ID.
     * @param id Identificador a buscar
     * @return Modulo encontrado
     * @throws SecurityNotFoundException si no existe
     */
    public Modulo ejecutar(Long id){
        return moduloRepository.findById(id).orElseThrow(()->new SecurityNotFoundException(id.toString()));
    }
}
