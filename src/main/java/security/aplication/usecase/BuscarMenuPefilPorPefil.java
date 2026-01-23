package security.aplication.usecase;

import security.aplication.port.output.MenuPerfilRepository;
import security.dominio.entidades.MenuPerfil;

import java.math.BigInteger;
import java.util.List;

public class BuscarMenuPefilPorPefil {

    private final MenuPerfilRepository menuPerfilRepository;

    public BuscarMenuPefilPorPefil(MenuPerfilRepository menuPerfilRepository) {
        this.menuPerfilRepository = menuPerfilRepository;
    }

    public List<MenuPerfil> ejecutar(BigInteger id){
       return this.menuPerfilRepository.findByPerfilId( id);
    }
}
