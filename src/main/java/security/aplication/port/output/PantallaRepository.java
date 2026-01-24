package security.aplication.port.output;

import security.dominio.entidades.Modulo;
import security.dominio.entidades.Pantalla;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de Salida (Output Port): PantallaRepository
 * 
 * Define el contrato de persistencia para pantallas.
 * 
 * Responsabilidad:
 * 1. Proveer operaciones CRUD de pantallas
 * 2. Abstraer la tecnología de persistencia
 * 
 * Patrón: Repository / Output Port (Arquitectura Hexagonal)
 * 
 * Flujo:
 * UseCase → PantallaRepository → Implementación de persistencia
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public interface PantallaRepository {
    /**
     * Persiste una nueva pantalla en BD.
     * 
     * @param pantalla pantalla a crear
     * @return pantalla persistida con ID asignado
     */
    Pantalla save(Pantalla pantalla);

    /**
     * Obtiene todas las pantallas.
     *
     * @return lista de pantallas
     */
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
