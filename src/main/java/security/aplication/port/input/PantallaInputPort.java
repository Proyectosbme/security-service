package security.aplication.port.input;

import security.dominio.entidades.Modulo;
import security.dominio.entidades.Pantalla;

import java.util.List;

/**
 * Puerto de Entrada (Input Port): PantallaInputPort
 * 
 * Define el contrato de aplicación para la gestión de pantallas.
 * 
 * Responsabilidad:
 * 1. Exponer operaciones CRUD de pantallas
 * 2. Mantener la independencia de la infraestructura
 * 
 * Patrón: Input Port (Arquitectura Hexagonal)
 * 
 * Flujo:
 * Controller → PantallaInputPort → PantallaService → UseCases → Repositorios
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public interface PantallaInputPort {
    
    /**
     * Crea una nueva pantalla.
     *
     * @param pantalla pantalla a crear
     * @return pantalla creada con ID asignado
     */
    Pantalla crear(Pantalla pantalla);

    /**
     * Obtiene una pantalla por su ID.
     *
     * @param id identificador de la pantalla
     * @return pantalla encontrada
     */
    Pantalla buscarPorId(Long id);

    /**
     * Obtiene todas las pantallas.
     *
     * @return lista de pantallas
     */
    List<Pantalla> obtenerTodas();
    /**
     * Actualiza una pantalla existente.
     *
     * @param id identificador de la pantalla
     * @param pantalla nuevos datos de pantalla
     * @return pantalla actualizada
     */
    Pantalla actualizar(Long id, Pantalla pantalla);

    /**
     * Elimina una pantalla.
     *
     * @param id identificador de la pantalla a eliminar
     */
    void eliminar(Long id);



}
