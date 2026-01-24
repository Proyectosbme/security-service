package security.aplication.usecase;

import security.aplication.port.output.PantallaRepository;
import security.dominio.entidades.Pantalla;
import security.dominio.exceptions.SecurityValidationException;

import java.time.LocalDateTime;

/**
 * Caso de Uso: CrearPantallaUseCase
 * 
 * Orquesta la creación de una pantalla con validación y auditoría básica.
 * 
 * Responsabilidad:
 * 1. Validar datos de pantalla
 * 2. Registrar auditoría de creación
 * 3. Persistir pantalla en BD
 * 
 * Patrón: Use Case / Command Pattern
 * 
 * Flujo:
 * Validar → Auditar creación → Persistir
 * 
 * Excepciones:
 * - SecurityValidationException: si datos de pantalla son inválidos
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
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
