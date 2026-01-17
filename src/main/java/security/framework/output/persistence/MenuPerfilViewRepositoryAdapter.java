package security.framework.output.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import security.aplication.port.output.MenuPerfilViewRepository;

import java.util.List;

/**
 * Adaptador de Salida: MenuPerfilViewRepositoryAdapter
 * 
 * Implementa MenuPerfilViewRepository usando JPA/Panache sobre la vista.
 */
@ApplicationScoped
public class MenuPerfilViewRepositoryAdapter implements MenuPerfilViewRepository {
    
    @Inject
    MenuPerfilViewJpaRepository jpaRepository;
    
    @Override
    public List<MenuPerfilViewEntity> findByPerfilId(Long perfilId) {
        return jpaRepository.findByPerfilId(perfilId);
    }
}
