
package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PimPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By campoUsuario      = By.name("username");
    private By campoPassword     = By.name("password");
    private By botonLogin        = By.cssSelector("button[type='submit']");
    private By menuPim           = By.xpath("//span[text()='PIM']");
    private By tituloPim         = By.xpath("//h6[text()='PIM']");
    private By inputEmployeeName = By.xpath("(//input[@placeholder='Type for hints...'])[1]");
    private By botonSearch       = By.xpath("//button[@type='submit']");
    private By filasTabla        = By.cssSelector(".oxd-table-body .oxd-table-row");

    public PimPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void iniciarSesionAdmin() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(campoUsuario)).sendKeys("Admin");
        driver.findElement(campoPassword).sendKeys("admin123");
        driver.findElement(botonLogin).click();
        wait.until(ExpectedConditions.urlContains("dashboard"));
    }

    public void clickPim() {
        wait.until(ExpectedConditions.elementToBeClickable(menuPim)).click();
    }

    public boolean estaEnPim() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(tituloPim)).isDisplayed();
    }

    public void abrirModuloPim() {
        iniciarSesionAdmin();
        wait.until(ExpectedConditions.elementToBeClickable(menuPim)).click();
        wait.until(ExpectedConditions.urlContains("pim"));
    }

    public void buscarEmpleado(String nombre) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputEmployeeName)).sendKeys(nombre);
        wait.until(ExpectedConditions.elementToBeClickable(botonSearch)).click();
    }

    public boolean hayResultadosBusqueda(String nombre) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(filasTabla));
            return !driver.findElements(filasTabla).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean noHayResultados() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.invisibilityOfElementLocated(
                            By.className("oxd-loading-spinner")));
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.or(
                            ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='No Records Found']")),
                            ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='No Records Found']")),
                            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-toast--info"))
                    ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickModuloMenu(String modulo) {
        By opcion = By.xpath("//ul[@class='oxd-main-menu']//span[text()='" + modulo + "']");
        wait.until(ExpectedConditions.elementToBeClickable(opcion)).click();
    }

    public boolean encabezadoVisible(String encabezado) {
        By titulo = By.xpath(String.format("//h6[text()='%s']", encabezado));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(titulo)).isDisplayed();
    }
}