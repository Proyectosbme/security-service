package security.aplication.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import security.aplication.port.input.AsignarMenuAPerfilUseCase;
import security.aplication.port.input.BuscarMenusPorPerfilUseCase;
import security.aplication.port.input.RemoverMenuDePerfilUseCase;
import security.aplication.port.output.MenuPerfilRepository;
import security.dominio.entidades.MenuPerfil;

import java.math.BigInteger;
import java.util.List;

/**
 * Servicio: MenuPerfilService
 * 
 * Implementa los casos de uso de MenuPerfil.
 */
@ApplicationScoped
public class MenuPerfilService implements 
    AsignarMenuAPerfilUseCase,
    BuscarMenusPorPerfilUseCase,
    RemoverMenuDePerfilUseCase {
    
    @Inject
    MenuPerfilRepository menuPerfilRepository;
    
    @Override
    public MenuPerfil asignar(BigInteger menuId, BigInteger perfilId) {
        MenuPerfil menuPerfil = new MenuPerfil(menuId, perfilId);
        return menuPerfilRepository.save(menuPerfil);
    }
    
    @Override
    public List<MenuPerfil> buscar(BigInteger perfilId) {
        return menuPerfilRepository.findByPerfilId(perfilId);
    }
    
    @Override
    public void remover(BigInteger menuId, BigInteger perfilId) {
        menuPerfilRepository.delete(menuId, perfilId);
    }
}
