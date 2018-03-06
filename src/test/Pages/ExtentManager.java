import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * Created by Abuthahir AH on 06-03-2018.
 */
public class ExtentManager {
    public static ExtentReports extent;

    public static ExtentReports getInstance(){
        return extent;
    }

    public static ExtentReports createInstance(String filename){
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filename);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle(filename);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(filename);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        return extent;
    }

}