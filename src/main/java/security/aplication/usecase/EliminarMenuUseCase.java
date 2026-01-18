package security.aplication.usecase;

import security.aplication.port.output.MenuRepository;
import security.dominio.exceptions.SecurityNotFoundException;

/**
 * Caso de Uso: EliminarMenuUseCase
 * Elimina un menú verificando su existencia.
 * Patrón: Use Case / Command Pattern
 * Flujo: Verificar existencia → Eliminar de BD
 */
public class EliminarMenuUseCase {

    private final MenuRepository menuRepository;

    /**
     * Constructor con inyección de dependencia.
     */
    public EliminarMenuUseCase(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    /**
     * Ejecuta eliminación de menú.
     * @param id Identificador a eliminar
     * @throws SecurityNotFoundException si no existe
     */
    public void ejecutar(Long id){
        boolean existe = menuRepository.findById(id).isPresent();

        if(!existe){
            throw new SecurityNotFoundException(id.toString());
        }
        menuRepository.deleteById(id);
    }
}
