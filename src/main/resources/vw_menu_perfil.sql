-- Vista: VW_MENU_PERFIL
-- 
-- Combina información de menús, perfiles y pantallas
-- para obtener datos completos de menús asignados a perfiles

DROP VIEW IF EXISTS kafka.vw_menu_perfil;

CREATE VIEW kafka.vw_menu_perfil AS
SELECT 
    m.id AS id_menu,
    mp.perfil_id AS id_perfil,
    m.nombre,
    m.jerarquia AS jerarq,
    m.codmenupadre AS menu_padre,
    m.orden,
    p.url
FROM 
    kafka.menus m
    INNER JOIN kafka.menu_perfil mp ON m.id = mp.menu_id
    LEFT JOIN kafka.pantallas p ON m.codpantalla = p.id
WHERE 
    m.estado = 1;
