package security.aplication.port.output;

import security.dominio.entidades.Perfil;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de Salida (Output Port): PerfilRepository
 * 
 * Define el contrato de persistencia para perfiles.
 * 
 * Responsabilidad:
 * 1. Proveer operaciones CRUD de perfiles
 * 2. Abstraer la tecnología de persistencia
 * 
 * Patrón: Repository / Output Port (Arquitectura Hexagonal)
 * 
 * Flujo:
 * UseCase → PerfilRepository → Implementación de persistencia
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public interface PerfilRepository {
    
    /**
     * Guarda un nuevo perfil en BD.
     * 
     * @param perfil Perfil a guardar
     * @return Perfil guardado con ID asignado
     */
    Perfil save(Perfil perfil);
    
    /**
     * Busca perfil por ID.
     * 
     * @param id ID del perfil
     * @return Optional con perfil si existe, empty si no
     */
    Optional<Perfil> findById(BigInteger id);
    
    /**
     * Actualiza perfil existente.
     * 
     * @param id ID del perfil
     * @param perfil Perfil con datos actualizados
     * @return Perfil actualizado
     */
    Perfil update(BigInteger id, Perfil perfil);
    
    /**
     * Elimina perfil por ID.
     * 
     * @param id ID del perfil a eliminar
     * @return true si se eliminó, false si no existía
     */
    boolean deleteById(BigInteger id);


    /**
     * Obtiene todos los perfiles.
     *
     * @return lista de perfiles
     */
    List<Perfil> findAll();
}
