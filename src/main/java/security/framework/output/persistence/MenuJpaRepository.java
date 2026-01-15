package security.framework.output.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import security.aplication.dto.FiltroMenu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class MenuJpaRepository implements PanacheRepository<MenuJpaEntity> {
    public List<MenuJpaEntity> buscarMenusPorFiltros(FiltroMenu filtro) {
        StringBuilder query = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        boolean whereAdded = false;

        if (filtro.getNombre() != null) {
            query.append(whereAdded ? " and " : "");
            query.append("lower(nombre) like :nombre");
            params.put("nombre", "%" + filtro.getNombre().toLowerCase() + "%");
            whereAdded = true;
        }

        if (filtro.getCodModulo() != null) {
            query.append(whereAdded ? " and " : "");
            query.append("codModulo = :codModulo");
            params.put("codModulo", filtro.getCodModulo());
            whereAdded = true;
        }

        if (filtro.getEstado() != null) {
            query.append(whereAdded ? " and " : "");
            query.append("estado = :estado");
            params.put("estado", filtro.getEstado());
            whereAdded = true;
        }

        // Si no hay filtros, devuelve todo
        if (query.isEmpty()) {
            return listAll();
        }
        System.out.println(query.toString());

        return find(query.toString(), params).list();
    }
}
