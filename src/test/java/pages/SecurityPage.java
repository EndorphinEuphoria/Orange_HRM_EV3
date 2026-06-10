package pages;

import org.openqa.selenium.WebDriver;

public class SecurityPage {

    private WebDriver driver;

    public SecurityPage(WebDriver driver) {
        this.driver = driver;
    }

    public void abrirDashboardSinLogin() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
    }

    public String obtenerUrlActual() {
        return driver.getCurrentUrl();
    }
}