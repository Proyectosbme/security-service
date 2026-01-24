package security.aplication.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import security.aplication.dto.MenuJerarquico;
import security.aplication.port.input.MenuPerfilInputPort;
import security.aplication.port.output.MenuPerfilRepository;
import security.aplication.port.output.MenuPerfilViewRepository;
import security.aplication.usecase.AsignarMenuAPerfilUseCase;
import security.aplication.usecase.BuscarMenuPefilPorPefil;
import security.aplication.usecase.EliminarMenuPerfilUseCase;
import security.aplication.usecase.ObtenerMenusJerarquicosPorPerfilUseCase;
import security.dominio.entidades.MenuPerfil;

import java.math.BigInteger;
import java.util.*;

/**
 * Servicio de Aplicación: MenuPerfilService
 * 
 * Orquesta operaciones de menú-perfil como implementación de MenuPerfilInputPort.
 * 
 * Responsabilidad:
 * 1. Instanciar casos de uso de menú-perfil
 * 2. Delegar asignación, consulta y eliminación de relaciones
 * 3. Construir menús jerárquicos por perfil
 * 
 * Patrón: Application Service / Facade
 * 
 * Flujo:
 * Controller -> MenuPerfilInputPort -> MenuPerfilService -> UseCases -> Repositorios
 * 
 * Excepciones:
 * - Se propagan las excepciones de los casos de uso
 * 
 * @author bme(Bryan Ivan Marroquin)
 * @version 1.0
 */
@ApplicationScoped
public class MenuPerfilService implements MenuPerfilInputPort {

    private final AsignarMenuAPerfilUseCase asignarMenuAPerfilUseCase;
    private final ObtenerMenusJerarquicosPorPerfilUseCase obtenerMenusJerarquicosUseCase;
    private final BuscarMenuPefilPorPefil buscarMenuPefilPorPefil;
    private final EliminarMenuPerfilUseCase eliminarMenuPerfilUseCase;
    
    @Inject
    public MenuPerfilService(MenuPerfilRepository menuPerfilRepository, 
                             MenuPerfilViewRepository menuPerfilViewRepository) {
        this.asignarMenuAPerfilUseCase = new AsignarMenuAPerfilUseCase(menuPerfilRepository);
        this.obtenerMenusJerarquicosUseCase = new ObtenerMenusJerarquicosPorPerfilUseCase(menuPerfilViewRepository);
        this.buscarMenuPefilPorPefil = new BuscarMenuPefilPorPefil(menuPerfilRepository);
        this.eliminarMenuPerfilUseCase = new EliminarMenuPerfilUseCase(menuPerfilRepository);
    }

    /**
     * Asigna un menú a un perfil.
     *
     * @param menuId identificador del menú
     * @param perfilId identificador del perfil
     * @return relación menú-perfil creada
     */
    @Override
    public MenuPerfil asignar(BigInteger menuId, BigInteger perfilId) {
        return asignarMenuAPerfilUseCase.ejecutar(menuId, perfilId);
    }

    /**
     * Obtiene las relaciones menú-perfil por perfil.
     *
     * @param perfilId identificador del perfil
     * @return lista de relaciones menú-perfil
     */
    @Override
    public List<MenuPerfil> buscarPorPerfil(BigInteger perfilId) {
        return buscarMenuPefilPorPefil.ejecutar(perfilId);
    }

    /**
     * Elimina una relación menú-perfil.
     *
     * @param menuId identificador del menú
     * @param perfilId identificador del perfil
     */
    @Override
    public void remover(BigInteger menuId, BigInteger perfilId) {
       this.eliminarMenuPerfilUseCase.ejecutar(menuId,perfilId);
    }

    /**
     * Obtiene el menú jerárquico asociado a un perfil.
     *
     * @param perfilId identificador del perfil
     * @return lista de menús jerárquicos
     */
    @Override
    public List<MenuJerarquico> obtenerMenusJerarquicos(Long perfilId) {
        return obtenerMenusJerarquicosUseCase.ejecutar(perfilId);
    }
}
