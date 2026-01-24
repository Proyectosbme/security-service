package security.aplication.usecase;

import security.aplication.port.output.MenuPerfilRepository;
import security.dominio.entidades.MenuPerfil;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Caso de Uso: AsignarMenuAPerfilUseCase
 * 
 * Orquesta la creación de la relación entre un menú y un perfil.
 * 
 * Responsabilidad:
 * 1. Construir la asociación menú-perfil
 * 2. Persistir relación en BD
 * 
 * Patrón: Use Case / Command Pattern
 * 
 * Flujo:
 * Construir relación → Persistir
 * 
 * Excepciones:
 * - Ninguna
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class AsignarMenuAPerfilUseCase {
    
    private final MenuPerfilRepository menuPerfilRepository;
    
    public AsignarMenuAPerfilUseCase(MenuPerfilRepository menuPerfilRepository) {
        this.menuPerfilRepository = menuPerfilRepository;
    }
    
    /**
     * Ejecuta el caso de uso de asignación.
     * 
     * @param menuId ID del menú a asignar
     * @param perfilId ID del perfil al que se asigna
     * @return MenuPerfil creado
     */
    public MenuPerfil ejecutar(BigInteger menuId, BigInteger perfilId) {
        MenuPerfil menuPerfil = new MenuPerfil(menuId, perfilId);
        return menuPerfilRepository.save(menuPerfil);
    }
}
