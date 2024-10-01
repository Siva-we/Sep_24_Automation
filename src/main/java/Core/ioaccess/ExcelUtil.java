package Core.ioaccess;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.*;

public class ExcelUtil {

    private String projectPath = System.getProperty("user.dir");
    protected HashMap<String, String> excelTestData = null;

    // loading data from the excel file
    public HashMap<String, String> loadTestData(String excelName, String path, String sheetName) {
        try {

            FileInputStream fs = new FileInputStream(projectPath +"\\"+ path+ "\\"+excelName);
            XSSFWorkbook wb = new XSSFWorkbook(fs);
            XSSFSheet sheet = wb.getSheet(sheetName);
            Iterator<Row> rows = sheet.iterator();

            excelTestData = new HashMap<String, String>();
            List<String> keys = new ArrayList<String>();
            List<String> values = new ArrayList<String>();

            boolean isHeader = true;

            while (rows.hasNext()) {
                XSSFRow eachRow = (XSSFRow) rows.next();
                Iterator<Cell> cells = eachRow.iterator();
                while (cells.hasNext()) {
                    XSSFCell eachCell = (XSSFCell) cells.next();
                    if (isHeader) {
                        keys.add(readCellValue(eachCell));
                    } else {
                        values.add(readCellValue(eachCell));
                    }
                }
                isHeader = false;
            }

            for (int index = 0; index < keys.size(); index++) {
                excelTestData.put(keys.get(index), values.get(index));
            }

        } catch (Exception ex) {
            System.out.println("Not able to read core.test data from excel");
        }
        return  excelTestData;
    }

    public String readCellValue(Cell cell) {
        return new DataFormatter().formatCellValue(cell);
    }
}
