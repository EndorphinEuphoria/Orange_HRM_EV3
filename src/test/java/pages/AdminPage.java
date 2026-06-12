package pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private FluentWait<WebDriver> fluentWait;

    private By campoUsuario         = By.name("username");
    private By campoContrasena      = By.name("password");
    private By botonLogin           = By.cssSelector("button[type='submit']");
    private By menuAdmin            = By.xpath("//span[text()='Admin']");
    private By botonAdd             = By.xpath("//button[normalize-space()='Add']");
    private By empleadoInput        = By.xpath("//input[@placeholder='Type for hints...']");
    private By opcionesAutocomplete = By.xpath("//div[@role='listbox']//div[@role='option']");
    private By listbox              = By.xpath("//div[@role='listbox']");
    private By usernameInput        = By.xpath("//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private By passwordInput        = By.xpath("(//input[@type='password'])[1]");
    private By confirmPasswordInput = By.xpath("(//input[@type='password'])[2]");
    private By botonSave            = By.xpath("//button[@type='submit']");
    private By tablaUsuarios        = By.cssSelector(".oxd-table-body");
    private By mensajeError         = By.cssSelector(".oxd-input-field-error-message");

    public AdminPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        this.fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    public void iniciarSesionAdmin() {
    driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    wait.until(ExpectedConditions.visibilityOfElementLocated(campoUsuario)).sendKeys("Admin");
    driver.findElement(campoContrasena).sendKeys("admin123");
    driver.findElement(botonLogin).click();
    wait.until(ExpectedConditions.urlContains("dashboard"));
    driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewSystemUsers");
    wait.until(ExpectedConditions.urlContains("admin"));
    }

   public void clickAdmin() {
    wait.until(ExpectedConditions.urlContains("admin"));
    }

    public boolean estaEnAdmin() {
        wait.until(ExpectedConditions.urlContains("admin"));
        return driver.getCurrentUrl().contains("admin");
    }

    public void clickAdd() {
        wait.until(ExpectedConditions.urlContains("admin"));
        wait.until(ExpectedConditions.elementToBeClickable(botonAdd)).click();
    }

    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(botonSave)).click();
    }

    public boolean usuarioCreado() {
        try {
            wait.until(ExpectedConditions.urlContains("viewSystemUsers"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tablaUsuarios));
            return true;
        } catch (Exception e) {
            driver.findElements(mensajeError)
                    .forEach(err -> System.out.println("VALIDACION: " + err.getText()));
            return false;
        }
    }

   public boolean usuarioDuplicado() {
    try {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(driver -> {
            String url = driver.getCurrentUrl();
            if (url.contains("saveSystemUser")) {
                return !driver.findElements(
                    By.cssSelector(".oxd-input-field-error-message")).isEmpty();
            }
            return false;
        });
        return true;
    } catch (Exception e) {
        System.out.println("URL actual: " + driver.getCurrentUrl());
        driver.findElements(By.cssSelector(".oxd-input-field-error-message"))
            .forEach(el -> { try { System.out.println("ERROR: [" + el.getText() + "]"); } catch (Exception ignored) {} });
        return false;
    }
}

    private void escribirEnAutocompleteYEsperar(WebElement campo, String texto) {
        campo.click();
        campo.clear();
        campo.sendKeys(texto);
        fluentWait.until(driver -> {
            List<WebElement> opciones = driver.findElements(opcionesAutocomplete);
            if (opciones.isEmpty()) return false;
            String primerTexto = opciones.get(0).getText().trim();
            return !primerTexto.equalsIgnoreCase("Searching....") && !primerTexto.isEmpty();
        });
    }

    private void seleccionarOpcionAutocomplete(String textoEsperado) {
        List<WebElement> opciones = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(opcionesAutocomplete));
        boolean encontrado = false;
        for (WebElement opcion : opciones) {
            try {
                if (opcion.getText().trim().contains(textoEsperado)) {
                    wait.until(ExpectedConditions.elementToBeClickable(opcion)).click();
                    encontrado = true;
                    break;
                }
            } catch (StaleElementReferenceException e) {
                System.out.println("STALE ELEMENT — reintentando");
            }
        }
        if (!encontrado) throw new RuntimeException("Opcion no encontrada en autocomplete: " + textoEsperado);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(listbox));
    }

    private void esperarValidacionFormulario() {
        fluentWait.until(driver -> {
            if (driver.getCurrentUrl().contains("viewSystemUsers")) return true;
            return !driver.findElements(mensajeError).isEmpty();
        });
    }

    private void seleccionarDropdown(String labelTexto, String opcionTexto) {
        By dropdown = By.xpath(
                "//label[text()='" + labelTexto + "']/ancestor::div[contains(@class,'oxd-input-group')]" +
                "//div[contains(@class,'oxd-select-text')]");
        wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();
        By opcion = By.xpath("//div[@role='option']//span[normalize-space()='" + opcionTexto + "']");
        wait.until(ExpectedConditions.elementToBeClickable(opcion)).click();
    }

    public void completarFormularioUsuario() {
        seleccionarDropdown("User Role", "Admin");

        WebElement empInput = wait.until(ExpectedConditions.elementToBeClickable(empleadoInput));
        escribirEnAutocompleteYEsperar(empInput, "Jobin");
        seleccionarOpcionAutocomplete("Jobin Mathew Sam");

        seleccionarDropdown("Status", "Enabled");

        String username = "user" + System.currentTimeMillis();
        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        userField.clear();
        userField.sendKeys(username);

        WebElement passField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        passField.clear();
        passField.sendKeys("Admin123!");

        WebElement confirmField = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordInput));
        confirmField.clear();
        confirmField.sendKeys("Admin123!");
        confirmField.sendKeys(Keys.TAB);

        esperarValidacionFormulario();
    }

    public void completarFormularioUsuarioDuplicado(String username) {
        seleccionarDropdown("User Role", "Admin");

        WebElement empInput = wait.until(ExpectedConditions.elementToBeClickable(empleadoInput));
        escribirEnAutocompleteYEsperar(empInput, "Jobin");
        seleccionarOpcionAutocomplete("Jobin Mathew Sam");

        seleccionarDropdown("Status", "Enabled");

        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        userField.clear();
        userField.sendKeys(username);
        userField.sendKeys(Keys.TAB);

        WebElement passField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        passField.clear();
        passField.sendKeys("Admin123!");

        WebElement confirmField = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordInput));
        confirmField.clear();
        confirmField.sendKeys("Admin123!");
        confirmField.sendKeys(Keys.TAB);
        wait.until(ExpectedConditions.elementToBeClickable(botonSave)).click();
    }

    public void completarFormularioDesdeExcel(
            String userRole, String employeeName, String status,
            String username, String password, String confirmPassword) {

        seleccionarDropdown("User Role", userRole);

        String hint = employeeName.split(" ")[0];
        WebElement empInput = wait.until(ExpectedConditions.elementToBeClickable(empleadoInput));
        escribirEnAutocompleteYEsperar(empInput, hint);
        seleccionarOpcionAutocomplete(employeeName);

        seleccionarDropdown("Status", status);

        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        userField.clear();
        userField.sendKeys(username);

        WebElement passField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        passField.clear();
        passField.sendKeys(password);

        WebElement confirmField = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordInput));
        confirmField.clear();
        confirmField.sendKeys(confirmPassword);
        confirmField.sendKeys(Keys.TAB);

        esperarValidacionFormulario();
    }
}