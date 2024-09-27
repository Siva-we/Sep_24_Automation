package IOUtility;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.format.CellFormatter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Iterator;

public class ExcelUtil {

    public List<UserData> readExcelFile() throws IOException, InvalidFormatException {

        List<UserData> userData = new ArrayList<UserData>();

        File file = new File("C:\\Users\\e008643\\Documents\\ramu\\SKAND Technologies\\QA Samples\\TestData\\TestData.xlsx");

        XSSFWorkbook wb = new XSSFWorkbook(file);
        XSSFSheet sheet = wb.getSheet("UserData");

        Iterator<Row> rows = sheet.rowIterator();

        while(rows.hasNext()) {
            Row eachRow = rows.next();
            Iterator<Cell> cells = eachRow.cellIterator();
            int cell = 0;
            UserData userData1 = new UserData();

            while(cells.hasNext()) {
                Cell eachCell = cells.next();
                //eachCell.getNumericCellValue()
                String cellValue = new DataFormatter().formatCellValue(eachCell);

                if(cell == 0){
                    userData1.userName = cellValue;
                    cell++;
                } else {
                    userData1.passwrod = cellValue;
                }
            }
            userData.add(userData1);
        }

        return  userData;
    }
}
