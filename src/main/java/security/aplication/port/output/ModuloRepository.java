package security.aplication.port.output;



import security.dominio.entidades.Modulo;

import java.util.List;
import java.util.Optional;

public interface ModuloRepository {
    Modulo save(Modulo modulo);
    Optional<Modulo> findById(Long id);
    List<Modulo> findAll();
    boolean deleteById(Long id);
    Modulo update(Long id,Modulo modulo);
    void delete(Long id);
}
