package selenium.db;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.sql.DataSource;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

public class EvidenceReport {
    private static final String TEST_CONTEXT_DATE = "test-context-date";
    private File basePathFile;
    
    private int index = 0;

    public EvidenceReport() {
        String testContextDate = initTestContextDate();
        String basePath = SeleniumUtils.getProperty("screenshot.basepath");
        basePathFile = new File(basePath, testContextDate);
        basePathFile.mkdirs();
    }

    private String initTestContextDate() {
        String testContextDate = System.getProperty(TEST_CONTEXT_DATE);
        if(testContextDate == null) {
            testContextDate = getDateString();
            System.setProperty(TEST_CONTEXT_DATE, testContextDate);
        }
        return testContextDate;
    }

    public EvidenceReport(String name) {
        String testContextDate = initTestContextDate();
        String basePath = SeleniumUtils.getProperty("screenshot.basepath");
        basePathFile = new File(new File(basePath, testContextDate), name);
        basePathFile.mkdirs();
    }

    public void takeScreenshot(TakesScreenshot driver) {
        String formatedDate = getDateString();
        String childPath = String.format("%s.png", formatedDate);
        
        takeScreenshot(driver, String.format("%03d-%s.png", index++, childPath));
    }
    
    public void takeScreenshot(TakesScreenshot driver, String childPath) {
        try {
            byte[] bytes = driver.getScreenshotAs(OutputType.BYTES);

            File childPathFile = new File(basePathFile, childPath);

            try (FileOutputStream os = new FileOutputStream(childPathFile);) {
                os.write(bytes);
                os.flush();
            }

        } catch (WebDriverException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public void dumpTable(DataSource dataSource ,String name, String sql) {
        try {
            QueryRunner run = new QueryRunner( dataSource );
            run.query(sql, new ResultSetToCSV(name) );
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    private String getDateString() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd-HHmmss");
        String formatedDate = sdf.format(date);
        return formatedDate;
    }
}
