package security.framework.output.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import security.aplication.port.output.ModuloRepository;
import security.dominio.entidades.Modulo;
import security.framework.output.mapper.ModuloOutputMapper;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ModuloRepositoryAdapter implements ModuloRepository {

    private final ModuloJpaRepository moduloJpaRepository;
    private final ModuloOutputMapper moduloOutputMapper;


    public ModuloRepositoryAdapter(ModuloJpaRepository moduloJpaRepository, ModuloOutputMapper moduloOutputMapper) {
        this.moduloJpaRepository = moduloJpaRepository;
        this.moduloOutputMapper=moduloOutputMapper;
    }

    @Override
    public Modulo save(Modulo modulo) {
       ModuloJpaEntity moduloJpaEntity = moduloOutputMapper.toJpaEntity(modulo);
        moduloJpaRepository.persist(moduloJpaEntity);
        return moduloOutputMapper.toDomain(moduloJpaEntity);
    }

    @Override
    public Optional<Modulo> findById(Long id) {
        return this.moduloJpaRepository.findByIdOptional(id)
                .map(moduloOutputMapper::toDomain);
    }

    @Override
    public List<Modulo> findAll() {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public Modulo update(Long id, Modulo modulo) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
