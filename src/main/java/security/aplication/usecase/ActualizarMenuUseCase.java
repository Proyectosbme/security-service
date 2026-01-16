package security.aplication.usecase;

import security.aplication.port.output.MenuRepository;
import security.dominio.entidades.Menu;


/**
 * Caso de Uso: ActualizarMenuUseCase
 * Actualiza un menú existente validando los nuevos datos.
 * Patrón: Use Case / Command Pattern
 * Flujo: Validar datos → Persistir actualización
 */
public class ActualizarMenuUseCase {
    private final MenuRepository menuRepository;

    /**
     * Constructor con inyección de dependencia.
     */
    public ActualizarMenuUseCase(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }


    /**
     * Ejecuta actualización de menú.
     * @param id Identificador del menú
     * @param datos Nuevos datos validados
     * @return Menu actualizado
     * @throws SecurityValidationException si datos son inválidos
     */
    public Menu ejecutar(Long id, Menu datos){
        // 1. Validación de negocio
        datos.validar();

        // 2. Orquestación (actualización)
        return menuRepository.update(id, datos);
    }
}
