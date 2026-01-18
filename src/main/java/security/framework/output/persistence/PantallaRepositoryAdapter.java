package security.framework.output.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import security.aplication.port.output.PantallaRepository;
import security.dominio.entidades.Pantalla;
import security.framework.output.mapper.PantallaOutputMapper;

import java.util.Optional;

/**
 * Adaptador: PantallaRepositoryAdapter
 * 
 * Responsabilidad: Adaptar la interfaz del puerto de salida (PantallaRepository) a la implementación
 * concreta usando JPA y Panache.
 * 
 * Patrón Hexagonal:
 * - Implementa PantallaRepository (puerto de salida)
 * - Depende de PantallaJpaRepository (Panache - implementación JPA)
 * - Depende de PantallaOutputMapper (MapStruct para conversiones)
 * - Convierte entre Pantalla (dominio) y PantallaJpaEntity (persistencia)
 * 
 * Flujo de Datos:
 * Entrada (Dominio) → [Mapper] → JpaEntity (Panache) → BD
 * BD → PantallaJpaEntity → [Mapper] → Salida (Dominio)
 * 
 * Mapeos:
 * - save(): Pantalla → PantallaJpaEntity → Guardar → PantallaJpaEntity → Pantalla
 * - findById(): ID → PantallaJpaEntity → Optional<Pantalla>
 * - deleteById(): ID → Eliminar de BD → boolean
 * - update(id, pantalla): Pantalla → Aplicar cambios a PantallaJpaEntity → Guardar → Pantalla
 * 
 * Auditoría:
 * - Preservación: PantallaOutputMapper.applyToEntity() preserva userC y fechaC en UPDATE
 * - userMod/fechaMod: Se asignan en CrearPantallaUseCase y ActualizarPantallaUseCase antes de llamar al adaptador
 * 
 * Scopes:
 * - @ApplicationScoped: Una sola instancia por aplicación
 * 
 * @author Security Team
 * @version 1.0
 */
@ApplicationScoped
public class PantallaRepositoryAdapter implements PantallaRepository {

    private final PantallaJpaRepository pantallaJpaRepository;
    private final PantallaOutputMapper pantallaOutputMapper;

    /**
     * Constructor con inyección de dependencias.
     * 
     * @param pantallaJpaRepository Repositorio JPA/Panache de pantallas
     * @param pantallaOutputMapper Mapper para conversiones Pantalla ↔ PantallaJpaEntity
     */
    @Inject
    public PantallaRepositoryAdapter(PantallaJpaRepository pantallaJpaRepository, 
                                     PantallaOutputMapper pantallaOutputMapper) {
        this.pantallaJpaRepository = pantallaJpaRepository;
        this.pantallaOutputMapper = pantallaOutputMapper;
    }

    /**
     * Guarda nueva pantalla en BD.
     * 
     * Flujo:
     * 1. Convertir Pantalla (dominio) a PantallaJpaEntity (JPA)
     * 2. Persistir en BD (Panache genera ID)
     * 3. Convertir PantallaJpaEntity (BD) a Pantalla (dominio)
     * 4. Retornar pantalla con ID asignado
     * 
     * @param pantalla Pantalla a guardar (sin ID)
     * @return Pantalla guardada con ID asignado por BD
     */
    @Override
    public Pantalla save(Pantalla pantalla) {
        // 1. Convertir dominio a JPA
        PantallaJpaEntity jpaEntity = pantallaOutputMapper.toJpaEntity(pantalla);
        
        // 2. Persistir (Panache asigna ID automáticamente)
        pantallaJpaRepository.persist(jpaEntity);
        
        // 3. Convertir JPA a dominio (incluye ID asignado)
        return pantallaOutputMapper.toDomain(jpaEntity);
    }

    /**
     * Busca pantalla por ID.
     * 
     * Flujo:
     * 1. Consultar BD por ID
     * 2. Si existe, convertir PantallaJpaEntity a Pantalla (dominio)
     * 3. Retornar Optional con Pantalla o empty si no existe
     * 
     * @param id ID de pantalla a buscar
     * @return Optional con Pantalla encontrada, o empty si no existe
     */
    @Override
    public Optional<Pantalla> findById(Long id) {
        // 1. Buscar en BD
        PantallaJpaEntity jpaEntity = pantallaJpaRepository.findById(id);
        
        // 2. Convertir a dominio usando Optional
        return jpaEntity != null 
            ? Optional.of(pantallaOutputMapper.toDomain(jpaEntity))
            : Optional.empty();
    }

    /**
     * Elimina pantalla de BD por ID.
     * 
     * Flujo:
     * 1. Ejecutar DELETE en BD por ID
     * 2. Retorna true si se eliminó, false si no existía
     * 
     * @param id ID de pantalla a eliminar
     * @return true si se eliminó, false si no existía
     */
    @Override
    public boolean deleteById(Long id) {
        PantallaJpaEntity jpaEntity = pantallaJpaRepository.findById(id);
        if (jpaEntity != null) {
            pantallaJpaRepository.delete(jpaEntity);
            return true;
        }
        return false;
    }

    /**
     * Actualiza pantalla existente.
     * 
     * Flujo:
     * 1. Buscar pantalla en BD por ID
     * 2. Aplicar cambios del dominio (preservando auditoría de creación)
     * 3. Guardar cambios (merge)
     * 4. Convertir a dominio y retornar
     * 
     * Auditoría:
     * - Preserva userC y fechaC (ignorados por mapper)
     * - Mantiene userMod y fechaMod (asignados antes en usecase)
     * 
     * @param id ID de la pantalla a actualizar
     * @param pantalla Pantalla con cambios a aplicar (incluye userMod y fechaMod)
     * @return Pantalla actualizada
     */
    @Override
    public Pantalla update(Long id, Pantalla pantalla) {
        // 1. Buscar pantalla en BD
        PantallaJpaEntity jpaEntity = pantallaJpaRepository.findById(id);
        
        // 2. Aplicar cambios (preservar auditoría de creación)
        if (jpaEntity != null) {
            pantallaOutputMapper.applyToEntity(pantalla, jpaEntity);
            
            // 3. Guardar (merge en transacción)
            pantallaJpaRepository.persist(jpaEntity);
            
            // 4. Convertir a dominio y retornar
            return pantallaOutputMapper.toDomain(jpaEntity);
        }
        
        return null;
    }
}
