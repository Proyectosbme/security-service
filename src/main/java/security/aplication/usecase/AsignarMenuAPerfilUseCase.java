package security.aplication.usecase;

import security.aplication.port.output.MenuPerfilRepository;
import security.dominio.entidades.MenuPerfil;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Caso de Uso: AsignarMenuAPerfilUseCase 
 * Responsabilidad: Crear la relación entre un menú y un perfil.
 * 
 * Reglas de negocio:
 * - Crea una nueva asociación menu-perfil
 * - Ambos IDs deben ser válidos (no se valida existencia aquí, se delega a BD con FK)
 * 
 * Patrón: Use Case (Clean Architecture)
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
