package security.aplication.usecase;

import security.aplication.port.output.ModuloRepository;
import security.dominio.entidades.Modulo;
import security.dominio.exceptions.SecurityNotFoundException;

/**
 * Caso de Uso: BuscarModuloPorIdUseCase
 * Busca un módulo existente por su identificador.
 * Patrón: Use Case / Query Pattern
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
