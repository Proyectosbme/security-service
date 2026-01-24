package security.aplication.usecase;

import security.aplication.port.output.PantallaRepository;
import security.dominio.entidades.Pantalla;
import security.dominio.exceptions.SecurityNotFoundException;
import security.dominio.exceptions.SecurityValidationException;

import java.time.LocalDateTime;

/**
 * Caso de Uso: ActualizarPantallaUseCase
 * 
 * Orquesta la actualización de una pantalla existente con validación y auditoría.
 * 
 * Responsabilidad:
 * 1. Validar existencia de la pantalla
 * 2. Validar nuevos datos
 * 3. Preservar auditoría de creación
 * 4. Registrar auditoría de modificación
 * 5. Persistir cambios
 * 
 * Patrón: Use Case / Command Pattern
 * 
 * Flujo:
 * Verificar existencia → Validar → Auditar modificación → Actualizar
 * 
 * Excepciones:
 * - SecurityNotFoundException: si la pantalla no existe
 * - SecurityValidationException: si datos inválidos
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
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
