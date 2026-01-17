package security.aplication.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import security.aplication.dto.MenuJerarquico;
import security.aplication.port.input.MenuPerfilInputPort;
import security.aplication.port.output.MenuPerfilRepository;
import security.aplication.port.output.MenuPerfilViewRepository;
import security.dominio.entidades.MenuPerfil;
import security.framework.output.persistence.MenuPerfilViewEntity;

import java.math.BigInteger;
import java.util.*;

/**
 * Servicio: MenuPerfilService
 * 
 * Implementa todos los casos de uso de MenuPerfil.
 */
@ApplicationScoped
public class MenuPerfilService implements MenuPerfilInputPort {
    
    @Inject
    MenuPerfilRepository menuPerfilRepository;
    
    @Inject
    MenuPerfilViewRepository menuPerfilViewRepository;
    
    @Override
    public MenuPerfil asignar(BigInteger menuId, BigInteger perfilId) {
        MenuPerfil menuPerfil = new MenuPerfil(menuId, perfilId);
        return menuPerfilRepository.save(menuPerfil);
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
        List<MenuPerfilViewEntity> registros = menuPerfilViewRepository.findByPerfilId(perfilId);
        
        Map<Long, MenuJerarquico> map = new HashMap<>();
        List<MenuJerarquico> raiz = new ArrayList<>();
        
        // 1️⃣ Crear nodos
        for (MenuPerfilViewEntity e : registros) {
            MenuJerarquico dto = new MenuJerarquico();
            dto.setCodigo(e.getId().getIdMenu());
            dto.setLabel(e.getId().getIdMenu() + "-" + e.getNombre());
            dto.setOrden(e.getOrden());
            
            if (e.getUrl() != null) {
                dto.setRouterLink(List.of(e.getUrl()));
                dto.setIcon("pi pi-fw pi-desktop");
            } else {
                dto.setIcon("pi pi-fw pi-folder");
                dto.setItems(new ArrayList<>());
            }
            
            map.put(dto.getCodigo(), dto);
        }
        
        // 2️⃣ Construir jerarquía
        for (MenuPerfilViewEntity e : registros) {
            MenuJerarquico actual = map.get(e.getId().getIdMenu());
            
            if (e.getMenuPadre() == null || e.getJerarq() == 0) {
                raiz.add(actual);
            } else {
                MenuJerarquico padre = map.get(e.getMenuPadre());
                if (padre != null && padre.getItems() != null) {
                    padre.getItems().add(actual);
                }
            }
        }
        
        // 3️⃣ Ordenar recursivamente
        ordenarRecursivo(raiz);
        return raiz;
    }
    
    private void ordenarRecursivo(List<MenuJerarquico> lista) {
        if (lista == null || lista.isEmpty()) {
            return;
        }
        
        lista.sort(Comparator.comparing(MenuJerarquico::getOrden));
        
        for (MenuJerarquico m : lista) {
            if (m.getItems() != null && !m.getItems().isEmpty()) {
                ordenarRecursivo(m.getItems());
            }
        }
    }
}
