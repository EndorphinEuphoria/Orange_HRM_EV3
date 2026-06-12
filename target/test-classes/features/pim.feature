Feature: Gestion de empleados PIM

  Scenario: TC-003 Navegacion al modulo PIM
    Given el usuario ha iniciado sesion como administrador
    When hace clic en el menu PIM
    Then debe visualizar el listado de empleados

  Scenario Outline: TC-004 Buscar empleado por nombre
    Given el usuario esta en el modulo PIM
    When busca al empleado desde la hoja "<hoja>" fila <fila>
    Then debe visualizar resultados relacionados
    Examples:
      | hoja           | fila |
      | BuscarEmpleado | 2    |

  Scenario Outline: TC-005 Buscar empleado inexistente
    Given el usuario esta en el modulo PIM
    When busca al empleado inexistente desde la hoja "<hoja>" fila <fila>
    Then no debe mostrar resultados
    Examples:
      | hoja           | fila |
      | BuscarEmpleado | 3    |

  Scenario Outline: TC-011 Buscar empleado por letra
    Given el usuario esta en el modulo PIM
    When busca empleados desde la hoja "<hoja>" fila <fila>
    Then debe mostrar empleados que contengan la letra
    Examples:
      | hoja           | fila |
      | BuscarEmpleado | 4    |

  Scenario: TC-012 Validar acceso a modulos desde el menu lateral
    Given el usuario ha iniciado sesion para validar la navegabilidad
    When el usuario selecciona la opcion "PIM" del menu lateral
    Then el sistema deberia redirigir a la seccion con el encabezado "PIM"

