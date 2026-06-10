package steps;

import static org.junit.Assert.assertTrue;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import pages.PimPage;
import utils.Hooks;

public class PimSteps {

    private PimPage pimPage() {
        return new PimPage(Hooks.getDriver());
    }

    @Dado("el usuario ha iniciado sesion como administrador")
    public void iniciarSesionAdministrador() {
        pimPage().iniciarSesionAdmin();
    }

    @Cuando("hace clic en el menu PIM")
    public void clickMenuPim() {
        pimPage().clickPim();
    }

    @Entonces("debe visualizar el listado de empleados")
    public void validarPim() {
        assertTrue(pimPage().estaEnPim());
    }

    @Dado("el usuario esta en el modulo PIM")
    public void usuarioEnModuloPim() {
        pimPage().abrirModuloPim();
    }

    @Cuando("busca al empleado {string}")
    public void buscarEmpleado(String nombre) {
        pimPage().buscarEmpleado(nombre);
    }

    @Entonces("debe visualizar resultados relacionados")
    public void validarResultadosBusqueda() {
        assertTrue(pimPage().hayResultadosBusqueda());
    }

    @Cuando("busca al empleado inexistente {string}")
    public void buscarEmpleadoInexistente(String nombre) {
        pimPage().buscarEmpleado(nombre);
    }

    @Entonces("no debe mostrar resultados")
    public void validarSinResultados() {
        assertTrue(pimPage().noHayResultados());
    }

    @Cuando("busca empleados con la letra {string}")
    public void buscarEmpleadoPorLetra(String letra) {
        pimPage().buscarEmpleado(letra);
    }

    @Entonces("debe mostrar empleados que contengan la letra")
    public void validarBusquedaPorLetra() {
        assertTrue(pimPage().hayResultadosBusqueda());
    }
}