package security.aplication.usecase;

import security.aplication.port.output.PantallaRepository;
import security.dominio.entidades.Pantalla;
import security.dominio.exceptions.SecurityValidationException;

import java.time.LocalDateTime;

/**
 * Caso de Uso: CrearPantallaUseCase
 * 
 * Responsabilidad: Crear una nueva pantalla con validación de negocio.
 * 
 * Flujo:
 * 1. Recibir datos de pantalla (desde Controller)
 * 2. Validar datos (URL no vacía, módulo válido, etc)
 * 3. Registrar auditoría (usuario y fecha de creación)
 * 4. Persistir en BD mediante PantallaRepository
 * 5. Retornar pantalla creada con ID asignado
 * 
 * Validaciones:
 * - Pantalla no nula
 * - URL no vacía
 * - Módulo debe estar referenciado
 * 
 * Auditoría:
 * - Registra userC: Usuario que crea (debe venir del contexto de seguridad)
 * - Registra fechaC: LocalDateTime.now()
 * 
 * @param pantalla Pantalla con datos de entrada (sin ID, sin auditoría)
 * @return Pantalla creada con ID asignado por BD
 * @throws SecurityValidationException si datos inválidos
 */
public class CrearPantallaUseCase {
    
    private final PantallaRepository pantallaRepository;

    /**
     * Constructor con inyección de repositorio.
     * 
     * @param pantallaRepository Repositorio de pantallas
     */
    public CrearPantallaUseCase(PantallaRepository pantallaRepository) {
        this.pantallaRepository = pantallaRepository;
    }

    /**
     * Ejecuta creación de pantalla.
     * 
     * @param pantalla Pantalla a crear (sin ID ni auditoría)
     * @return Pantalla creada con ID y auditoría
     * @throws SecurityValidationException si URL vacía o datos inválidos
     */
    public Pantalla ejecutar(Pantalla pantalla) {
        // 1. Validar datos mediante método de dominio
        pantalla.validar();
        
        // 2. Registrar auditoría de creación
        // NOTA: Usuario debe venir del contexto de seguridad de la aplicación
        pantalla.setUserc("SYSTEM"); // TODO: Obtener del SecurityContext
        pantalla.setFechaC(LocalDateTime.now());
        
        // 3. Persistir en BD
        return pantallaRepository.save(pantalla);
    }
}
