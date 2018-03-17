/**
 * Created by Abuthahir AH on 06-03-2018.
 */
import java.io.File;import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/*
* This Utility class for taking the screen shots for failed cases and saved it in the directory
*/
public class Utility
{
    public static void captureScreenshot(WebDriver driver,String screenshotName)
    {
        try
        {
            TakesScreenshot ts=(TakesScreenshot)driver;
            File source=ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File(System.getProperty("user.dir")+"\\target\\Screenshot\\"+screenshotName+".png"));
            System.out.println("Screenshot taken");
        }
        catch (Exception e)
        {
            System.out.println("Exception while taking screenshot "+e.getMessage());
        }
    }
}
