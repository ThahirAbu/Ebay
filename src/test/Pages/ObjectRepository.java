
import io.appium.java_client.android.AndroidKeyCode;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * This is an Object Repository Class, where we created the variables, action keys and common functions etc.
 */
public class ObjectRepository extends Base {

/*Creating alais variables*/
    public By signin, username,password, homesearchfield, searchField,menusearchtxt,menusearch, prod1, filterelement,homemenu,
            landing,buy,Qty,Preview,loginskip, Sortbtn,leftmenu;

/*Assigning the resource elements to alais variables*/
    ObjectRepository() {
        signin = By.id("com.ebay.mobile:id/button_sign_in");
        username = By.className("android.widget.EditText");
        password = By.className("android.widget.EditText");
        homesearchfield = By.id("com.ebay.mobile:id/search_box");
        menusearchtxt = By.id ("com.ebay.mobile:id/search_src_text");
        searchField = By.id("com.ebay.mobile:id/search_src_text");
        prod1 = By.id("com.ebay.mobile:id/cell_collection_item");
        Sortbtn=By.id("com.ebay.mobile:id/button_sort");
        filterelement = By.id("com.ebay.mobile:id/button_filter_subelement");
        homemenu = By.id ("com.ebay.mobile:id/home");
        landing = By.id("com.ebay.mobile:id/design_menu_item_text");
        buy = By.id("com.ebay.mobile:id/button_bin");
        Qty = By.id("android:id/numberpicker_input");
        Preview = By.id("com.ebay.mobile:id/take_action");
        loginskip = By.id("com.ebay.mobile:id/button_google_deny");
        menusearch=By.id("com.ebay.mobile:id/menu_search");
        leftmenu=By.id("com.ebay.mobile:id/primary_toolbar");
    }

/*Creating Signin action */
    void click_signin() {
        driver.findElement(signin).click();
    }

/*Here using Xpath method to identify the Username field and get the input from external sourcee*/
    void setUsername(String uName) {
        driver.findElementByXPath("//*[@text = 'Email or username']").sendKeys(uName);
    }

/*Here using Xpath method to identify the Password field and get the input from external source*/
    void setPassword(String pwd) {
        driver.findElementByXPath("//*[@text = 'Password']").sendKeys(pwd);
    }

/*Searching the products by getting the input from the external source*/
    void searchProd(String prod) throws InterruptedException {
        driver.findElement(searchField).sendKeys(prod);
        driver.pressKeyCode(AndroidKeyCode.ENTER);
        sleep(5000);
        Filter();
        driver.findElement(prod1).click();
    }

/*Using List Element parameters to perform Sorting the Products*/
    void Filter()  {
        driver.findElement(Sortbtn).click();
        int x;
        List<WebElement> sort = driver.findElements(filterelement);
        x = sort.size();
        sort.get(x-3).click();
    }

/*Home screen navigation*/
    void Landing()
    {
        driver.findElement(homemenu).click();
        driver.findElement(landing).click();
    }

/*Cart screen navigation and passing the values from the external source*/
    void cart(String quantity)
    {
        driver.findElement(buy).click();
        driver.findElement(Qty).sendKeys(quantity);
        driver.pressKeyCode(AndroidKeyCode.ENTER);
        driver.findElement(Preview).click();
    }

/*This function take entire screen size and perform scrolling action from Bottom to Top middle of the screen*/
    void swipingVerticalBtoT() throws InterruptedException
    {
        sleep(2000);
        Dimension size = driver.manage().window().getSize();
        int starty = (int) (size.height * 0.80);
        int endy = (int) (size.height * 0.20);
        int startx = size.width / 2;
        driver.swipe(startx, starty, startx, endy, 3000);
        sleep(2000);
    }

/*For reading each row inputs from Excel sheet.*/
    public static HashMap<String, String> storeValues = new HashMap();

        static List<HashMap<String,String>> data(File filepath, String sheetName)
        {
            /*Create an ArrayList to store the data read from excel sheet.*/
            List<HashMap<String,String>> mydata = new ArrayList<HashMap<String, String>>();
            try
            {
                /*Create an excel workbook from the file system*/
                FileInputStream fs = new FileInputStream(filepath);
                XSSFWorkbook workbook = new XSSFWorkbook(fs);
                XSSFSheet sheet = workbook.getSheet(sheetName);
                Row HeaderRow = sheet.getRow(0);
                for(int i=1;i<sheet.getPhysicalNumberOfRows();i++)
                {
                    Row currentRow = sheet.getRow(i);
                    HashMap<String,String> currentHash = new HashMap<String,String>();
                    for(int j=1;j<currentRow.getLastCellNum();j++)
                    {
                        Cell currentCell = currentRow.getCell(j);
                        if(currentCell !=null) {
                            String cellvalue = readcell(currentCell);
                            currentHash.put(HeaderRow.getCell(j).getStringCellValue(), cellvalue);
                        }else {
                            currentHash.put(HeaderRow.getCell(j).getStringCellValue(), "");
                        }
                    }
                    mydata.add(currentHash);
                }
                fs.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return mydata;

        }

    private static String readcell(Cell currentcell){
        Object cellvalue = null;
        switch (currentcell.getCellType()){

            case Cell.CELL_TYPE_STRING:
                cellvalue = currentcell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                cellvalue = NumberToTextConverter.toText(currentcell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                break;
        }
        return String.valueOf(cellvalue);
    }

}
