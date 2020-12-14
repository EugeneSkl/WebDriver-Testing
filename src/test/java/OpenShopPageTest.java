import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.NoSuchElementException;

public class OpenShopPageTest {
    private WebDriver driver;
    private Wait<WebDriver> wait;

    public WebElement getElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void clickOnLink(WebElement link) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", link);
    }

    @BeforeTest (alwaysRun = true)
    public void browserSetup() {
        System.setProperty("webdriver.chrome.driver",".\\src\\test\\resources\\chromedriver.exe");

        driver = new ChromeDriver();
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofSeconds(3))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    @Test
    public void openSaleShopPageTest() {
        driver.get("https://pudra.by/");

        WebElement linkToPromotions = getElement(By.xpath("//a[@class='i-gift'][contains(text(), 'АКЦИИ')]"));
        Assert.assertTrue(linkToPromotions.getAttribute("text").contains("АКЦИИ"));

        WebElement linkToSales = getElement(By.xpath("//a[@class='i-sale'][contains(text(), 'СКИДКИ')]"));
        Assert.assertTrue(linkToSales.getAttribute("text").contains("СКИДКИ"));

        WebElement salesCategory = getElement(By.xpath("//a[@class='nav-menu-item'][contains(text(), 'ТАЮЩИЙ КАТАЛОГ')]"));
        Assert.assertTrue(salesCategory.getAttribute("text").contains("ТАЮЩИЙ КАТАЛОГ"));
    }

    @AfterTest (alwaysRun = true)
    public void browserQuit() {
        driver.quit();
        driver = null;
    }
}
