package utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
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

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        options.addArguments("--force-device-scale-factor=1");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        try { Thread.sleep(500); } catch (Exception ignored) {}
    }

    @After(order = 1)   
public void capturarEvidencia(Scenario scenario) {
    if (driver == null) return;
    try {
        Thread.sleep(3000);
        esperarPaginaEstable();
        tomarScreenshot(scenario);
    } catch (Exception e) {
        System.out.println("Error capturando evidencia: " + e.getMessage());
    }
}

    @After(order = 0)  
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
                    .equals("complete")
            );
        } catch (Exception ignored) {}

        try {
            wait.until(webDriver -> {
                Object result = ((JavascriptExecutor) webDriver)
                    .executeScript(
                        "return (typeof jQuery !== 'undefined') " +
                        "? jQuery.active === 0 : true"
                    );
                return Boolean.TRUE.equals(result);
            });
        } catch (Exception ignored) {}

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                org.openqa.selenium.By.className("oxd-loading-spinner")
            ));
        } catch (Exception ignored) {}

        try {
 
            Thread.sleep(600);
        } catch (Exception ignored) {}
    }
    private void tomarScreenshot(Scenario scenario) {
        try {
            String timestamp   = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String nombreLimpo = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
            String estado      = scenario.isFailed() ? "FAIL" : "PASS";

            File carpeta = new File("evidencias");
            if (!carpeta.exists()) carpeta.mkdirs();

            File src  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File("evidencias/" + estado + "_" + nombreLimpo + "_" + timestamp + ".png");
            FileUtils.copyFile(src, dest);

            byte[] bytes = FileUtils.readFileToByteArray(dest);
            scenario.attach(bytes, "image/png", estado + " — " + scenario.getName());

            System.out.println("✔ Screenshot: " + dest.getPath());

        } catch (Exception e) {
            System.out.println("✘ Screenshot fallido: " + e.getMessage());
        }
    }
}