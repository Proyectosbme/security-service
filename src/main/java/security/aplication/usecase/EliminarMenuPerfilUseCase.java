package security.aplication.usecase;

import security.aplication.port.output.MenuPerfilRepository;
import security.dominio.entidades.MenuPerfil;
import security.dominio.exceptions.SecurityNotFoundException;

import java.math.BigInteger;

public class EliminarMenuPerfilUseCase {

    private final MenuPerfilRepository menuPerfilRepository;


    public EliminarMenuPerfilUseCase(MenuPerfilRepository menuPerfilRepository) {
        this.menuPerfilRepository = menuPerfilRepository;
    }

    public void ejecutar(BigInteger menuId, BigInteger perfilId){
        if(menuId == null || perfilId == null){
            throw new SecurityNotFoundException(
                    "No existe el menú " + menuId + " y perfil " + perfilId
            );
        }
        MenuPerfil menuPerfil= new MenuPerfil(menuId, perfilId);
        boolean existe =menuPerfilRepository.buscarMenuPerfil(menuPerfil).isPresent();

        if(!existe){
            throw new SecurityNotFoundException(menuId.toString()+ perfilId.toString());
        }
        menuPerfilRepository.delete(menuId,perfilId);
    }
}
