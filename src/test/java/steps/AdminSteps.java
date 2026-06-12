package steps;

import static org.junit.Assert.assertTrue;
import io.cucumber.java.an.Y;
import io.cucumber.java.en.Then;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import pages.AdminPage;
import utils.Excel;
import utils.Hooks;

public class AdminSteps {

    private AdminPage adminPage() {
        return new AdminPage(Hooks.getDriver());
    }

    @Dado("el administrador esta autenticado en el modulo Admin")
    public void iniciarSesionAdmin() {
        adminPage().iniciarSesionAdmin();
        adminPage().clickAdmin();
    }

    @Cuando("hace clic en el menu Admin")
    public void clickMenuAdmin() {
        adminPage().clickAdmin();
    }

    @Entonces("debe visualizar el listado de usuarios")
    public void validarAdmin() {
        assertTrue(adminPage().estaEnAdmin());
    }

    @Cuando("hace clic en Add")
    public void clickAdd() {
        adminPage().clickAdd();
    }
 
    @Y("completa los datos del nuevo usuario desde la hoja {string} fila {int}")
    public void completarUsuarioDesdeExcel(String hoja, int fila) {
        String userRole = Excel.leerCeldaDeHoja(hoja, fila, 2);
        String employeeName = Excel.leerCeldaDeHoja(hoja, fila, 3);
        String status = Excel.leerCeldaDeHoja(hoja, fila, 4);
        String username = Excel.leerCeldaDeHoja(hoja, fila, 5);
        String password = Excel.leerCeldaDeHoja(hoja, fila, 6);
        String confirmPassword = Excel.leerCeldaDeHoja(hoja, fila, 7);
        adminPage().completarFormularioDesdeExcel(userRole, employeeName, status, username, password, confirmPassword);
    }

    @Y("hace clic en Save")
    public void clickSave() {
        adminPage().clickSave();
    }

    @Entonces("el usuario se crea correctamente")
    public void validarUsuarioCreado() {
        assertTrue(adminPage().usuarioCreado());
    }

    
    @Y("completa los datos con usuario duplicado desde la hoja {string} fila {int}")
    public void completarUsuarioDuplicado(String hoja, int fila) {
        String username = Excel.leerCeldaDeHoja(hoja, fila, 2);
        adminPage().completarFormularioUsuarioDuplicado(username);
    }

    @Then("el sistema debe rechazar la creacion del usuario")
    public void validarUsuarioDuplicado() {
        assertTrue(adminPage().usuarioDuplicado());
    }
}