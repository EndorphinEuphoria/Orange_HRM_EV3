package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SecurityPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By campoUsuario  = By.name("username");
    private By campoPassword = By.name("password");
    private By botonLogin    = By.cssSelector("button[type='submit']");
    private By menuUsuario   = By.xpath("//*[contains(@class,'oxd-userdropdown-tab')]");
    private By logout        = By.xpath("//a[normalize-space()='Logout']");

    public SecurityPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void abrirDashboardSinLogin() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
    }

    public String obtenerUrlActual() {
        return driver.getCurrentUrl();
    }

    public void iniciarSesionAdmin() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(campoUsuario)).sendKeys("Admin");
        driver.findElement(campoPassword).sendKeys("admin123");
        driver.findElement(botonLogin).click();
        wait.until(ExpectedConditions.urlContains("dashboard"));
    }

    public void cerrarSesion() {
    wait.until(ExpectedConditions.elementToBeClickable(menuUsuario)).click();
    WebElement btnLogout = wait.until(
        ExpectedConditions.visibilityOfElementLocated(logout)); // visibilidad, no solo presencia
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnLogout);
    wait.until(ExpectedConditions.urlContains("auth/login"));
    }
}