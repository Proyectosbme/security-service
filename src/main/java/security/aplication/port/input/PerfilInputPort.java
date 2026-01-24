package security.aplication.port.input;

import security.dominio.entidades.Perfil;
import java.math.BigInteger;
import java.util.List;

/**
 * Puerto de Entrada (Input Port): PerfilInputPort
 * 
 * Define el contrato de aplicación para la gestión de perfiles.
 * 
 * Responsabilidad:
 * 1. Exponer operaciones CRUD de perfiles
 * 2. Mantener la independencia de la infraestructura
 * 
 * Patrón: Input Port (Arquitectura Hexagonal)
 * 
 * Flujo:
 * Controller → PerfilInputPort → PerfilService → UseCases → Repositorios
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public interface PerfilInputPort {
    
    /**
     * Crea un nuevo perfil.
     *
     * @param perfil perfil a crear (sin ID)
     * @return perfil creado con ID asignado
     */
    Perfil crear(Perfil perfil);
    
    /**
     * Busca perfil por ID.
     *
     * @param id identificador del perfil
     * @return perfil encontrado
     */
    Perfil buscarPorId(BigInteger id);
    
    /**
     * Actualiza perfil existente.
     *
     * @param id identificador del perfil
     * @param perfil nuevos datos del perfil
     * @return perfil actualizado
     */
    Perfil actualizar(BigInteger id, Perfil perfil);
    
    /**
     * Elimina perfil por ID.
     *
     * @param id identificador del perfil
     */
    void eliminar(BigInteger id);

    /**
     * Obtiene todos los perfiles.
     *
     * @return lista de perfiles
     */
    List<Perfil> obtenerTodos();
}
