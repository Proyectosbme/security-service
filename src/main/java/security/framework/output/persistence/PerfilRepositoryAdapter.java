package security.framework.output.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import security.aplication.port.output.PerfilRepository;
import security.dominio.entidades.Perfil;
import security.framework.output.mapper.PerfilOutputMapper;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Adaptador: PerfilRepositoryAdapter
 * 
 * Responsabilidad: Adaptar la interfaz del puerto de salida (PerfilRepository) a la implementación
 * concreta usando JPA y Panache.
 * 
 * Patrón Hexagonal:
 * - Implementa PerfilRepository (puerto de salida)
 * - Depende de PerfilJpaRepository (Panache - implementación JPA)
 * - Depende de PerfilOutputMapper (MapStruct para conversiones)
 * - Convierte entre Perfil (dominio) y PerfilJpaEntity (persistencia)
 * 
 * Flujo de Datos:
 * Entrada (Dominio) → [Mapper] → JpaEntity (Panache) → BD
 * BD → PerfilJpaEntity → [Mapper] → Salida (Dominio)
 * 
 * Mapeos:
 * - save(): Perfil → PerfilJpaEntity → Guardar → PerfilJpaEntity → Perfil
 * - findById(): ID → PerfilJpaEntity → Optional<Perfil>
 * - deleteById(): ID → Eliminar de BD → boolean
 * - update(id, perfil): Perfil → Aplicar cambios a PerfilJpaEntity → Guardar → Perfil
 * 
 * Scopes:
 * - @ApplicationScoped: Una sola instancia por aplicación
 * 
 * @author Security Team
 * @version 1.0
 */
@ApplicationScoped
public class PerfilRepositoryAdapter implements PerfilRepository {

    private final PerfilJpaRepository perfilJpaRepository;
    private final PerfilOutputMapper perfilOutputMapper;

    /**
     * Constructor con inyección de dependencias.
     * 
     * @param perfilJpaRepository Repositorio JPA/Panache de perfiles
     * @param perfilOutputMapper Mapper para conversiones Perfil ↔ PerfilJpaEntity
     */
    @Inject
    public PerfilRepositoryAdapter(PerfilJpaRepository perfilJpaRepository, 
                                   PerfilOutputMapper perfilOutputMapper) {
        this.perfilJpaRepository = perfilJpaRepository;
        this.perfilOutputMapper = perfilOutputMapper;
    }

    /**
     * Guarda nuevo perfil en BD.
     * 
     * Flujo:
     * 1. Convertir Perfil (dominio) a PerfilJpaEntity (JPA)
     * 2. Persistir en BD (Panache genera ID)
     * 3. Convertir PerfilJpaEntity (BD) a Perfil (dominio)
     * 4. Retornar perfil con ID asignado
     * 
     * @param perfil Perfil a guardar (sin ID)
     * @return Perfil guardado con ID asignado por BD
     */
    @Override
    public Perfil save(Perfil perfil) {
        // 1. Convertir dominio a JPA
        PerfilJpaEntity jpaEntity = perfilOutputMapper.toJpaEntity(perfil);
        
        // 2. Persistir (Panache asigna ID automáticamente)
        perfilJpaRepository.persist(jpaEntity);
        
        // 3. Convertir JPA a dominio (incluye ID asignado)
        return perfilOutputMapper.toDomain(jpaEntity);
    }

    /**
     * Busca perfil por ID.
     * 
     * Flujo:
     * 1. Consultar BD por ID
     * 2. Si existe, convertir PerfilJpaEntity a Perfil (dominio)
     * 3. Retornar Optional con Perfil o empty si no existe
     * 
     * @param id ID de perfil a buscar
     * @return Optional con Perfil encontrado, o empty si no existe
     */
    @Override
    public Optional<Perfil> findById(BigInteger id) {
        // 1. Buscar en BD
        PerfilJpaEntity jpaEntity = perfilJpaRepository.findById(id);
        
        // 2. Convertir a dominio usando Optional
        return jpaEntity != null 
            ? Optional.of(perfilOutputMapper.toDomain(jpaEntity))
            : Optional.empty();
    }

    /**
     * Elimina perfil de BD por ID.
     * 
     * Flujo:
     * 1. Ejecutar DELETE en BD por ID
     * 2. Retorna true si se eliminó, false si no existía
     * 
     * @param id ID de perfil a eliminar
     * @return true si se eliminó, false si no existía
     */
    @Override
    public boolean deleteById(BigInteger id) {
        PerfilJpaEntity jpaEntity = perfilJpaRepository.findById(id);
        if (jpaEntity != null) {
            perfilJpaRepository.delete(jpaEntity);
            return true;
        }
        return false;
    }

    /**
     * Actualiza perfil existente.
     * 
     * Flujo:
     * 1. Buscar perfil en BD por ID
     * 2. Aplicar cambios del dominio
     * 3. Guardar cambios (merge)
     * 4. Convertir a dominio y retornar
     * 
     * @param id ID del perfil a actualizar
     * @param perfil Perfil con cambios a aplicar
     * @return Perfil actualizado
     */
    @Override
    public Perfil update(BigInteger id, Perfil perfil) {
        // 1. Buscar perfil en BD
        PerfilJpaEntity jpaEntity = perfilJpaRepository.findById(id);
        
        // 2. Aplicar cambios
        perfilOutputMapper.applyToEntity(perfil, jpaEntity);
        
        // 3. Persistir cambios (JPA hace merge automáticamente)
        perfilJpaRepository.persist(jpaEntity);
        
        // 4. Convertir a dominio y retornar
        return perfilOutputMapper.toDomain(jpaEntity);
    }
}
