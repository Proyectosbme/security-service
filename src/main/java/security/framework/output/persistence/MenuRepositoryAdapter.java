package security.framework.output.persistence;

import security.aplication.dto.FiltroMenu;
import security.aplication.port.output.MenuRepository;
import security.dominio.entidades.Menu;
import security.framework.output.mapper.MenuOutputMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MenuRepositoryAdapter implements MenuRepository {

    private final MenuJpaRepository menuJpaRepository;
    private final MenuOutputMapper menuOutputMapper;

    public MenuRepositoryAdapter(MenuJpaRepository menuJpaRepository, MenuOutputMapper menuOutputMapper) {
        this.menuJpaRepository = menuJpaRepository;
        this.menuOutputMapper = menuOutputMapper;
    }

    @Override
    public Menu save(Menu menu) {
      MenuJpaEntity menuJpaEntity = menuOutputMapper.toJpaEntity(menu);
      menuJpaRepository.persist(menuJpaEntity);
      return menuOutputMapper.toDomain(menuJpaEntity);
    }

    @Override
    public Optional<Menu> findById(Long id) {
        return this.menuJpaRepository.findByIdOptional(id)
                .map(menuOutputMapper::toDomain);
    }

    @Override
    public boolean deleteById(Long id) {
        return menuJpaRepository.findByIdOptional(id)
                .map(entity -> {
                    menuJpaRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Menu update(Long id,Menu menu) {
        MenuJpaEntity entity = menuJpaRepository.findById(id);
        if (entity == null) {
            throw new RuntimeException("Menú no encontrado");
        }
        menuOutputMapper.applyToEntity(menu, entity);
        return menuOutputMapper.toDomain(entity);
    }

    @Override
    public void delete(Long id) {
       boolean resp = menuJpaRepository.findByIdOptional(id)
               .map(entity-> {
                   menuJpaRepository.delete(entity);
                   return true;
               })
               .orElse(false);
    }

    @Override
    public List<Menu> buscarMenuPorFiltros(FiltroMenu filtroMenu) {
        return menuJpaRepository.buscarMenusPorFiltros(filtroMenu).stream().map(
                (menuOutputMapper::toDomain)
                        ).toList();
    }
}
