package steps;

import static org.junit.Assert.assertTrue;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import pages.PimPage;
import utils.Excel;
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

    @Cuando("busca al empleado desde la hoja {string} fila {int}")
    public void buscarEmpleado(String hoja, int fila) {
        String nombre = Excel.leerCeldaDeHoja(hoja, fila, 2);
        pimPage().buscarEmpleado(nombre);
    }

    @Entonces("debe visualizar resultados relacionados")
    public void validarResultadosBusqueda() {
        String nombre = Excel.leerCeldaDeHoja("BuscarEmpleado", 2, 2);
        assertTrue(pimPage().hayResultadosBusqueda(nombre));
    }

    @Cuando("busca al empleado inexistente desde la hoja {string} fila {int}")
    public void buscarEmpleadoInexistente(String hoja, int fila) {
        String nombre = Excel.leerCeldaDeHoja(hoja, fila, 2);
        pimPage().buscarEmpleado(nombre);
    }

    @Entonces("no debe mostrar resultados")
    public void validarSinResultados() {
        assertTrue(pimPage().noHayResultados());
    }

    @Cuando("busca empleados desde la hoja {string} fila {int}")
    public void buscarEmpleadoPorLetra(String hoja, int fila) {
        String letra = Excel.leerCeldaDeHoja(hoja, fila, 2);
        pimPage().buscarEmpleado(letra);
    }

    @Entonces("debe mostrar empleados que contengan la letra")
    public void validarBusquedaPorLetra() {
        String letra = Excel.leerCeldaDeHoja("BuscarEmpleado", 4, 2);
        assertTrue(pimPage().hayResultadosBusqueda(letra));
    }

    @Dado("el usuario ha iniciado sesion para validar la navegabilidad")
    public void iniciarSesionNavegabilidad() {
        pimPage().iniciarSesionAdmin();
    }

    @Cuando("el usuario selecciona la opcion {string} del menu lateral")
    public void seleccionarOpcionMenu(String modulo) {
        pimPage().clickModuloMenu(modulo);
    }

    @Entonces("el sistema deberia redirigir a la seccion con el encabezado {string}")
    public void validarEncabezadoSeccion(String encabezado) {
        assertTrue(pimPage().encabezadoVisible(encabezado));
    }
}