  Feature: Seguridad - Control de Sesion

  Scenario: TC-010 Acceso directo a URL privada sin sesion activa
    Given el usuario no esta autenticado
    When intenta acceder directamente al dashboard
    Then debe ser redirigido al login