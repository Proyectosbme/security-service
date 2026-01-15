package security.aplication.port.input;


import security.dominio.entidades.Modulo;

import java.util.List;

public interface ModuloInputPort {

    Modulo crear(Modulo modulo);
    Modulo buscarPorId(Long id);
    void eliminar(Long id);
    List<Modulo> obtenerTodas();
    Modulo acualizar(Long id,Modulo Modulo);
}
