  Feature: Seguridad - Control de Sesion

  Scenario: TC-010 Acceso directo a URL privada sin sesion activa
    Given el usuario no esta autenticado
    When intenta acceder directamente al dashboard
    Then debe ser redirigido al login

  Scenario: TC-017 Cierre de sesion exitoso
    Given el usuario ha iniciado sesion para probar el control de sesion
    When el usuario decide cerrar su sesion actual
    Then el sistema deberia redirigirlo automaticamente a la pagina de inicio de sesion