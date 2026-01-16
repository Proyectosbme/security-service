package security.aplication.service;

import security.aplication.port.input.PerfilInputPort;
import security.aplication.port.output.PerfilRepository;
import security.aplication.usecase.ActualizarPerfilUseCase;
import security.aplication.usecase.BuscarPerfilPorIdUseCase;
import security.aplication.usecase.CrearPerfilUseCase;
import security.aplication.usecase.EliminarPerfilUseCase;
import security.dominio.entidades.Perfil;

import java.math.BigInteger;

/**
 * Servicio de aplicación para gestión de perfiles.
 * 
 * Responsabilidad: Orquestar casos de uso de perfil.
 * 
 * Este servicio implementa el puerto de entrada y delega operaciones
 * a los casos de uso correspondientes. No contiene lógica de negocio,
 * solo coordina flujos.
 * 
 * Patrón: Service Layer + Use Case Pattern
 * 
 * @author Security Team
 * @version 1.0
 */
public class PerfilService implements PerfilInputPort {
    
    private final CrearPerfilUseCase crearPerfilUseCase;
    private final BuscarPerfilPorIdUseCase buscarPerfilPorIdUseCase;
    private final ActualizarPerfilUseCase actualizarPerfilUseCase;
    private final EliminarPerfilUseCase eliminarPerfilUseCase;

    /**
     * Constructor con inyección de casos de uso.
     * 
     * @param crearPerfilUseCase Caso de uso para crear perfil
     * @param buscarPerfilPorIdUseCase Caso de uso para buscar perfil
     * @param actualizarPerfilUseCase Caso de uso para actualizar perfil
     * @param eliminarPerfilUseCase Caso de uso para eliminar perfil
     */
    public PerfilService(
            CrearPerfilUseCase crearPerfilUseCase,
            BuscarPerfilPorIdUseCase buscarPerfilPorIdUseCase,
            ActualizarPerfilUseCase actualizarPerfilUseCase,
            EliminarPerfilUseCase eliminarPerfilUseCase) {
        this.crearPerfilUseCase = crearPerfilUseCase;
        this.buscarPerfilPorIdUseCase = buscarPerfilPorIdUseCase;
        this.actualizarPerfilUseCase = actualizarPerfilUseCase;
        this.eliminarPerfilUseCase = eliminarPerfilUseCase;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Perfil crear(Perfil perfil) {
        return crearPerfilUseCase.ejecutar(perfil);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Perfil buscarPorId(BigInteger id) {
        return buscarPerfilPorIdUseCase.ejecutar(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Perfil actualizar(BigInteger id, Perfil perfil) {
        return actualizarPerfilUseCase.ejecutar(id, perfil);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void eliminar(BigInteger id) {
        eliminarPerfilUseCase.ejecutar(id);
    }
}
