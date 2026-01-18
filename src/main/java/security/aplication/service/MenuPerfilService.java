package security.aplication.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import security.aplication.dto.MenuJerarquico;
import security.aplication.port.input.MenuPerfilInputPort;
import security.aplication.port.output.MenuPerfilRepository;
import security.aplication.port.output.MenuPerfilViewRepository;
import security.aplication.usecase.AsignarMenuAPerfilUseCase;
import security.aplication.usecase.ObtenerMenusJerarquicosPorPerfilUseCase;
import security.dominio.entidades.MenuPerfil;

import java.math.BigInteger;
import java.util.*;

/**
 * Servicio: MenuPerfilService
 * 
 * Implementa MenuPerfilInputPort y delega a casos de uso.
 * Mantiene la arquitectura limpia delegando toda lógica a UseCases.
 * 
 * Arquitectura Hexagonal:
 * - Solo usa DTOs de aplicación (MenuPerfilView)
 * - No importa nada del framework/infraestructura
 * - Delega toda lógica a casos de uso específicos
 * - Servicio actúa solo como orquestador
 */
@ApplicationScoped
public class MenuPerfilService implements MenuPerfilInputPort {
    
    private final MenuPerfilRepository menuPerfilRepository;
    private final AsignarMenuAPerfilUseCase asignarMenuAPerfilUseCase;
    private final ObtenerMenusJerarquicosPorPerfilUseCase obtenerMenusJerarquicosUseCase;
    
    @Inject
    public MenuPerfilService(MenuPerfilRepository menuPerfilRepository, 
                             MenuPerfilViewRepository menuPerfilViewRepository) {
        this.menuPerfilRepository = menuPerfilRepository;
        this.asignarMenuAPerfilUseCase = new AsignarMenuAPerfilUseCase(menuPerfilRepository);
        this.obtenerMenusJerarquicosUseCase = new ObtenerMenusJerarquicosPorPerfilUseCase(menuPerfilViewRepository);
    }
    
    @Override
    public MenuPerfil asignar(BigInteger menuId, BigInteger perfilId) {
        return asignarMenuAPerfilUseCase.ejecutar(menuId, perfilId);
    }
    
    @Override
    public List<MenuPerfil> buscarPorPerfil(BigInteger perfilId) {
        return menuPerfilRepository.findByPerfilId(perfilId);
    }
    
    @Override
    public void remover(BigInteger menuId, BigInteger perfilId) {
        menuPerfilRepository.delete(menuId, perfilId);
    }
    
    @Override
    public List<MenuJerarquico> obtenerMenusJerarquicos(Long perfilId) {
        return obtenerMenusJerarquicosUseCase.ejecutar(perfilId);
    }
}
