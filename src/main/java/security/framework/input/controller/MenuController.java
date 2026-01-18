package security.framework.input.controller;

import jakarta.validation.Valid;
import jakarta.ws.rs.core.MediaType;
import security.aplication.dto.FiltroMenu;
import security.aplication.port.input.MenuInputPort;
import security.dominio.entidades.Menu;
import security.framework.input.dto.MenuRequestDTO;
import security.framework.input.dto.MenuResponseDTO;
import security.framework.input.mapper.MenuInputMapper;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * Controlador REST: MenuController
 * 
 * Expone endpoints HTTP para operaciones CRUD de menús.
 * Punto de entrada de la aplicación (REST API Adapter).
 * 
 * Responsabilidad: 
 * - Recibir peticiones HTTP
 * - Validar DTOs de entrada
 * - Delegar lógica a puertos de entrada (MenuInputPort)
 * - Retornar respuestas HTTP estructuradas
 * 
 * Patrón: REST Controller / Adapter de Entrada
 * Framework: JAX-RS (Jakarta REST)
 * 
 * Flujo Completo:
 * HTTP Request → @Path /menu → MenuController → MenuInputPort (UseCase) → Respuesta HTTP
 * 
 * Anotaciones:
 * - @Path("/menu"): Prefijo base para todos los endpoints
 * - @Consumes(APPLICATION_JSON): Acepta JSON en entrada
 * - @Produces(APPLICATION_JSON): Responde con JSON
 * - @POST, @GET, @PUT: Métodos HTTP soportados
 * - @Transactional: Demarca transacciones de BD
 * - @Valid: Valida DTOs con Jakarta Validation
 * 
 * Endpoints:
 * - POST /menu: Crear nuevo menú (201 Created)
 * - POST /menu/buscar: Buscar menús con filtros (200 OK)
 * - GET /menu/idmenu/{id}: Obtener menú por ID (200 OK)
 * - PUT /menu/idmenu/{id}: Actualizar menú (200 OK)
 */
@Path("/menu")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MenuController {

    /** Puerto de entrada para casos de uso de menús */
    private final MenuInputPort menuInputPort;
    
    /** Mapper para conversiones MenuRequestDTO ↔ MenuResponseDTO ↔ Menu */
    private final MenuInputMapper menuInputMapper;

    /**
     * Constructor con inyección de dependencias.
     * CDI automáticamente inyecta el puerto y mapper.
     * 
     * @param personaService Puerto MenuInputPort (inyectado por CDI)
     * @param menuInputMapper Mapper de entrada (inyectado por CDI)
     */
    public MenuController(MenuInputPort personaService, MenuInputMapper menuInputMapper) {
        this.menuInputPort = personaService;
        this.menuInputMapper = menuInputMapper;
    }

    /**
     * Crea un nuevo menú.
     * 
     * Flujo:
     * 1. Recibe MenuRequestDTO (JSON) desde cliente HTTP
     * 2. Valida con anotaciones @Valid
     * 3. Convierte a Menu (dominio) con MenuInputMapper
     * 4. Delega a menuInputPort.crear() para persistencia
     * 5. Convierte resultado a MenuResponseDTO
     * 6. Retorna HTTP 201 Created con el menú creado
     * 
     * Transacción: @Transactional asegura commit/rollback de BD
     * 
     * @param dto MenuRequestDTO con datos de entrada (validado)
     * @return Response HTTP 201 Created con MenuResponseDTO
     * 
     * Ejemplo Request:
     * POST /menu
     * {
     *   "nombre": "Gestión de Usuarios",
     *   "jerarquia": 1,
     *   "orden": 10,
     *   "codPantalla": 5,
     *   "codModulo": 2,
     *   "icono": "users-icon",
     *   "estado": 1
     * }
     * 
     * Ejemplo Response (201):
     * {
     *   "id": 42,
     *   "nombre": "Gestión de Usuarios",
     *   "estado": "ACTIVO",
     *   ...
     * }
     */
    @POST
    @Transactional
    public Response crear(@Valid MenuRequestDTO dto) {

        //USUARIO A DOMINIO
        Menu menu = menuInputMapper.toDomain(dto);



        Menu menuGuardado = menuInputPort.crear(menu);
        MenuResponseDTO response = menuInputMapper.toResponseDto(menuGuardado);
        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    /**
     * Busca menús aplicando filtros avanzados.
     * 
     * Flujo:
     * 1. Recibe MenuRequestDTO con criterios de búsqueda
     * 2. Convierte a FiltroMenu (DTO de dominio)
     * 3. Delega a menuInputPort.buscarPorFiltros()
     * 4. Mapea cada Menu → MenuResponseDTO
     * 5. Retorna HTTP 200 OK con lista de resultados
     * 
     * Filtros soportados:
     * - nombre: búsqueda parcial insensible a mayúsculas
     * - codModulo: filtro por módulo
     * - estado: filtro por estado (ACTIVO/INACTIVO)
     * 
     * @param dto MenuRequestDTO con criterios de búsqueda
     * @return Response HTTP 200 OK con List<MenuResponseDTO>
     * 
     * Ejemplo Request:
     * POST /menu/buscar
     * {
     *   "nombre": "usuario",
     *   "codModulo": 2,
     *   "estado": 1
     * }
     * 
     * Ejemplo Response (200):
     * [
     *   { "id": 42, "nombre": "Gestión de Usuarios", "estado": "ACTIVO" },
     *   { "id": 43, "nombre": "Reportes Usuarios", "estado": "ACTIVO" }
     * ]
     */
    @POST
    @Path("/buscar")
    public Response buscarPorFiltros( MenuRequestDTO dto) {
        FiltroMenu filtro = menuInputMapper.toFiltro(dto);
        List<Menu> menus = menuInputPort.buscarPorFiltros(filtro);
        List<MenuResponseDTO> response = menus.stream()
                .map(menuInputMapper::toResponseDto)
                .toList();
        return Response.ok(response).build();
    }

    /**
     * Obtiene un menú específico por su ID.
     * 
     * Flujo:
     * 1. Extrae ID de la ruta (/menu/idmenu/{id})
     * 2. Delega a menuInputPort.buscarPorId(id)
     * 3. Si no existe, lanza SecurityNotFoundException → HTTP 404
     * 4. Mapea Menu → MenuResponseDTO
     * 5. Retorna HTTP 200 OK con el menú
     * 
     * @param id ID del menú a recuperar (de ruta)
     * @return MenuResponseDTO serializable a JSON
     * @throws SecurityNotFoundException si no existe menú con ese ID
     * 
     * Ejemplo Request:
     * GET /menu/idmenu/42
     * 
     * Ejemplo Response (200):
     * {
     *   "id": 42,
     *   "nombre": "Gestión de Usuarios",
     *   "jerarquia": 1,
     *   "estado": "ACTIVO",
     *   ...
     * }
     * 
     * Ejemplo Response (404):
     * {
     *   "status": 404,
     *   "error": "Not Found",
     *   "message": "Menú con ID 999 no existe"
     * }
     */
    @GET
    @Path("/idmenu/{idmenu}")
    public MenuResponseDTO buscarPorId(@PathParam("idmenu") Long id) {
        Menu menu = menuInputPort.buscarPorId(id);
        return menuInputMapper.toResponseDto(menu);
    }

    /**
     * Actualiza un menú existente.
     * 
     * Flujo:
     * 1. Extrae ID de la ruta (/menu/idmenu/{id})
     * 2. Recibe MenuRequestDTO con nuevos datos
     * 3. Valida con anotaciones @Valid
     * 4. Convierte a Menu (dominio)
     * 5. Delega a menuInputPort.acualizar(id, menu) [nota: hay typo en método]
     * 6. Mapea resultado a MenuResponseDTO
     * 7. Retorna HTTP 200 OK con menú actualizado
     * 
     * Transacción: @Transactional asegura commit/rollback de BD
     * 
     * @param id ID del menú a actualizar (de ruta)
     * @param dto MenuRequestDTO con nuevos datos (validado)
     * @return MenuResponseDTO con cambios aplicados
     * @throws SecurityNotFoundException si no existe menú con ese ID
     * 
     * Ejemplo Request:
     * PUT /menu/idmenu/42
     * {
     *   "nombre": "Gestión Avanzada de Usuarios",
     *   "estado": 0
     * }
     * 
     * Ejemplo Response (200):
     * {
     *   "id": 42,
     *   "nombre": "Gestión Avanzada de Usuarios",
     *   "estado": "INACTIVO",
     *   ...
     * }
     */
    @PUT
    @Path("/idmenu/{idmenu}")
    @Transactional
    public MenuResponseDTO actualizar(@PathParam("idmenu") Long id,
                                         @Valid MenuRequestDTO dto ) {
        Menu datosNuevos = menuInputMapper.toDomain(dto);
        Menu personaActualizada = menuInputPort.acualizar(id,datosNuevos);
        return menuInputMapper.toResponseDto(personaActualizada);
    }}