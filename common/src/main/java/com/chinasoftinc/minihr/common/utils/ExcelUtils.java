package com.chinasoftinc.minihr.common.utils;

import com.chinasoftinc.minihr.common.exception.FileReadException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aizhanglin on 2018/4/17.
 */
public class ExcelUtils {

    private final static String XLSX = "xlsx";
    private static final String XLS = "xls";

    public static Workbook readFileToWorkbook(MultipartFile file) throws FileReadException {
        String fileName = file.getOriginalFilename();
        Workbook workbook = null;
        try {
            InputStream is = file.getInputStream();
            if (fileName.endsWith(XLS)) {
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(XLSX)) {
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            throw new FileReadException("文件读取异常", e);
        }
        return workbook;
    }

    public static boolean checkFile(MultipartFile file) {
        if (file != null) {
            String fileName = file.getOriginalFilename();
            if (fileName.endsWith(XLSX) || fileName.endsWith(XLS)) {
                return true;
            }
        }
        return false;
    }

    public static List<String[]> readExcel(Workbook workbook) {
        List<String[]> list = new ArrayList<>();
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                int firstRowNum = sheet.getFirstRowNum();
                int lastRowNum = sheet.getLastRowNum();
                for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    int firstCellNum = row.getFirstCellNum();
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    String[] cells = new String[row.getPhysicalNumberOfCells()];
                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    list.add(cells);
                }
            }
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private static String getCellValue(Cell cell) {
        String value = null;

        if (cell != null) {
            switch (cell.getCellTypeEnum()) {
                case FORMULA:
                    value = "" + cell.getCellFormula();
                    break;
                case NUMERIC:
                    value = "" + cell.getNumericCellValue();
                    break;
                case STRING:
                    value = "" + cell.getStringCellValue();
                    break;
                case BLANK:
                    value = "";
                    break;
                case BOOLEAN:
                    value = "" + cell.getBooleanCellValue();
                    break;
                case ERROR:
                    value = "" + cell.getErrorCellValue();
                    break;
                default:
                    value = "UNKNOWN value" + cell.getCellTypeEnum();
            }
            System.out.println("CELL col=" + cell.getColumnIndex() + " VALUE="
                    + value);
        }
        return value;
    }
}
