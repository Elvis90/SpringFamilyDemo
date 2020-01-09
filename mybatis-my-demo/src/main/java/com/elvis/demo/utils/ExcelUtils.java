package com.elvis.demo.utils;

import com.elvis.demo.service.BaseExcelParseModel;
import com.elvis.demo.service.MyExcelModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Elvis
 * @create 2019-11-26 14:23
 */
@Slf4j
public class ExcelUtils {
    public static <T extends BaseExcelParseModel> List<T> parseExcelFile(File file, Class<T> clazz) throws Exception {
        if (null == file) {
            throw new Exception("文件不存在");
        }
        String fileName = file.getName();
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
        } catch (IOException e) {
            log.error("文件转输入流失败，文件路径：{}，失败原因：{}", file.getAbsoluteFile(), e.getMessage());
            throw new Exception("文件出错，请尝试重新上传");
        }
        return parseExcelInputStream(inputStream, fileName, clazz);
    }

    public static <T extends BaseExcelParseModel> List<T> parseExcelInputStream(InputStream inputStream, String fileName, Class<T> clazz) throws Exception {
        Workbook workbook = getWorkbook(inputStream, fileName);
        try {
            T excelParseModel = clazz.newInstance();
            Integer sheetNumber = excelParseModel.getSheetNumber();
            if (sheetNumber == null || sheetNumber < 0) {
                sheetNumber = 0;
            }
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            if (sheet == null) {
                return null;
            }
            List<T> results = new ArrayList<>(sheet.getLastRowNum());
            Integer beginRowNumber = excelParseModel.getBeginRowNumber();
            if (beginRowNumber == null || beginRowNumber < 0) {
                beginRowNumber = 0;
            }
            for (int rowNumber = beginRowNumber; rowNumber <= sheet.getLastRowNum(); rowNumber++) {
                Row row = sheet.getRow(rowNumber);
                if (row == null) {
                    continue;
                }
                T model = convertRow2Model(row, clazz);
                if (model != null) {
                    results.add(model);
                }
            }
            return results;
        } catch (Exception e) {
            log.error("文件解析失败，失败原因：{}", e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ioException) {
                log.warn("输入流关闭失败，失败原因：{}", ioException.getMessage());
            }
        }
        throw new Exception("文件解析失败，请重新尝试");
    }

    private static Workbook getWorkbook(InputStream inputStream, String fileName) throws Exception {
        try {
            if (StringUtils.isNotEmpty(fileName)) {
                int length = fileName.length();
                String fileSuffix;
                if (length > 5) {
                    fileSuffix = fileName.substring(length - 5, length);
                } else {
                    fileSuffix = fileName;
                }
                if (fileSuffix.toUpperCase().endsWith(".XLS")) {
                    //2003
                    return new HSSFWorkbook(inputStream);
                } else if (fileSuffix.toUpperCase().endsWith(".XLSX")) {
                    //2007
                    return new XSSFWorkbook(inputStream);
                }
            }
        } catch (Exception e) {
            log.error("创建Workbook工作薄对象失败，失败原因：{}", e.getMessage());
        }
        log.error("文件格式不支持，文件名：{}", fileName);
        throw new Exception("该文件格式不支持，暂支持xls、xlsx文件");

    }

    private static <T extends BaseExcelParseModel> T convertRow2Model(Row row, Class<T> clazz) {
        try {
            T excelParseModel = clazz.newInstance();
            List<String> columnNameList = excelParseModel.listColumnName();
            if (CollectionUtils.isNotEmpty(columnNameList)) {
                for (int i = 0; i < columnNameList.size(); i++) {
                    Cell cell = row.getCell(i);
                    setValue(excelParseModel, columnNameList.get(i), getCellValue(cell));
                }
            }
            return excelParseModel;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 给对象设值
     *
     * @param object    对象
     * @param fieldName 属性名
     * @param value     值
     * @return
     */
    private static Object setValue(Object object, String fieldName, String value) {
        String setterMethodName = getSetterMethodNameByFieldName(fieldName);
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                if(fields[i].getName().equals(fieldName)){
                    fields[i].setAccessible(true);
                    Method setterMethod = object.getClass().getDeclaredMethod(setterMethodName, fields[i].getType());
                    return setterMethod.invoke(object, value);
                }
            } catch (Exception e) {
                log.info("{}类中反射{}方法发生异常！异常：{}", object.getClass(), setterMethodName, e.getMessage());
                return "";
            }
        }
        return "";
    }

    /**
     * 根据 属性名 得到 setter方法名
     *
     * @param fieldName 属性名
     * @return
     */
    private static String getSetterMethodNameByFieldName(String fieldName) {
        StringBuilder methodName = new StringBuilder("set");
        methodName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
        return methodName.toString();
    }

    private static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }

        //判断数据的类型
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                //自定义日期处理
                short format = cell.getCellStyle().getDataFormat();
                SimpleDateFormat sdf = null;
                if (format == 14 || format == 31 || format == 57 || format == 58
                        || (176<=format && format<=178) || (182<=format && format<=196)
                        || (210<=format && format<=213) || (208==format ) ) { // 日期
                    sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                } else if (format == 20 || format == 32 || format==183 || (200<=format && format<=209) ) { // 时间
                    sdf = new SimpleDateFormat("HH:mm");
                } else { // 不是日期格式
                    cellValue =  String.valueOf(cell.getNumericCellValue());
                }
                double value = cell.getNumericCellValue();
                Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                if(date==null || "".equals(date)){
                    log.error("date为空");
                    cellValue =  "";
                }

                try {
                    cellValue = sdf.format(date);
                } catch (Exception e) {
                    e.printStackTrace();
                    cellValue = "";
                }

//                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK:
                cellValue = "";
                break;
            case ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * 设置私有属性为可访问
     * @param object
     */
    private static void setAccessible(Object object){
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
        }
    }

    public static void main(String[] args) {
        File excelFile = new File("D:\\test.xlsx");
        try {
            List<MyExcelModel> list = parseExcelFile(excelFile, MyExcelModel.class);
            System.out.println(list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
