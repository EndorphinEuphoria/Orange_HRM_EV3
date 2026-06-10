Feature: Control de Acceso - Login OrangeHRM

  Scenario: TC-001 Inicio de sesion con credenciales validas
    Given el usuario esta en la pagina de login
    When ingresa usuario "Admin" y contrasena "admin123"
    And hace clic en Login
    Then debe ser redirigido al Dashboard

  Scenario: TC-002 Inicio de sesion con contrasena incorrecta
    Given el usuario esta en la pagina de login
    When ingresa usuario "Admin" y contrasena "wrong123"
    And hace clic en Login
    Then debe aparecer el mensaje de error "Invalid credentials"

    