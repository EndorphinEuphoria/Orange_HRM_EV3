package steps;

import static org.junit.Assert.assertTrue;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import pages.SecurityPage;
import utils.Hooks;

public class SecuritySteps {

    private SecurityPage securityPage() {
        return new SecurityPage(Hooks.getDriver());
    }

    @Dado("el usuario no esta autenticado")
    public void usuarioNoAutenticado() {

    }

    @Cuando("intenta acceder directamente al dashboard")
    public void accederDashboardDirectamente() {
        securityPage().abrirDashboardSinLogin();
    }

    @Entonces("debe ser redirigido al login")
    public void validarRedireccionLogin() {
        assertTrue(securityPage().obtenerUrlActual().contains("auth/login"));
    }

    @Dado("el usuario ha iniciado sesion para probar el control de sesion")
    public void iniciarSesionControlSesion() {
        securityPage().iniciarSesionAdmin();
    }

    @Cuando("el usuario decide cerrar su sesion actual")
    public void cerrarSesion() {
        securityPage().cerrarSesion();
    }

    @Entonces("el sistema deberia redirigirlo automaticamente a la pagina de inicio de sesion")
    public void validarRedireccionPaginaLogin() {
        assertTrue(securityPage().obtenerUrlActual().contains("auth/login"));
    }
}