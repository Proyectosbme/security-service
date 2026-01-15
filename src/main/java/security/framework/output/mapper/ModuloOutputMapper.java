package security.framework.output.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import security.dominio.entidades.Modulo;
import security.framework.output.persistence.ModuloJpaEntity;

@Mapper(componentModel = "cdi")
public interface ModuloOutputMapper {
    ModuloJpaEntity toJpaEntity(Modulo modulo);
    Modulo toDomain(ModuloJpaEntity moduloJpaEntity);
    void applyToEntity(Modulo domain, @MappingTarget ModuloJpaEntity entity);
}
