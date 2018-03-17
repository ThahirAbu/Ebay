import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Abuthahir AH on 06-03-2018.
 */
public class Checkout extends ObjectRepository
{
    public List<HashMap<String, String>> datamap;

    @Test(priority = 1)
    public void Checkout() throws InterruptedException
    {
        /*Declaring Excel sheet file location and name*/
        driver.findElement(homesearchfield).click();
        File location = new File("src");
        File FileLocation = new File(location, "Ebay_TestData.xlsx");

        /*Declaring Excel sheet name and assigning the input values to input fields*/
        /*Searching the product and navigating to Orderpad screen*/

        for(HashMap h:ObjectRepository.data(FileLocation,"Checkout"))
        {
            searchProd(h.get("iTems").toString());
            cart(h.get("Qty").toString());
        }
    }
}
