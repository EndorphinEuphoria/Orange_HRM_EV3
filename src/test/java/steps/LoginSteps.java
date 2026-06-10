package steps;

import io.cucumber.java.es.*;
import pages.LoginPage;
import utils.Hooks;
import static org.junit.Assert.*;

public class LoginSteps {

    private LoginPage loginPage() {
        return new LoginPage(Hooks.getDriver());
    }

    @Dado("el usuario esta en la pagina de login")
    public void usuarioEnPaginaLogin() {
        loginPage().abrirPagina();
    }

    @Cuando("ingresa usuario {string} y contrasena {string}")
    public void ingresarCredenciales(String usuario, String contrasena) {
        loginPage().ingresarUsuario(usuario);
        loginPage().ingresarContrasena(contrasena);
    }

    @Y("hace clic en Login")
    public void hacerClicLogin() {
        loginPage().clickLogin();
    }

    @Entonces("debe ser redirigido al Dashboard")
    public void verificarDashboard() {
        assertTrue(loginPage().obtenerURLActual().contains("dashboard"));
    }

    @Entonces("debe aparecer el mensaje de error {string}")
    public void verificarMensajeError(String mensajeEsperado) {
        assertEquals(mensajeEsperado, loginPage().obtenerMensajeError());
    }
}