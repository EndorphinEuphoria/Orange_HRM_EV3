package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // =========================
    // LOGIN
    // =========================

    private By campoUsuario =
            By.name("username");

    private By campoContrasena =
            By.name("password");

    private By botonLogin =
            By.cssSelector("button[type='submit']");

    // =========================
    // MENU
    // =========================

    private By menuAdmin =
            By.xpath("//span[text()='Admin']");

    private By botonAdd =
            By.xpath("//button[normalize-space()='Add']");

    // =========================
    // EMPLOYEE
    // =========================

    private By empleadoInput =
            By.xpath("//input[@placeholder='Type for hints...']");

    private By opcionesAutocomplete =
            By.xpath("//div[@role='listbox']//div[@role='option']");

    // =========================
    // USERNAME
    // =========================

    private By usernameInput =
            By.xpath("//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input");

    // =========================
    // PASSWORDS
    // =========================

    private By passwordInput =
            By.xpath("(//input[@type='password'])[1]");

    private By confirmPasswordInput =
            By.xpath("(//input[@type='password'])[2]");

    // =========================
    // SAVE
    // =========================

    private By botonSave =
            By.xpath("//button[@type='submit']");

    // =========================
    // TABLA
    // =========================

    private By tablaUsuarios =
            By.cssSelector(".oxd-table-body");

    public AdminPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(40)
        );
    }

    // =====================================================
    // LOGIN
    // =====================================================

    public void iniciarSesionAdmin() {

        driver.get(
                "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login"
        );

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(campoUsuario)
        ).sendKeys("Admin");

        driver.findElement(campoContrasena)
                .sendKeys("admin123");

        driver.findElement(botonLogin)
                .click();

        wait.until(
                ExpectedConditions.urlContains("dashboard")
        );
    }

    // =====================================================
    // CLICK ADMIN
    // =====================================================

    public void clickAdmin() {

        wait.until(
                ExpectedConditions.elementToBeClickable(menuAdmin)
        ).click();
    }

    // =====================================================
    // VALIDAR ADMIN
    // =====================================================

    public boolean estaEnAdmin() {

        wait.until(
                ExpectedConditions.urlContains("admin")
        );

        return driver.getCurrentUrl().contains("admin");
    }

    // =====================================================
    // CLICK ADD
    // =====================================================

    public void clickAdd() {

        wait.until(
                ExpectedConditions.urlContains("admin")
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(botonAdd)
        ).click();
    }

    // =====================================================
    // COMPLETAR FORMULARIO
    // =====================================================

    public void completarFormularioUsuario() {

        By userRoleDropdown =
                By.xpath(
                        "//label[text()='User Role']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]"
                );

        WebElement userRole = wait.until(
                ExpectedConditions.elementToBeClickable(userRoleDropdown)
        );

        userRole.click();

        By opcionAdmin =
                By.xpath("//div[@role='option']//span[normalize-space()='Admin']");

        wait.until(
                ExpectedConditions.elementToBeClickable(opcionAdmin)
        ).click();

        WebElement empInput = wait.until(
                ExpectedConditions.elementToBeClickable(empleadoInput)
        );

        empInput.click();

        empInput.clear();

        String empleado = "Jobin";

        for (char c : empleado.toCharArray()) {

            empInput.sendKeys(String.valueOf(c));

            try {

                Thread.sleep(300);

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        wait.until(driver -> {

            List<WebElement> opciones =
                    driver.findElements(opcionesAutocomplete);

            if (opciones.isEmpty()) {
                return false;
            }

            String texto =
                    opciones.get(0).getText().trim();

            return !texto.equalsIgnoreCase("Searching....")
                    && !texto.isEmpty();
        });

        List<WebElement> opciones =
                wait.until(
                        ExpectedConditions.visibilityOfAllElementsLocatedBy(
                                opcionesAutocomplete
                        )
                );

        boolean encontrado = false;

        for (WebElement opcion : opciones) {

            try {

                String texto =
                        opcion.getText().trim();

                if (texto.contains("Jobin Mathew Sam")) {

                    wait.until(
                            ExpectedConditions.elementToBeClickable(opcion)
                    ).click();

                    encontrado = true;

                    break;
                }

            } catch (StaleElementReferenceException e) {

                System.out.println("STALE ELEMENT DETECTADO");
            }
        }

        if (!encontrado) {

            throw new RuntimeException(
                    "NO SE ENCONTRO EL EMPLEADO EN EL AUTOCOMPLETE"
            );
        }

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//div[@role='listbox']")
                )
        );

        By statusDropdown =
                By.xpath(
                        "//label[text()='Status']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]"
                );

        WebElement statusElement = wait.until(
                ExpectedConditions.elementToBeClickable(statusDropdown)
        );

        statusElement.click();

        By opcionEnabled =
                By.xpath("//div[@role='option']//span[normalize-space()='Enabled']");

        WebElement enabledOption = wait.until(
                ExpectedConditions.visibilityOfElementLocated(opcionEnabled)
        );

        enabledOption.click();

        String username =
                "user" + System.currentTimeMillis();

        WebElement userField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(usernameInput)
        );

        userField.clear();

        userField.sendKeys(username);

        WebElement passField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordInput)
        );

        passField.clear();

        passField.sendKeys("Admin123!");

        WebElement confirmField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(confirmPasswordInput)
        );

        confirmField.clear();

        confirmField.sendKeys("Admin123!");

        confirmField.sendKeys(Keys.TAB);

        try {

            Thread.sleep(2000);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    // =====================================================
// FORMULARIO USUARIO DUPLICADO
// =====================================================

public void completarFormularioUsuarioDuplicado(String username) {

    // =================================================
    // USER ROLE
    // =================================================

    By userRoleDropdown =
            By.xpath(
                    "//label[text()='User Role']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]"
            );

    wait.until(
            ExpectedConditions.elementToBeClickable(userRoleDropdown)
    ).click();

    By opcionAdmin =
            By.xpath("//div[@role='option']//span[normalize-space()='Admin']");

    wait.until(
            ExpectedConditions.elementToBeClickable(opcionAdmin)
    ).click();

    // =================================================
    // EMPLOYEE NAME
    // =================================================

    WebElement empInput = wait.until(
            ExpectedConditions.elementToBeClickable(empleadoInput)
    );

    empInput.click();

    empInput.clear();

    String empleado = "Jobin";

    for (char c : empleado.toCharArray()) {

        empInput.sendKeys(String.valueOf(c));

        try {

            Thread.sleep(300);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    wait.until(driver -> {

        List<WebElement> opciones =
                driver.findElements(opcionesAutocomplete);

        if (opciones.isEmpty()) {
            return false;
        }

        String texto =
                opciones.get(0).getText().trim();

        return !texto.equalsIgnoreCase("Searching....")
                && !texto.isEmpty();
    });

    List<WebElement> opciones =
            wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(
                            opcionesAutocomplete
                    )
            );

    boolean encontrado = false;

    for (WebElement opcion : opciones) {

        try {

            String texto =
                    opcion.getText().trim();

            if (texto.contains("Jobin Mathew Sam")) {

                wait.until(
                        ExpectedConditions.elementToBeClickable(opcion)
                ).click();

                encontrado = true;

                break;
            }

        } catch (StaleElementReferenceException e) {

            System.out.println("STALE ELEMENT DETECTADO");
        }
    }

    if (!encontrado) {

        throw new RuntimeException(
                "NO SE ENCONTRO EL EMPLEADO"
        );
    }

    wait.until(
            ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//div[@role='listbox']")
            )
    );

    // =================================================
    // STATUS
    // =================================================

    By statusDropdown =
            By.xpath(
                    "//label[text()='Status']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]"
            );

    wait.until(
            ExpectedConditions.elementToBeClickable(statusDropdown)
    ).click();

    By opcionEnabled =
            By.xpath("//div[@role='option']//span[normalize-space()='Enabled']");

    wait.until(
            ExpectedConditions.elementToBeClickable(opcionEnabled)
    ).click();

    // =================================================
    // USERNAME DUPLICADO
    // =================================================

    WebElement userField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(usernameInput)
    );

    userField.clear();
    userField.sendKeys(username);
    userField.sendKeys(Keys.TAB);
    try { Thread.sleep(1500); } catch (Exception e) { e.printStackTrace(); }

    // =================================================
    // PASSWORD
    // =================================================

    WebElement passField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(passwordInput)
    );

    passField.clear();

    passField.sendKeys("Admin123!");

    // =================================================
    // CONFIRM PASSWORD
    // =================================================

    WebElement confirmField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(confirmPasswordInput)
    );

    confirmField.clear();

    confirmField.sendKeys("Admin123!");

    confirmField.sendKeys(Keys.TAB);

    // PEQUEÑA ESPERA PARA AJAX
    try {

        Thread.sleep(3000);

    } catch (Exception e) {

        e.printStackTrace();
    }
}
    // =====================================================
    // SAVE
    // =====================================================

    public void clickSave() {

        WebElement boton = wait.until(
                ExpectedConditions.elementToBeClickable(botonSave)
        );

        boton.click();
    }

    // =====================================================
    // VALIDAR CREACION
    // =====================================================

    public boolean usuarioCreado() {

        try {

            wait.until(
                    ExpectedConditions.urlContains("viewSystemUsers")
            );

            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(tablaUsuarios)
            );

            return true;

        } catch (Exception e) {

            List<WebElement> errores =
                    driver.findElements(
                            By.cssSelector(".oxd-input-field-error-message")
                    );

            for (WebElement err : errores) {

                System.out.println(
                        "VALIDACION: " + err.getText()
                );
            }

            return false;
        }
    }

    /// =====================================================
    // VALIDAR USUARIO DUPLICADO
    // =====================================================

public boolean usuarioDuplicado() {
    try {
        // Esperar explícitamente que aparezca el mensaje
        By mensajeError = By.cssSelector(".oxd-input-field-error-message");

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(mensajeError)
        );

        List<WebElement> errores = driver.findElements(mensajeError);

        for (WebElement err : errores) {
            System.out.println("TEXTO ERROR: [" + err.getText() + "]");
            if (err.getText().toLowerCase().contains("already exists")) {
                return true;
            }
        }

        return false;

    } catch (Exception e) {
        System.out.println("EXCEPCION: " + e.getMessage());
        return false;
    }
}

public void completarFormularioDesdeExcel(
        String userRole, String employeeName, String status,
        String username, String password, String confirmPassword) {

    // USER ROLE
    By userRoleDropdown = By.xpath(
        "//label[text()='User Role']/ancestor::div[contains(@class,'oxd-input-group')]" +
        "//div[contains(@class,'oxd-select-text')]"
    );
    wait.until(ExpectedConditions.elementToBeClickable(userRoleDropdown)).click();

    By opcionRole = By.xpath(
        "//div[@role='option']//span[normalize-space()='" + userRole + "']"
    );
    wait.until(ExpectedConditions.elementToBeClickable(opcionRole)).click();

    // EMPLOYEE NAME — escribe el primer nombre para el autocomplete
    WebElement empInput = wait.until(
        ExpectedConditions.elementToBeClickable(empleadoInput)
    );
    empInput.click();
    empInput.clear();

    String hint = employeeName.split(" ")[0]; 
    for (char c : hint.toCharArray()) {
        empInput.sendKeys(String.valueOf(c));
        try { Thread.sleep(300); } catch (Exception ignored) {}
    }

    // Esperar que cargue el autocomplete
    wait.until(driver -> {
        List<WebElement> ops = driver.findElements(opcionesAutocomplete);
        if (ops.isEmpty()) return false;
        String txt = ops.get(0).getText().trim();
        return !txt.equalsIgnoreCase("Searching....") && !txt.isEmpty();
    });

    // Seleccionar la opción que coincida con el nombre completo del Excel
    List<WebElement> opciones = wait.until(
        ExpectedConditions.visibilityOfAllElementsLocatedBy(opcionesAutocomplete)
    );
    boolean encontrado = false;
    for (WebElement opcion : opciones) {
        try {
            if (opcion.getText().trim().contains(employeeName)) {
                wait.until(ExpectedConditions.elementToBeClickable(opcion)).click();
                encontrado = true;
                break;
            }
        } catch (StaleElementReferenceException e) {
            System.out.println("STALE ELEMENT");
        }
    }
    if (!encontrado) throw new RuntimeException("Empleado no encontrado: " + employeeName);

    wait.until(ExpectedConditions.invisibilityOfElementLocated(
        By.xpath("//div[@role='listbox']")
    ));

    // STATUS
    By statusDropdown = By.xpath(
        "//label[text()='Status']/ancestor::div[contains(@class,'oxd-input-group')]" +
        "//div[contains(@class,'oxd-select-text')]"
    );
    wait.until(ExpectedConditions.elementToBeClickable(statusDropdown)).click();

    By opcionStatus = By.xpath(
        "//div[@role='option']//span[normalize-space()='" + status + "']"
    );
    wait.until(ExpectedConditions.elementToBeClickable(opcionStatus)).click();

    // USERNAME
    WebElement userField = wait.until(
        ExpectedConditions.visibilityOfElementLocated(usernameInput)
    );
    userField.clear();
    userField.sendKeys(username);

    // PASSWORD
    WebElement passField = wait.until(
        ExpectedConditions.visibilityOfElementLocated(passwordInput)
    );
    passField.clear();
    passField.sendKeys(password);

    // CONFIRM PASSWORD
    WebElement confirmField = wait.until(
        ExpectedConditions.visibilityOfElementLocated(confirmPasswordInput)
    );
    confirmField.clear();
    confirmField.sendKeys(confirmPassword);
    confirmField.sendKeys(Keys.TAB);

    try { Thread.sleep(2000); } catch (Exception ignored) {}
}
}