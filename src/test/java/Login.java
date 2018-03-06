import android.view.inputmethod.EditorInfo;
import io.appium.java_client.android.AndroidKeyCode;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Abuthahir AH on 05-03-2018.
 */
public class Login extends ObjectRepository {

    private File location = new File("src");
    private File FileLocation = new File(location,"Ebay_TestData.xlsx");

    @Test(priority = 1)
    public void login_Page(){
        click_signin();
        for(HashMap h:ObjectRepository.data(FileLocation,"Login"))
        {
            System.out.println(h.keySet());
            System.out.println(h.values());
            setUsername(h.get("UserID").toString());
            driver.pressKeyCode(EditorInfo.IME_ACTION_NEXT);
            setPassword (h.get("PassCode").toString());
            click_signin();
        }
    }
}
