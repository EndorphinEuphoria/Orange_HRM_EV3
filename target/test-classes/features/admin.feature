Feature: Administracion del sistema

  Scenario: TC-006 Navegacion al modulo Admin
    Given el administrador esta autenticado en el modulo Admin
    When hace clic en el menu Admin
    Then debe visualizar el listado de usuarios

  Scenario Outline: TC-008 Crear nuevo usuario
    Given el administrador esta autenticado en el modulo Admin
    When hace clic en Add
    And completa los datos del nuevo usuario desde la fila <fila>
    And hace clic en Save
    Then el usuario se crea correctamente

    Examples:
      | fila |
      | 2    |

  Scenario: TC-009 Crear usuario con username duplicado
    Given el administrador esta autenticado en el modulo Admin
    When hace clic en Add
    And completa los datos con usuario duplicado
    Then el sistema debe rechazar la creacion del usuario