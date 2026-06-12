package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PerformancePage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By campoUsuario = By.name("username");
    private By campoPassword = By.name("password");
    private By botonLogin = By.cssSelector("button[type='submit']");
    private By menuPerformance = By.xpath("//span[text()='Performance']");
    private By menuKpi = By.xpath("//a[text()='KPIs']");
    private By dropdownJobTitle = By.xpath("(//div[contains(@class,'oxd-select-text-input')])[1]");
    private By botonSearch = By.xpath("//button[@type='submit']");
    private By botonAdd = By.xpath("//button[normalize-space()='Add']");
    private By campoKpiName = By.xpath("//label[text()='Key Performance Indicator']/following::input[1]");
    private By dropdownJobTitleForm = By.xpath("(//div[@class='oxd-select-text-input'])[1]");
    private By botonSave = By.xpath("//button[@type='submit']");
    private By tablaResultados = By.cssSelector(".oxd-table-body .oxd-table-row");

    public PerformancePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void iniciarSesionYNavegar() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(campoUsuario)).sendKeys("Admin");
        driver.findElement(campoPassword).sendKeys("admin123");
        driver.findElement(botonLogin).click();
        wait.until(ExpectedConditions.urlContains("dashboard"));

        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/performance/searchKpi");
        wait.until(ExpectedConditions.urlContains("searchKpi"));
    }

    public void filtrarPorPuesto(String puesto) {
        WebDriverWait waitLargo = new WebDriverWait(driver, Duration.ofSeconds(20));

        waitLargo.until(ExpectedConditions.elementToBeClickable(dropdownJobTitle)).click();

        By opcion = By.xpath(String.format("//span[text()='%s']", puesto));
        waitLargo.until(ExpectedConditions.elementToBeClickable(opcion)).click();
        waitLargo.until(ExpectedConditions.elementToBeClickable(botonSearch)).click();
        waitLargo.until(ExpectedConditions.presenceOfElementLocated(tablaResultados));
    }

    public boolean hayResultadosPorPuesto(String puesto) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(tablaResultados));
            return !driver.findElements(tablaResultados).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public void crearKpi(String kpiName, String puesto) {
        wait.until(ExpectedConditions.elementToBeClickable(botonAdd)).click();
        wait.until(ExpectedConditions.urlContains("saveKpi"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(campoKpiName)).sendKeys(kpiName);

        wait.until(ExpectedConditions.elementToBeClickable(dropdownJobTitleForm)).click();
        By opcion = By.xpath(String.format("//span[text()='%s']", puesto));
        wait.until(ExpectedConditions.elementToBeClickable(opcion)).click();

        wait.until(ExpectedConditions.elementToBeClickable(botonSave)).click();
        wait.until(ExpectedConditions.urlContains("searchKpi"));
    }

    public boolean listaIncluyeKpi(String kpiName) {
        try {
            By kpiEnTabla = By.xpath(String.format(
                    "//div[@class='oxd-table-cell oxd-padding-cell']//div[text()='%s']", kpiName));
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(kpiEnTabla)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}