package utils;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class Hooks {

    private static WebDriver driver;
    private int contadorPasos; 

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--force-device-scale-factor=1");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        contadorPasos = 0;
    }

    @AfterStep
    public void capturarEvidenciaPorPaso(Scenario scenario) {
        if (driver == null) return;
        try {
            contadorPasos++;
            esperarPaginaEstable();
            tomarScreenshotPorPaso(scenario, contadorPasos);
        } catch (Exception e) {
            System.out.println("Error capturando evidencia del paso: " + e.getMessage());
        }
    }

    @After
    public void cerrarDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(WebDriver d) {
        driver = d;
    }

    private void esperarPaginaEstable() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            wait.until(webDriver ->
                    ((JavascriptExecutor) webDriver)
                            .executeScript("return document.readyState")
                            .equals("complete"));
        } catch (Exception ignored) {}
        try {
            wait.until(webDriver -> {
                Object result = ((JavascriptExecutor) webDriver)
                        .executeScript("return (typeof jQuery !== 'undefined') ? jQuery.active === 0 : true");
                return Boolean.TRUE.equals(result);
            });
        } catch (Exception ignored) {}
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.className("oxd-loading-spinner")));
        } catch (Exception ignored) {}
    }

    private void tomarScreenshotPorPaso(Scenario scenario, int pasoNum) {
        try {
            String timestamp   = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String nombreLimpio = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
            String estado      = scenario.isFailed() ? "FAIL" : "PASS";

            File carpeta = new File("evidencias");
            if (!carpeta.exists()) carpeta.mkdirs();

            String nombreArchivo = String.format("evidencias/%s_%s_Paso_%d_%s.png", 
                    estado, nombreLimpio, pasoNum, timestamp);

            File src  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(nombreArchivo);
            FileUtils.copyFile(src, dest);

            byte[] bytes = FileUtils.readFileToByteArray(dest);
            scenario.attach(bytes, "image/png", "Paso " + pasoNum + " — " + estado);
            System.out.println("✔ Captura guardada: " + dest.getPath());
        } catch (Exception e) {
            System.out.println("✘ Error al guardar captura de paso: " + e.getMessage());
        }
    }
}