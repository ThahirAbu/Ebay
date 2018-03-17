import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import java.io.File;
import java.util.HashMap;
import static java.lang.Thread.sleep;

/**
 * Extending the Object Repository class for accessing the variables.
 */

public class Search extends ObjectRepository {

    private File location = new File("src");
    private File FileLocation = new File(location,"Ebay_TestData.xlsx");

    @Test(priority = 1)
    public void search() throws InterruptedException {
        int i =1;
        /*Declaring Excel sheet name and assigning the input values to input fields*/
        /*This for loop will based on number of rows present in the Product Search sheet*/
        /*Here searching the products, applying the Sorting*/
        driver.findElement(homesearchfield).click();
        for(HashMap h:ObjectRepository.data(FileLocation,"ProductSearch")) {
            searchProd(h.get("Search").toString());
            sleep(5000);
            swipingVerticalBtoT();
            sleep(5000);
//            driver.pressKeyCode(AndroidKeyCode.BACK);
            if (i==1)
            {
                driver.findElement(menusearch).click();
            }
            else {
                Landing();
            }
            i=i+1;
        }
    }

}
