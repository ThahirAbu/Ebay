import android.view.inputmethod.EditorInfo;
import io.appium.java_client.android.AndroidKeyCode;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Extending the Object Repository class for accessing the variables.
 */
public class Login extends ObjectRepository {
    /*Declaring Excel sheet file location and name*/
    private File location = new File("src");
    private File FileLocation = new File(location,"Ebay_TestData.xlsx");

    @Test(priority = 1)
    public void login_Page(){
        click_signin();
        /*Declaring Excel sheet name and assigning the input values to input fields*/
        for(HashMap h:ObjectRepository.data(FileLocation,"Login"))
        {
            System.out.println(h.keySet());
            System.out.println(h.values());
            setUsername(h.get("UserID").toString());
            driver.pressKeyCode(EditorInfo.IME_ACTION_NEXT);
            setPassword (h.get("PassCode").toString());
            click_signin();
        }
        driver.findElement(loginskip).click();
    }
}
