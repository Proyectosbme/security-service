package security.aplication.port.input;


import security.dominio.entidades.Modulo;

import java.util.List;

/**
 * Puerto de Entrada (Input Port): ModuloInputPort
 * Interface que define los casos de uso para gestión de Módulos.
 * Arquitectura Hexagonal: lado de entrada (primario)
 * Controller → ModuloInputPort → ModuloService → UseCases → Repository
 */
public interface ModuloInputPort {

    /**
     * Crea un nuevo módulo validando reglas de negocio.
     * @param modulo Módulo a crear (sin ID)
     * @return Modulo creado con ID asignado
     * @throws SecurityValidationException si es inválido
     */
    Modulo crear(Modulo modulo);

    /**
     * Busca un módulo por su ID.
     * @param id Identificador del módulo
     * @return Modulo encontrado
     * @throws SecurityNotFoundException si no existe
     */
    Modulo buscarPorId(Long id);

    /**
     * Elimina un módulo del sistema.
     * @param id Identificador del módulo
     * @throws SecurityNotFoundException si no existe
     */
    void eliminar(Long id);

    /**
     * Obtiene todos los módulos disponibles.
     * @return Lista de todos los módulos
     */
    List<Modulo> obtenerTodas();

    /**
     * Actualiza un módulo existente.
     * @param id Identificador del módulo
     * @param Modulo Nuevos datos del módulo
     * @return Modulo actualizado
     * @throws SecurityValidationException si datos son inválidos
     */
    Modulo acualizar(Long id, Modulo Modulo);
}
