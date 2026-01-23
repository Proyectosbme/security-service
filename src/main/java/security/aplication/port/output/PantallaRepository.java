package security.aplication.port.output;

import security.dominio.entidades.Modulo;
import security.dominio.entidades.Pantalla;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de Salida: PantallaRepository
 * 
 * Define contrato para operaciones de persistencia de pantallas.
 * Abstrae la tecnología de persistencia (JPA, MongoDB, etc).
 * 
 * Patrón: Repository Pattern / Output Port (Hexagonal Architecture)
 * 
 * Responsabilidad: Definir métodos de acceso a datos para pantallas.
 * 
 * Implementación: PantallaRepositoryAdapter en framework/output
 * 
 * Métodos:
 * - save(Pantalla): Crear nueva pantalla
 * - findById(Long): Buscar por ID
 * - deleteById(Long): Eliminar por ID
 * - update(Long, Pantalla): Actualizar pantalla existente
 */
public interface PantallaRepository {
    
    /**
     * Persiste una nueva pantalla en BD.
     * 
     * @param pantalla Pantalla a crear
     * @return Pantalla persistida con ID asignado
     */
    Pantalla save(Pantalla pantalla);


    List<Pantalla> findAll();
    /**
     * Obtiene una pantalla por su ID.
     * 
     * @param id ID de la pantalla
     * @return Optional con Pantalla si existe, Optional.empty() si no
     */
    Optional<Pantalla> findById(Long id);

    /**
     * Elimina una pantalla por su ID.
     * 
     * @param id ID de la pantalla a eliminar
     * @return true si se eliminó, false si no existía
     */
    boolean deleteById(Long id);

    /**
     * Actualiza una pantalla existente.
     * 
     * @param id ID de la pantalla a actualizar
     * @param pantalla Pantalla con nuevos datos
     * @return Pantalla actualizada
     */
    Pantalla update(Long id, Pantalla pantalla);
}
