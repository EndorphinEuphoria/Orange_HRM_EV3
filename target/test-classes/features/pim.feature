Feature: Gestion de empleados PIM

  Scenario: TC-003 Navegacion al modulo PIM
    Given el usuario ha iniciado sesion como administrador
    When hace clic en el menu PIM
    Then debe visualizar el listado de empleados

  Scenario: TC-004 Buscar empleado por nombre
    Given el usuario esta en el modulo PIM
    When busca al empleado "Charles"
    Then debe visualizar resultados relacionados

  Scenario: TC-005 Buscar empleado inexistente
    Given el usuario esta en el modulo PIM
    When busca al empleado inexistente "zzz_noexiste"
    Then no debe mostrar resultados

  Scenario: TC-011 Buscar empleado por letra
   Given el usuario esta en el modulo PIM
   When busca empleados con la letra "a"
   Then debe mostrar empleados que contengan la letra

    