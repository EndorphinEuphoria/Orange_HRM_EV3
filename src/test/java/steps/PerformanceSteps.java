package steps;

import static org.junit.Assert.assertTrue;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import pages.PerformancePage;
import utils.Excel;
import utils.Hooks;

public class PerformanceSteps {

    private PerformancePage performancePage() {
        return new PerformancePage(Hooks.getDriver());
    }

    @Dado("el administrador ha iniciado sesion y navega al modulo de Performance")
    public void iniciarSesionYNavegarPerformance() {
        performancePage().iniciarSesionYNavegar();
    }

    @Cuando("filtra los KPIs por el puesto de trabajo desde la hoja {string} fila {int}")
    public void filtrarKpisPorPuesto(String hoja, int fila) {
        String puesto = Excel.leerCeldaDeHoja(hoja, fila, 2);
        performancePage().filtrarPorPuesto(puesto);
    }

    @Entonces("la lista de KPIs deberia mostrar registros asociados al puesto desde la hoja {string} fila {int}")
    public void validarResultadosPorPuesto(String hoja, int fila) {
        String puesto = Excel.leerCeldaDeHoja(hoja, fila, 2);
        assertTrue(performancePage().hayResultadosPorPuesto(puesto));
    }

    @Cuando("registra un nuevo KPI desde la hoja {string} fila {int}")
    public void crearNuevoKpi(String hoja, int fila) {
        String puesto = Excel.leerCeldaDeHoja(hoja, fila, 2);
        String kpiName = Excel.leerCeldaDeHoja(hoja, fila, 3);
        performancePage().crearKpi(kpiName, puesto);
    }

    @Entonces("la lista de KPIs deberia incluir el indicador desde la hoja {string} fila {int}")
    public void validarKpiEnLista(String hoja, int fila) {
        String kpiName = Excel.leerCeldaDeHoja(hoja, fila, 3);
        assertTrue(performancePage().listaIncluyeKpi(kpiName));
    }
}