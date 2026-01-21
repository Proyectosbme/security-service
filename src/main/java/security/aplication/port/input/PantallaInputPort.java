package security.aplication.port.input;

import security.dominio.entidades.Modulo;
import security.dominio.entidades.Pantalla;

import java.util.List;

/**
 * Puerto de Entrada: PantallaInputPort
 * 
 * Define contrato para casos de uso relacionados con pantallas.
 * Abstrae la implementación de la lógica de negocio.
 * 
 * Patrón: Use Case Pattern / Input Port (Hexagonal Architecture)
 * 
 * Responsabilidad: Definir operaciones CRUD de pantallas en capa de aplicación.
 * 
 * Implementación: PantallaService en application/service
 * 
 * Métodos:
 * - crear(Pantalla): Crear nueva pantalla con validación
 * - buscarPorId(Long): Obtener pantalla por ID
 * - actualizar(Long, Pantalla): Actualizar pantalla existente
 * - eliminar(Long): Eliminar pantalla
 */
public interface PantallaInputPort {
    
    /**
     * Crea una nueva pantalla.
     * 
     * Validaciones:
     * - Pantalla no nula
     * - Módulo asociado debe existir
     * - URL no vacía
     * 
     * Auditoría:
     * - Registra usuario que crea
     * - Registra fecha de creación
     * 
     * @param pantalla Pantalla a crear
     * @return Pantalla creada con ID asignado
     * @throws SecurityValidationException si datos inválidos
     * @throws SecurityNotFoundException si módulo no existe
     */
    Pantalla crear(Pantalla pantalla);

    /**
     * Obtiene una pantalla por su ID.
     * 
     * @param id ID de la pantalla
     * @return Pantalla encontrada
     * @throws SecurityNotFoundException si no existe
     */
    Pantalla buscarPorId(Long id);

    List<Pantalla> obtenerTodas();
    /**
     * Actualiza una pantalla existente.
     * 
     * Auditoría:
     * - Conserva usuario y fecha de creación (userC, fechaC)
     * - Registra usuario que modifica (userMod)
     * - Registra fecha de modificación (fechaMod)
     * 
     * @param id ID de la pantalla a actualizar
     * @param pantalla Pantalla con nuevos datos
     * @return Pantalla actualizada
     * @throws SecurityNotFoundException si no existe
     */
    Pantalla actualizar(Long id, Pantalla pantalla);

    /**
     * Elimina una pantalla.
     * 
     * @param id ID de la pantalla a eliminar
     * @throws SecurityNotFoundException si no existe
     */
    void eliminar(Long id);


}
