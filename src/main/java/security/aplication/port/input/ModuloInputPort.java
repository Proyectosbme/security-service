package security.aplication.port.input;


import security.dominio.entidades.Modulo;

import java.util.List;

/**
 * Puerto de Entrada (Input Port): ModuloInputPort
 * 
 * Define el contrato de aplicación para la gestión de módulos.
 * 
 * Responsabilidad:
 * 1. Exponer operaciones de módulo para la capa de entrada
 * 2. Mantener la independencia de la infraestructura
 * 
 * Patrón: Input Port (Arquitectura Hexagonal)
 * 
 * Flujo:
 * Controller → ModuloInputPort → ModuloService → UseCases → Repositorios
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public interface ModuloInputPort {

    /**
     * Crea un nuevo módulo.
     *
     * @param modulo módulo a crear (sin ID)
     * @return módulo creado con ID asignado
     */
    Modulo crear(Modulo modulo);

    /**
     * Busca un módulo por su ID.
     *
     * @param id identificador del módulo
     * @return módulo encontrado
     */
    Modulo buscarPorId(Long id);

    /**
     * Elimina un módulo del sistema.
     *
     * @param id identificador del módulo
     */
    void eliminar(Long id);

    /**
     * Obtiene todos los módulos disponibles.
     *
     * @return lista de módulos
     */
    List<Modulo> obtenerTodas();

    /**
     * Actualiza un módulo existente.
     *
     * @param id identificador del módulo
     * @param modulo nuevos datos del módulo
     * @return módulo actualizado
     */
    Modulo acualizar(Long id, Modulo modulo);
}
