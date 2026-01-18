package security.aplication.usecase;

import security.aplication.port.output.PantallaRepository;
import security.dominio.entidades.Pantalla;
import security.dominio.exceptions.SecurityNotFoundException;
import security.dominio.exceptions.SecurityValidationException;

import java.time.LocalDateTime;

/**
 * Caso de Uso: ActualizarPantallaUseCase
 * 
 * Responsabilidad: Actualizar datos de una pantalla existente.
 * 
 * Flujo:
 * 1. Recibir ID y datos nuevos de pantalla (desde Controller)
 * 2. Validar que pantalla existe
 * 3. Validar nuevos datos (URL no vacía, etc)
 * 4. Preservar auditoría de creación (userC, fechaC)
 * 5. Registrar auditoría de modificación (userMod, fechaMod)
 * 6. Actualizar en BD
 * 7. Retornar pantalla actualizada
 * 
 * Validaciones:
 * - ID debe corresponder a pantalla existente
 * - URL no puede estar vacía
 * - Módulo debe ser válido
 * 
 * Auditoría:
 * - Preserva userC: No se modifica (auditoría original)
 * - Preserva fechaC: No se modifica (auditoría original)
 * - Registra userMod: Usuario que realiza cambios
 * - Registra fechaMod: LocalDateTime.now()
 * 
 * @param id ID de pantalla a actualizar
 * @param pantallaNueva Pantalla con datos actualizados
 * @return Pantalla actualizada
 * @throws SecurityNotFoundException si pantalla no existe
 * @throws SecurityValidationException si datos inválidos
 */
public class ActualizarPantallaUseCase {
    
    private final PantallaRepository pantallaRepository;

    /**
     * Constructor con inyección de repositorio.
     * 
     * @param pantallaRepository Repositorio de pantallas
     */
    public ActualizarPantallaUseCase(PantallaRepository pantallaRepository) {
        this.pantallaRepository = pantallaRepository;
    }

    /**
     * Ejecuta actualización de pantalla.
     * 
     * @param id ID de pantalla a actualizar
     * @param pantallaNueva Pantalla con datos actualizados (sin auditoría)
     * @return Pantalla actualizada con nueva auditoría de modificación
     * @throws SecurityNotFoundException si pantalla no existe
     * @throws SecurityValidationException si datos inválidos
     */
    public Pantalla ejecutar(Long id, Pantalla pantallaNueva) {
        // 1. Validar que pantalla existe
        Pantalla pantallaExistente = pantallaRepository.findById(id)
                .orElseThrow(() -> new SecurityNotFoundException("Pantalla no encontrada con ID: " + id));
        
        // 2. Validar nuevos datos mediante método de dominio
        pantallaNueva.validar();
        
        // 3. Actualizar datos (pero preservar auditoría de creación)
        pantallaExistente.setNombre(pantallaNueva.getNombre());
        pantallaExistente.setUrl(pantallaNueva.getUrl());
        pantallaExistente.setModulo(pantallaNueva.getModulo());
        
        // 4. Registrar auditoría de modificación
        // NOTA: Usuario debe venir del contexto de seguridad
        pantallaExistente.setUsermod("SYSTEM"); // TODO: Obtener del SecurityContext
        pantallaExistente.setFechamod(LocalDateTime.now());
        
        // 5. Persistir cambios
        return pantallaRepository.update(id, pantallaExistente);
    }
}
