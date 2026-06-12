package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By campoUsuario = By.name("username");
    private By campoContrasena = By.name("password");
    private By botonLogin = By.cssSelector("button[type='submit']");
    private By mensajeError = By.cssSelector(".oxd-alert-content-text");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void abrirPagina() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    public void ingresarUsuario(String usuario) {
        WebElement userInput =
                wait.until(ExpectedConditions.visibilityOfElementLocated(campoUsuario));

        userInput.clear();
        userInput.sendKeys(usuario);
    }

    public void ingresarContrasena(String contrasena) {
        WebElement passwordInput =
                wait.until(ExpectedConditions.visibilityOfElementLocated(campoContrasena));

        passwordInput.clear();
        passwordInput.sendKeys(contrasena);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(botonLogin))
                .click();
    }

    public String obtenerMensajeError() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(mensajeError)
        ).getText();
    }

    public String obtenerURLActual() {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    wait.until(ExpectedConditions.urlContains("dashboard"));

    return driver.getCurrentUrl();
}
}