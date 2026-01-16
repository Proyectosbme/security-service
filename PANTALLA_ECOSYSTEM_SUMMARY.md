================================================================================
PANTALLA ENTITY COMPLETE ECOSYSTEM - HEXAGONAL ARCHITECTURE
================================================================================
Project: bme-security-core (Quarkus + Jakarta EE + JPA + Panache)
Date: 2025-01-20
Author: Security Team

================================================================================
1. OVERVIEW
================================================================================

Se ha creado un ECOSISTEMA COMPLETO de la entidad Pantalla (Screen) siguiendo
el patrón de Arquitectura Hexagonal (Puertos y Adaptadores).

ESTRUCTURA HEXAGONAL:
┌─────────────────────────────────────────────────────────────────┐
│                        FRAMEWORK LAYER                          │
│  ┌──────────────────┐                    ┌──────────────────┐  │
│  │  INPUT ADAPTER   │                    │ OUTPUT ADAPTER   │  │
│  │ ┌──────────────┐ │                    │ ┌──────────────┐ │  │
│  │ │ Controller   │ │                    │ │ Repository   │ │  │
│  │ │ DTO/Mapper   │ │                    │ │ JPA Entity   │ │  │
│  │ └──────────────┘ │                    │ │ JPA Mapper   │ │  │
│  └──────────────────┘                    └──────────────────┘  │
│           ▲                                          ▲           │
│           │ Usa                                      │ Implementa
│           ▼                                          ▼           │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  APPLICATION LAYER (Service + Use Cases)              │   │
│  │  ┌────────────────────────────────────────────────┐   │   │
│  │  │ PantallaService (PantallaInputPort)            │   │   │
│  │  │ - Crear, Buscar, Actualizar, Eliminar         │   │   │
│  │  │ - Orquesta: CrearPantallaUseCase               │   │   │
│  │  │             BuscarPantallaPorIdUseCase         │   │   │
│  │  │             ActualizarPantallaUseCase          │   │   │
│  │  │             EliminarPantallaUseCase            │   │   │
│  │  └────────────────────────────────────────────────┘   │   │
│  │ Ports: PantallaInputPort, PantallaRepository          │   │
│  └─────────────────────────────────────────────────────────┘   │
│           ▲                                          ▲           │
│           │ Implementa Input Port                   │ Depende    │
│           │                                    Output Port      │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ DOMAIN LAYER (Pure Business Logic)                     │   │
│  │ ┌────────────────────────────────────────────────┐    │   │
│  │ │ Pantalla Entity (No dependencies)              │    │   │
│  │ │ - id, codigo, nome, url, codModulo            │    │   │
│  │ │ - userC, fechaC (creation audit - immutable)  │    │   │
│  │ │ - userMod, fechaMod (modification audit)      │    │   │
│  │ │ Modulo (dependency)                           │    │   │
│  │ │ SecurityValidationException                   │    │   │
│  │ │ SecurityNotFoundException                     │    │   │
│  │ └────────────────────────────────────────────────┘    │   │
│  └─────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘

================================================================================
2. ARCHIVOS CREADOS/MODIFICADOS
================================================================================

📂 DOMAIN LAYER (dominio/)
═══════════════════════════════════════════════════════════════════

  ✓ Pantalla.java (ACTUALIZADO)
    Ubicación: src/main/java/security/dominio/entidades/
    - Agregados campos: id, usermod, fechamod
    - Actualizado: FechaC de Date a LocalDateTime
    - Actualizado: Getters/Setters completos
    - Actualizado: Equals/HashCode por ID
    - Actualizado: toString()
    
    Campos:
      Long id                           // PK asignada por BD
      BigInteger codigo                 // Código único
      Modulo modulo                     // Relación a Modulo
      BigInteger nome                   // Nombre/ID
      String url                        // URL de acceso
      
      // Auditoría - Creación (INMUTABLE después de crear)
      String userc                      // Usuario creador
      LocalDateTime fechaC              // Fecha creación
      
      // Auditoría - Modificación (MUTABLE en updates)
      String usermod                    // Usuario modificador
      LocalDateTime fechamod            // Fecha última modificación


📂 APPLICATION LAYER (aplication/)
═══════════════════════════════════════════════════════════════════

  ✓ PantallaInputPort.java (PUERTO DE ENTRADA - YA EXISTÍA)
    Ubicación: src/main/java/security/aplication/port/input/
    Métodos: crear(), buscarPorId(), actualizar(), eliminar()
    
  ✓ PantallaRepository.java (PUERTO DE SALIDA - YA EXISTÍA)
    Ubicación: src/main/java/security/aplication/port/output/
    Métodos: save(), findById(), deleteById(), update()
    
  ✓ PantallaRequestDTO.java (NUEVO)
    Ubicación: src/main/java/security/aplication/dto/
    Validaciones: @NotNull, @NotBlank, @Positive
    Campos: codigo, nome, url, codModulo
    
  ✓ PantallaResponseDTO.java (NUEVO)
    Ubicación: src/main/java/security/aplication/dto/
    Incluye: Toda la info de respuesta + auditoría completa
    
  ✓ PantallaService.java (SERVICIO - NUEVO)
    Ubicación: src/main/java/security/aplication/service/
    Implementa: PantallaInputPort
    Orquesta: 4 casos de uso
    Scope: @ApplicationScoped (singleton)
    
  ✓ CrearPantallaUseCase.java (NUEVO)
    Ubicación: src/main/java/security/aplication/usecase/
    - Valida datos
    - Registra userC y fechaC
    - Persiste mediante repositorio
    - Retorna pantalla con ID asignado
    
  ✓ BuscarPantallaPorIdUseCase.java (NUEVO)
    Ubicación: src/main/java/security/aplication/usecase/
    - Valida ID
    - Busca en repositorio
    - Lanza SecurityNotFoundException si no existe
    
  ✓ ActualizarPantallaUseCase.java (NUEVO)
    Ubicación: src/main/java/security/aplication/usecase/
    - Verifica existencia
    - Valida nuevos datos
    - Preserva userC/fechaC
    - Registra userMod/fechaMod
    - Persiste cambios
    
  ✓ EliminarPantallaUseCase.java (NUEVO)
    Ubicación: src/main/java/security/aplication/usecase/
    - Verifica existencia
    - Elimina de BD
    - Lanza excepción si no existe


📂 FRAMEWORK LAYER - INPUT (framework/input/)
═══════════════════════════════════════════════════════════════════

  ✓ PantallaInputMapper.java (NUEVO)
    Ubicación: src/main/java/security/framework/input/mapper/
    MapStruct con @Mapper(componentModel="cdi")
    Conversiones:
      - PantallaRequestDTO → Pantalla (dominio)
      - Pantalla → PantallaResponseDTO
    
  ✓ PantallaController.java (NUEVO)
    Ubicación: src/main/java/security/framework/input/controller/
    @Path("/pantalla")
    Endpoints REST:
      POST   /pantalla         → Crear (201 Created)
      GET    /pantalla/{id}    → Buscar (200 OK)
      PUT    /pantalla/{id}    → Actualizar (200 OK)
      DELETE /pantalla/{id}    → Eliminar (204 No Content)
    
    Excepciones manejadas por GlobalExceptionHandler:
      - 400 Bad Request: SecurityValidationException
      - 404 Not Found: SecurityNotFoundException
      - 500 Internal Server Error: Otros errores


📂 FRAMEWORK LAYER - OUTPUT (framework/output/)
═══════════════════════════════════════════════════════════════════

  ✓ PantallaJpaEntity.java (NUEVO - YA CREADO ANTERIORMENTE)
    Ubicación: src/main/java/security/framework/output/persistence/
    @Entity @Table(name="pantallas", schema="kafka")
    Campos: id, codigo, nome, url, codModulo
            userC, fechaC, userMod, fechaMod
    Tipo: Panache Entity para Quarkus
    
  ✓ PantallaJpaRepository.java (NUEVO - YA CREADO ANTERIORMENTE)
    Ubicación: src/main/java/security/framework/output/persistence/
    Extends PanacheRepository<PantallaJpaEntity>
    @ApplicationScoped
    
  ✓ PantallaOutputMapper.java (NUEVO - YA CREADO ANTERIORMENTE)
    Ubicación: src/main/java/security/framework/output/mapper/
    MapStruct con @Mapper(componentModel="cdi")
    Conversiones:
      - Pantalla (dominio) → PantallaJpaEntity (JPA)
      - PantallaJpaEntity → Pantalla (dominio)
    Preserva auditoría: @Mapping(target="userC", ignore=true)
    
  ✓ PantallaRepositoryAdapter.java (NUEVO)
    Ubicación: src/main/java/security/framework/output/persistence/
    Implementa: PantallaRepository (puerto de salida)
    Depende: PantallaJpaRepository, PantallaOutputMapper
    Métodos:
      - save()     → Persistir nueva pantalla
      - findById() → Buscar por ID
      - update()   → Actualizar preservando auditoría
      - deleteById() → Eliminar


📂 CONFIGURATION (framework/config/)
═══════════════════════════════════════════════════════════════════

  ✓ ApplicationConfig.java (ACTUALIZADO)
    Ubicación: src/main/java/security/framework/config/
    Agregado: pantallaService() @Produces
    - Inyecta PantallaRepository
    - Produce PantallaInputPort (PantallaService)
    - Scope: @ApplicationScoped


================================================================================
3. FLUJOS DE EJECUCIÓN
================================================================================

FLUJO 1: CREATE (POST /pantalla)
════════════════════════════════════════════════════════════════════

HTTP Request: POST /pantalla
  ↓
PantallaController.crear()
  ↓ Valida @Valid PantallaRequestDTO
  ↓
PantallaInputMapper.toDomain() → Pantalla (dominio)
  ↓
PantallaService.crear() (implementa PantallaInputPort)
  ↓
CrearPantallaUseCase.ejecutar()
  ├─ Validar datos
  ├─ Registrar: userC = "SYSTEM" (TODO: Del SecurityContext)
  ├─ Registrar: fechaC = LocalDateTime.now()
  ↓
PantallaRepository.save() (puerto de salida)
  ↓
PantallaRepositoryAdapter.save()
  ├─ PantallaOutputMapper.toJpaEntity() → PantallaJpaEntity
  ├─ pantallaJpaRepository.persist() → BD asigna ID
  ├─ PantallaOutputMapper.toDomain() → Pantalla con ID
  ↓
Response: PantallaResponseDTO (status 201 Created + Location header)


FLUJO 2: SEARCH (GET /pantalla/{id})
════════════════════════════════════════════════════════════════════

HTTP Request: GET /pantalla/{id}
  ↓
PantallaController.buscarPorId(id)
  ↓
PantallaService.buscarPorId() (implementa PantallaInputPort)
  ↓
BuscarPantallaPorIdUseCase.ejecutar()
  ├─ Validar ID
  ↓
PantallaRepository.findById(id)
  ↓
PantallaRepositoryAdapter.findById()
  ├─ pantallaJpaRepository.findById() → PantallaJpaEntity
  ├─ PantallaOutputMapper.toDomain() → Pantalla
  ↓
PantallaInputMapper.toResponseDto() → PantallaResponseDTO
  ↓
Response: PantallaResponseDTO (status 200 OK)


FLUJO 3: UPDATE (PUT /pantalla/{id})
════════════════════════════════════════════════════════════════════

HTTP Request: PUT /pantalla/{id}
  ↓
PantallaController.actualizar(id, PantallaRequestDTO)
  ↓ Valida @Valid PantallaRequestDTO
  ↓
PantallaInputMapper.toDomain() → Pantalla (dominio)
  ↓
PantallaService.actualizar() (implementa PantallaInputPort)
  ↓
ActualizarPantallaUseCase.ejecutar()
  ├─ Verificar existencia
  ├─ Validar nuevos datos
  ├─ Registrar: userMod = "SYSTEM" (TODO: Del SecurityContext)
  ├─ Registrar: fechaMod = LocalDateTime.now()
  ├─ Preservar: userC y fechaC (immutable)
  ↓
PantallaRepository.update() (puerto de salida)
  ↓
PantallaRepositoryAdapter.update()
  ├─ pantallaJpaRepository.findById() → Pantalla existente
  ├─ PantallaOutputMapper.applyToEntity() → Aplica cambios
  │  └─ @Mapping(target="userC", ignore=true)
  │  └─ @Mapping(target="fechaC", ignore=true)
  ├─ pantallaJpaRepository.persist() → Merge/Update en BD
  ├─ PantallaOutputMapper.toDomain() → Pantalla actualizada
  ↓
PantallaInputMapper.toResponseDto() → PantallaResponseDTO
  ↓
Response: PantallaResponseDTO (status 200 OK)


FLUJO 4: DELETE (DELETE /pantalla/{id})
════════════════════════════════════════════════════════════════════

HTTP Request: DELETE /pantalla/{id}
  ↓
PantallaController.eliminar(id)
  ↓
PantallaService.eliminar() (implementa PantallaInputPort)
  ↓
EliminarPantallaUseCase.ejecutar()
  ├─ Validar ID
  ├─ Verificar existencia
  ↓
PantallaRepository.deleteById(id)
  ↓
PantallaRepositoryAdapter.deleteById()
  ├─ pantallaJpaRepository.deleteById(id) → Elimina de BD
  ↓
Response: No Content (status 204 No Content)


================================================================================
4. AUDITORÍA Y CAMPOS
================================================================================

CREACIÓN (CREATE):
  - userC: Usuario que crea (registrado en CrearPantallaUseCase)
  - fechaC: LocalDateTime.now() en creación
  - Estos campos son INMUTABLES después de crear

MODIFICACIÓN (UPDATE):
  - userMod: Usuario que modifica (registrado en ActualizarPantallaUseCase)
  - fechaMod: LocalDateTime.now() en cada cambio
  - userC/fechaC se PRESERVAN (ignorados en mapper)
  - Estos campos se ACTUALIZAN en cada cambio


IMPLEMENTACIÓN EN MAPPERS:

PantallaOutputMapper.applyToEntity():
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "userC", ignore = true)    // Preserva original
  @Mapping(target = "fechaC", ignore = true)   // Preserva original
  void applyToEntity(Pantalla domain, @MappingTarget PantallaJpaEntity entity);


================================================================================
5. EXCEPCIONES Y ERROR HANDLING
================================================================================

GlobalExceptionHandler mapea excepciones a respuestas HTTP:

SecurityValidationException → 400 Bad Request
  Ejemplo: "URL de pantalla no puede estar vacía"
  Causas:
    - URL/nombre vacío
    - Módulo no asociado
    - Datos inválidos

SecurityNotFoundException → 404 Not Found
  Ejemplo: "Pantalla no encontrada con ID: 99"
  Causas:
    - BuscarPantallaPorIdUseCase no encuentra pantalla
    - ActualizarPantallaUseCase - pantalla no existe
    - EliminarPantallaUseCase - pantalla no existe

Cualquier otra excepción → 500 Internal Server Error
  GlobalExceptionHandler la captura y responde con JSON estándar


================================================================================
6. VALIDACIONES
================================================================================

PantallaRequestDTO - Anotaciones de validación:

  @NotNull BigInteger codigo       // No puede ser null
  @NotNull BigInteger nome         // No puede ser null
  @NotNull String url              // No puede ser null
  @NotNull BigInteger codModulo    // No puede ser null
  
  @NotBlank String url             // No puede estar vacío
  @Positive BigInteger codModulo   // Debe ser > 0

UseCase - Validaciones de lógica de negocio:

  CrearPantallaUseCase:
    ✓ Pantalla no nula
    ✓ URL no vacía
    ✓ Módulo debe estar referenciado

  ActualizarPantallaUseCase:
    ✓ Pantalla debe existir antes de actualizar
    ✓ URL no puede estar vacía
    ✓ Módulo debe ser válido


================================================================================
7. INYECCIÓN DE DEPENDENCIAS (CDI)
================================================================================

Cadena de inyección:

  1. ApplicationConfig.pantallaService() produce PantallaInputPort
     └─ Inyecta PantallaRepository (inyectado por CDI)
     
  2. PantallaController inyecta:
     ├─ PantallaInputPort (del @Produces de ApplicationConfig)
     └─ PantallaInputMapper (encontrado por tipo en CDI)
     
  3. PantallaRepositoryAdapter inyecta:
     ├─ PantallaJpaRepository (encontrado por tipo en CDI)
     └─ PantallaOutputMapper (encontrado por tipo en CDI)
     
  4. MapStruct Mappers (@Mapper(componentModel="cdi")):
     ├─ PantallaInputMapper (CDI bean)
     └─ PantallaOutputMapper (CDI bean)


Scopes (Ciclo de vida):

  @ApplicationScoped:
    ├─ PantallaService (una instancia por aplicación)
    ├─ PantallaRepositoryAdapter (una instancia por aplicación)
    ├─ PantallaJpaRepository (una instancia por aplicación)
    └─ ApplicationConfig (una instancia por aplicación)
    
  Predeterminado (Dependent):
    ├─ PantallaController (nueva por request HTTP)
    ├─ CrearPantallaUseCase (nueva instancia en Service)
    ├─ BuscarPantallaPorIdUseCase (nueva instancia en Service)
    ├─ ActualizarPantallaUseCase (nueva instancia en Service)
    └─ EliminarPantallaUseCase (nueva instancia en Service)


================================================================================
8. TESTING RECOMMENDATIONS
================================================================================

ENDPOINTS A PROBAR:

  1. Create:
     POST /api/security/pantalla
     {
       "codigo": 101,
       "nome": 501,
       "url": "/admin/usuarios",
       "codModulo": 1
     }
     Esperado: 201 Created + Location: /pantalla/{id}

  2. Read:
     GET /api/security/pantalla/1
     Esperado: 200 OK + PantallaResponseDTO con auditoría

  3. Update:
     PUT /api/security/pantalla/1
     {
       "codigo": 101,
       "nome": 501,
       "url": "/admin/usuarios/updated",
       "codModulo": 1
     }
     Esperado: 200 OK + PantallaResponseDTO con userMod/fechaMod actualizados

  4. Delete:
     DELETE /api/security/pantalla/1
     Esperado: 204 No Content

  5. Error - Not Found:
     GET /api/security/pantalla/999
     Esperado: 404 Not Found + ErrorResponseDTO

  6. Error - Validation:
     POST /api/security/pantalla
     {
       "codigo": null,
       "nome": 501,
       "url": "",
       "codModulo": -1
     }
     Esperado: 400 Bad Request + ErrorResponseDTO con detalles


================================================================================
9. NEXT STEPS / MEJORAS FUTURAS
================================================================================

TODO Items:

  1. [MEDIO] Obtener usuario de SecurityContext
     En CrearPantallaUseCase y ActualizarPantallaUseCase:
     - Cambiar "SYSTEM" por valor real de SecurityContext
     - Inyectar un componente que proporcione usuario actual
     
  2. [BAJO] Considerar soft-delete
     En EliminarPantallaUseCase:
     - Agregar userEliminado y fechaEliminado
     - No eliminar físicamente, solo marcar como eliminado
     
  3. [MEDIO] Agregar métodos de búsqueda complejos
     En PantallaJpaRepository:
     - findByCodigo()
     - findByModuloId()
     - findByNome() (búsqueda parcial)
     
  4. [BAJO] Implementar paginación
     En PantallaInputPort:
     - buscarTodos() con PageRequest
     - buscarPorModulo() paginado
     
  5. [BAJO] Agregar transacciones explícitas
     En PantallaService:
     - @Transactional en métodos que modifican datos
     
  6. [ALTO] Integrar con transacciones distribuidas
     Si se requiere consistencia con otros módulos


================================================================================
10. SUMMARY
================================================================================

✓ COMPLETADO: Ecosistema completo de Pantalla con:
  
  Domain Layer:
    ✓ Entidad Pantalla actualizada con auditoría completa
    
  Application Layer:
    ✓ PantallaService (orquestador)
    ✓ 4 Use Cases (Crear, Buscar, Actualizar, Eliminar)
    ✓ 2 Puertos (Input, Output)
    ✓ DTOs con validación
    
  Framework Layer Input:
    ✓ PantallaController (REST)
    ✓ PantallaInputMapper
    ✓ 4 Endpoints REST
    
  Framework Layer Output:
    ✓ PantallaRepositoryAdapter
    ✓ PantallaOutputMapper
    ✓ PantallaJpaEntity
    ✓ PantallaJpaRepository
    
  Configuration:
    ✓ ApplicationConfig actualizado con bean PantallaService

TOTAL: ~2500 líneas de código bien documentado con patrón hexagonal puro.

================================================================================
End of Document
================================================================================
