package test01;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ExcelUtils
 *
 * @author yangjie
 * @date 2016/10/31
 */
public class ExcelUtils {


    static List<List<String>> readFromXLSX(String filePath) {
        File excelFile;
        InputStream is = null;
        String cellStr;
        List<List<String>> studentList = new ArrayList<List<String>>();
        try {
            excelFile = new File(filePath);
            is = new FileInputStream(excelFile);
            Workbook workbook2007 = WorkbookFactory.create(is);
            Sheet sheet = workbook2007.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                List<String> cellList = new ArrayList<String>();
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                // 循环遍历单元格
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    // 获取单元格对象
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        cellStr = "";
                    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
                        cellStr = String.valueOf(cell.getBooleanCellValue());
                    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        cellStr = (int) cell.getNumericCellValue() + "";
                    } else {// 其余按照字符串处理
                        cellStr = cell.getStringCellValue();
                    }
                    // 下面按照数据出现位置封装到bean中
                    cellList.add(cellStr);
                }
                studentList.add(cellList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            // TODO Auto-generated catch block
        } finally {// 关闭文件流
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return studentList;
    }


    /**
     * 将List集合数据写入excel（单个sheet）
     *
     * @param filePath     文件路径
     * @param employeeList 要写入的数据集合
     */
    static void writeExcel(String filePath, List<List<String>> employeeList) {
        System.out.println("开始写入文件>>>>>>>>>>>>");
        Workbook workbook = null;
        //2003
        if (filePath.toLowerCase().endsWith("xls")) {
            workbook = new HSSFWorkbook();
            //2007
        } else if (filePath.toLowerCase().endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        }
        if (workbook == null) {
            return;
        }
        //create sheet
        Sheet sheet = workbook.createSheet("表一");
        int rowIndex = 0;
        //遍历数据集，将其写入excel中
        try {
            rowIndex++;
            //循环写入主表数据
            for (List<String> employee : employeeList) {
                Row row = sheet.createRow(rowIndex);
                for (int i = 0; i < employee.size(); i++) {
                    Cell tempCell = row.createCell(i);
                    tempCell.setCellValue(employee.get(i));
                }
                rowIndex++;
            }
            System.out.println("主表数据写入完成>>>>>>>>");
            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
            System.out.println(filePath + "写入文件成功>>>>>>>>>>>");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
