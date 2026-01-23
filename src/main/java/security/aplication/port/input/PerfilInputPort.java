package security.aplication.port.input;

import security.dominio.entidades.Perfil;
import java.math.BigInteger;
import java.util.List;

/**
 * Puerto de Entrada: PerfilInputPort
 * 
 * Define las operaciones disponibles para gestión de perfiles desde el exterior (Controllers).
 * 
 * Patrón Hexagonal:
 * - Puerto de entrada para la capa de aplicación
 * - Implementado por PerfilService
 * - Invocado desde PerfilController
 * 
 * Operaciones CRUD:
 * - crear(): Crear nuevo perfil
 * - buscarPorId(): Obtener perfil por ID
 * - actualizar(): Modificar perfil existente
 * - eliminar(): Borrar perfil
 * 
 * @author Security Team
 * @version 1.0
 */
public interface PerfilInputPort {
    
    /**
     * Crea un nuevo perfil.
     * 
     * @param perfil Perfil a crear (sin ID)
     * @return Perfil creado con ID asignado
     */
    Perfil crear(Perfil perfil);
    
    /**
     * Busca perfil por ID.
     * 
     * @param id ID del perfil
     * @return Perfil encontrado
     */
    Perfil buscarPorId(BigInteger id);
    
    /**
     * Actualiza perfil existente.
     * 
     * @param id ID del perfil a actualizar
     * @param perfil Perfil con datos actualizados
     * @return Perfil actualizado
     */
    Perfil actualizar(BigInteger id, Perfil perfil);
    
    /**
     * Elimina perfil por ID.
     * 
     * @param id ID del perfil a eliminar
     */
    void eliminar(BigInteger id);


    List<Perfil> obtenerTodos();
}
