package selenium.db;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumUtils {
    
    private static final String PREFIX = "selenium-util.";
    static Properties prop;
    static {
        try {
            InputStream is = SeleniumUtils.class.getResourceAsStream("selenium-util.properties");
            if( is != null )
                prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            
        }
    }
    public static RemoteWebDriver getDriver() {
        return getDriver(getProperty("webdriver.default"));
    }
    
    public static RemoteWebDriver getDriver(String name) {
        if( "chrome".equals(name) ) {
            return getChromeDriver();
        }
        if( "edge".equals(name) ) {
            return getChromeDriver();
        }
        
        throw new IllegalArgumentException("");
    }

    public static RemoteWebDriver getChromeDriver() {
        String driverPath = getProperty("webdriver.chrome.driver");
        System.setProperty("webdriver.chrome.driver", driverPath);

        int w = getPropertyInt("width");
        int h = getPropertyInt("height");

        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        RemoteWebDriver driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(w, h));
        return driver;
    }

    public static RemoteWebDriver getEdgeDriver() {
        String driverPath = getProperty("webdriver.edge.driver");
        System.setProperty("webdriver.edge.driver", driverPath);

        int w = getPropertyInt("width");
        int h = getPropertyInt("height");

        EdgeOptions options = new EdgeOptions();
        options.setHeadless(true);
        RemoteWebDriver driver = new EdgeDriver(options);
        driver.manage().window().setSize(new Dimension(w, h));
        return driver;
    }
    
    private static String getProperty(String key) {
        String val = System.getProperty(key);
        if(val == null) {
            return prop.getProperty(PREFIX + key);
        }
        return val;
    }
    
    private static int getPropertyInt(String key) {
        return Integer.parseInt(getProperty(key));
    }
}

