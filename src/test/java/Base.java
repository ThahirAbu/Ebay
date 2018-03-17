import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by Abuthahir AH on 05-03-2018.
 */
public class Base  {

    /*Declaring the Desired Capabilities*/
    static AndroidDriver driver;
    private DesiredCapabilities caps = new DesiredCapabilities();
    static ExtentReports extent;
    static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
    static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
    String ScreenshotPath = System.getProperty("user.dir")+"\\target\\Screenshot\\";


@BeforeTest
    public void setup(){

        caps.setCapability(MobileCapabilityType.DEVICE_NAME,"Android device");
        try{
            serverConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Declaring APK file location and Appium driver setup*/
    private void serverConnection() throws MalformedURLException {

        File location = new File("src");
        File AppLocation = new File(location,"com.ebay.mobile_v5.17.0.18-117_Android-5.0.apk");
        caps.setCapability(MobileCapabilityType.APP, AppLocation.getAbsolutePath());
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
    }

    /*At once all test cases was executed successfully, this method will create the HTML report automatically*/
    @BeforeSuite
    public void beforeSuite() throws IOException, InterruptedException {
        Thread.sleep(20000L);
        extent = ExtentManager.createInstance(System.getProperty("user.dir") + "\\Reports\\" + "Test.html");
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "\\Reports\\" + "Test.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        htmlReporter.config().setReportName("EBay Test Report");
    }

    @BeforeClass
    public synchronized void beforeClass() {
        String class_name = getClass().getName();
        String classname = class_name.replace("_", " ");
        ExtentTest parent = extent.createTest(classname);
        parentTest.set(parent);
    }

    @BeforeMethod
    public synchronized void beforeMethod(Method method) {

        String method_name = method.getName();
        String methodname = method_name.replace("_", " ");
        ExtentTest child = parentTest.get().createNode(methodname);
        test.set(child);
    }

    /*When Test case was filed, this method will capture the screen shots and save it under Screen shot folder*/
    @AfterMethod
    public synchronized void afterMethod(ITestResult result)throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            Utility.captureScreenshot(driver,result.getName());
            test.get().log(Status.FAIL, MarkupHelper.createLabel(result.getName()
                    + " Test case Failed due to below issues:", ExtentColor.RED)).addScreenCaptureFromPath(ScreenshotPath+result.getName()+".png");
            test.get().fail(result.getThrowable());
        } else if
                (result.getStatus() == ITestResult.SKIP){
            test.get().log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " Test case Skipped due to below reason : ", ExtentColor.ORANGE));
            test.get().skip(result.getThrowable());
        }
        else
            test.get().log(Status.PASS,MarkupHelper.createLabel(result.getName()+" Test case Passed",ExtentColor.GREEN));
    }

    /*Closing the session object*/
    @AfterSuite
    public synchronized void after_suite() throws Exception {
        extent.flush();
    }

}