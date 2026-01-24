package security.aplication.usecase;

import security.aplication.dto.MenuJerarquico;
import security.aplication.dto.MenuPerfilView;
import security.aplication.port.output.MenuPerfilViewRepository;

import java.util.*;

/**
 * Caso de Uso: ObtenerMenusJerarquicosPorPerfilUseCase
 * 
 * Orquesta la construcción de la estructura jerárquica de menús para un perfil.
 * 
 * Responsabilidad:
 * 1. Obtener registros desde la vista de perfil
 * 2. Crear nodos jerárquicos
 * 3. Vincular relaciones padre-hijo
 * 4. Ordenar recursivamente por el campo de orden
 * 
 * Patrón: Use Case / Query Pattern
 * 
 * Flujo:
 * Consultar → Construir jerarquía → Ordenar → Retornar
 * 
 * Excepciones:
 * - Ninguna
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
public class ObtenerMenusJerarquicosPorPerfilUseCase {
    
    private final MenuPerfilViewRepository menuPerfilViewRepository;
    
    public ObtenerMenusJerarquicosPorPerfilUseCase(MenuPerfilViewRepository menuPerfilViewRepository) {
        this.menuPerfilViewRepository = menuPerfilViewRepository;
    }
    
    /**
     * Ejecuta el caso de uso.
     * 
     * @param perfilId ID del perfil
     * @return Lista de menús jerárquicos ordenados
     */
    public List<MenuJerarquico> ejecutar(Long perfilId) {
        List<MenuPerfilView> registros = menuPerfilViewRepository.findByPerfilId(perfilId);
        
        Map<Long, MenuJerarquico> map = new HashMap<>();
        List<MenuJerarquico> raiz = new ArrayList<>();
        
        // 1️⃣ Crear nodos
        crearNodos(registros, map);
        
        // 2️⃣ Construir jerarquía
        construirJerarquia(registros, map, raiz);
        
        // 3️⃣ Ordenar recursivamente
        ordenarRecursivo(raiz);
        
        return raiz;
    }
    
    /**
     * Paso 1: Crear nodos del árbol.
     * Convierte cada registro en un MenuJerarquico y lo agrega al mapa.
     */
    private void crearNodos(List<MenuPerfilView> registros, Map<Long, MenuJerarquico> map) {
        for (MenuPerfilView e : registros) {
            MenuJerarquico dto = new MenuJerarquico();
            dto.setCodigo(e.getIdMenu());
            dto.setLabel(e.getIdMenu() + "-" + e.getNombre());
            dto.setOrden(e.getOrden());
            
            if (e.getUrl() != null) {
                // Nodo hoja (con pantalla)
                dto.setRouterLink(List.of(e.getUrl()));
                dto.setIcon("pi pi-fw pi-desktop");
            } else {
                // Nodo padre (contenedor)
                dto.setIcon("pi pi-fw pi-folder");
                dto.setItems(new ArrayList<>());
            }
            
            map.put(dto.getCodigo(), dto);
        }
    }
    
    /**
     * Paso 2: Construir relaciones padre-hijo.
     * Agrega cada nodo a su padre correspondiente o a la raíz.
     */
    private void construirJerarquia(List<MenuPerfilView> registros, 
                                     Map<Long, MenuJerarquico> map, 
                                     List<MenuJerarquico> raiz) {
        for (MenuPerfilView e : registros) {
            MenuJerarquico actual = map.get(e.getIdMenu());
            
            if (e.getMenuPadre() == null || e.getJerarq() == 0) {
                // Nodo raíz (sin padre)
                raiz.add(actual);
            } else {
                // Nodo hijo (tiene padre)
                MenuJerarquico padre = map.get(e.getMenuPadre());
                if (padre != null && padre.getItems() != null) {
                    padre.getItems().add(actual);
                }
            }
        }
    }
    
    /**
     * Paso 3: Ordenar recursivamente.
     * Ordena cada nivel por el campo 'orden'.
     */
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
