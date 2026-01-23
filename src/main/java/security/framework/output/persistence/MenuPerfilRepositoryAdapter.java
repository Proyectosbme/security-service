package security.framework.output.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import security.aplication.port.output.MenuPerfilRepository;
import security.dominio.entidades.MenuPerfil;
import security.framework.output.mapper.MenuPerfilJpaMapper;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Adaptador de Salida: MenuPerfilRepositoryAdapter
 * 
 * Implementa MenuPerfilRepository usando JPA/Panache.
 */
@ApplicationScoped
public class MenuPerfilRepositoryAdapter implements MenuPerfilRepository {
    
    @Inject
    MenuPerfilJpaRepository jpaRepository;
    
    @Inject
    MenuPerfilJpaMapper mapper;
    
    @Override
    @Transactional
    public MenuPerfil save(MenuPerfil menuPerfil) {
        MenuPerfilJpaEntity entity = mapper.toJpaEntity(menuPerfil);
        jpaRepository.persist(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public Optional<MenuPerfil> buscarMenuPerfil(MenuPerfil menuPerfil) {
        MenuPerfilId id = new MenuPerfilId(
                menuPerfil.getMenuId().longValue(),
                menuPerfil.getPerfilId().longValue()
        );

        return jpaRepository.findByIdOptional(id)
                .map(mapper::toDomain);

    }

    @Override
    public List<MenuPerfil> findByPerfilId(BigInteger perfilId) {
        List<MenuPerfilJpaEntity> entities = jpaRepository.findByPerfilId(perfilId.longValue());
        return mapper.toDomainList(entities);
    }
    
    @Override
    public List<MenuPerfil> findByMenuId(BigInteger menuId) {
        List<MenuPerfilJpaEntity> entities = jpaRepository.findByMenuId(menuId.longValue());
        return mapper.toDomainList(entities);
    }
    
    @Override
    @Transactional
    public void delete(BigInteger menuId, BigInteger perfilId) {
        jpaRepository.deleteByMenuIdAndPerfilId(menuId.longValue(), perfilId.longValue());
    }
    
    @Override
    @Transactional
    public void deleteByPerfilId(BigInteger perfilId) {
        jpaRepository.deleteByPerfilId(perfilId.longValue());
    }
    
    @Override
    @Transactional
    public void deleteByMenuId(BigInteger menuId) {
        jpaRepository.deleteByMenuId(menuId.longValue());
    }
}
