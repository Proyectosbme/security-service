package security.aplication.usecase;

import security.aplication.port.output.PerfilRepository;
import security.dominio.entidades.Perfil;

import java.util.List;

public class ObtenerPerfilesUseCase {

    private final PerfilRepository perfilRepository;

    public ObtenerPerfilesUseCase(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    public List<Perfil> ejecutar(){
        return perfilRepository.findAll();
    }

}
