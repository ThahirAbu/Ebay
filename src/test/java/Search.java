import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import java.io.File;
import java.util.HashMap;
import static java.lang.Thread.sleep;

/**
 * Created by Abuthahir AH on 05-03-2018.
 */
public class Search extends ObjectRepository {

    private File location = new File("src");
    private File FileLocation = new File(location,"Ebay_TestData.xlsx");

    @Test(priority = 1)
    public void search() throws InterruptedException {
        int i =1;
        driver.findElement(homesearchfield).click();
        for(HashMap h:ObjectRepository.data(FileLocation,"ProductSearch")) {
            searchProd(h.get("Search").toString());
            sleep(5000);
            swipingVerticalBtoT();
            driver.pressKeyCode(AndroidKeyCode.BACK);
            if(i==1)
            {
                driver.findElement(By.id("com.ebay.mobile:id/primary_toolbar")).click();
            }
            else
            {
                Filter();
                Landing();
            }
            i = i+1;
        }
    }

}
