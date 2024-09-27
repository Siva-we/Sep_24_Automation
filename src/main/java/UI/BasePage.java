package UI;

import IOUtility.ExcelUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class BasePage {

   protected WebDriver driver = null;

   protected  void launchApplication(){

   }

   public static void main(String[] args) throws IOException, InvalidFormatException {
      ExcelUtil excelUtil = new ExcelUtil();
      excelUtil.readExcelFile();
   }
}
