package selenium.db;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

class SeleniumSampleTest {

    @Test
    void test() throws InterruptedException {
        
        EvidenceReport report = new EvidenceReport("test");
        // Optional. If not specified, WebDriver searches the PATH for chromedriver.
        RemoteWebDriver driver = SeleniumUtils.getDriver();
        driver.get("http://www.google.com/");

        WebElement searchBox = driver.findElement(By.name("q"));

        searchBox.sendKeys("ChromeDriver");
        
        report.takeScreenshot(driver);
        
        searchBox.submit();

        report.takeScreenshot(driver);
        

        driver.quit();
        
    }


}
