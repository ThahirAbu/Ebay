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
    public Checkout() throws InterruptedException
    {
        driver.findElement(homesearchfield).click();
        File location = new File("src");
        File FileLocation = new File(location, "Ebay_TestData.xlsx");
        for(HashMap h:ObjectRepository.data(FileLocation,"Checkout"))
        {
            searchProd(h.get("iTems").toString());
            cart(h.get("Qty").toString());
        }
    }
}
