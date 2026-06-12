Feature: Monitoreo de Performance

  Scenario Outline: TC-015 Buscar KPIs por puesto de trabajo
    Given el administrador ha iniciado sesion y navega al modulo de Performance
    When filtra los KPIs por el puesto de trabajo desde la hoja "<hoja>" fila <fila>
    Then la lista de KPIs deberia mostrar registros asociados al puesto desde la hoja "<hoja>" fila <fila>
    Examples:
      | hoja        | fila |
      | Performance | 2    |

  Scenario Outline: TC-016 Crear un nuevo Key Performance Indicator
    Given el administrador ha iniciado sesion y navega al modulo de Performance
    When registra un nuevo KPI desde la hoja "<hoja>" fila <fila>
    And filtra los KPIs por el puesto de trabajo desde la hoja "<hoja>" fila <fila>
    Then la lista de KPIs deberia incluir el indicador desde la hoja "<hoja>" fila <fila>
    Examples:
      | hoja        | fila |
      | Performance | 3    |