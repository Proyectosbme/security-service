package security.aplication.port.output;

import security.dominio.entidades.Modulo;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de Salida (Output Port): ModuloRepository
 * 
 * Define el contrato de persistencia para módulos.
 * 
 * Responsabilidad:
 * 1. Proveer operaciones CRUD de módulos
 * 2. Abstraer la tecnología de persistencia
 * 
 * Patrón: Repository / Output Port (Arquitectura Hexagonal)
 * 
 * Flujo:
 * UseCase → ModuloRepository → Implementación de persistencia
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public interface ModuloRepository {
    /**
     * Persiste un nuevo módulo en la BD.
     * @param modulo Módulo a guardar (sin ID)
     * @return Modulo guardado con ID asignado
     */
    Modulo save(Modulo modulo);

    /**
     * Busca un módulo por ID.
     * @param id Identificador a buscar
     * @return Optional con el módulo si existe
     */
    Optional<Modulo> findById(Long id);

    /**
     * Obtiene todos los módulos.
     * @return Lista completa de módulos
     */
    List<Modulo> findAll();

    /**
     * Elimina un módulo por ID.
     * @param id Identificador a eliminar
     * @return true si se eliminó, false si no existía
     */
    boolean deleteById(Long id);

    /**
     * Actualiza un módulo existente.
     * @param id Identificador del módulo
     * @param modulo Nuevos datos
     * @return Modulo actualizado
     */
    Modulo update(Long id, Modulo modulo);

    /**
     * Elimina un módulo (alternativa a deleteById).
     * @param id Identificador a eliminar
     */
    void delete(Long id);
}
