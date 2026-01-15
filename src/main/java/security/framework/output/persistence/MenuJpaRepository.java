package security.framework.output.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MenuJpaRepository implements PanacheRepository<MenuJpaEntity> {
}
