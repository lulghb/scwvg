package com.scwvg.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scwvg.utils.annotation.ExcelField;
import com.scwvg.utils.annotation.ExcelSheet;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/20 22:04
 * @desc: Excel工具类, Excel转Java对象、Java对象转Excel
**/
public class ExcelUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
	
	public static List<Object> importExcel(Workbook workbook, Class<?> sheetClass) {
        List<Object> sheetDataList = importSheet(workbook, sheetClass);
        return sheetDataList;
    }
	
	public static List<Object> importSheet(Workbook workbook, Class<?> sheetClass) {
        try {
            // sheet
            ExcelSheet excelSheet = sheetClass.getAnnotation(ExcelSheet.class);
            String sheetName = (excelSheet!=null && excelSheet.name()!=null && excelSheet.name().trim().length()>0)?excelSheet.name().trim():sheetClass.getSimpleName();

            // sheet field
            List<Field> fields = new ArrayList<Field>();
            if (sheetClass.getDeclaredFields()!=null && sheetClass.getDeclaredFields().length>0) {
                for (Field field: sheetClass.getDeclaredFields()) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    fields.add(field);
                }
            }

            if (fields.isEmpty()) {
                throw new RuntimeException("无数据!");
            }

            // sheet data
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                return null;
            }

            Iterator<Row> sheetIterator = sheet.rowIterator();
            int rowIndex = 0;
            List<Object> dataList = new ArrayList<Object>();
            while (sheetIterator.hasNext()) {
                Row rowX = sheetIterator.next();
                if (rowIndex > 0) {
                    Object rowObj = sheetClass.newInstance();
                    for (int i = 0; i < fields.size(); i++) {

                        // cell
                        Cell cell = rowX.getCell(i);
                        if (cell == null) {
                            continue;
                        }

                        // call val str
                        cell.setCellType(CellType.STRING);
                        String fieldValueStr = cell.getStringCellValue();       // cell.getCellTypeEnum()

                        // java val
                        Field field = fields.get(i);
                        Object fieldValue = parseValue(field, fieldValueStr);
                        if (fieldValue == null) {
                            continue;
                        }

                        // fill val
                        field.setAccessible(true);
                        field.set(rowObj, fieldValue);
                    }
                    dataList.add(rowObj);
                }
                rowIndex++;
            }
            return dataList;
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
	
	public static List<Object> importExcel(File excelFile, Class<?> sheetClass) {
        try {
            Workbook workbook = WorkbookFactory.create(excelFile);
            List<Object> dataList = importExcel(workbook, sheetClass);
            return dataList;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
	
	public static List<Object> importExcel(String filePath, Class<?> sheetClass) {
        File excelFile = new File(filePath);
        List<Object> dataList = importExcel(excelFile, sheetClass);
        return dataList;
    }
	
	public static List<Object> importExcel(InputStream inputStream, Class<?> sheetClass) {
        try {
            Workbook workbook = WorkbookFactory.create(inputStream);
            List<Object> dataList = importExcel(workbook, sheetClass);
            return dataList;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
	
	public static Workbook exportWorkbook(List<?>... sheetDataListArr){

        // data array valid
        if (sheetDataListArr==null || sheetDataListArr.length==0) {
            throw new RuntimeException("data array can not be empty.");
        }

        // book （HSSFWorkbook=2003/xls、XSSFWorkbook=2007/xlsx）
        Workbook workbook = new HSSFWorkbook();

        // sheet
        for (List<?> dataList: sheetDataListArr) {
            makeSheet(workbook, dataList);
        }

        return workbook;
    }
	
	private static void makeSheet(Workbook workbook, List<?> sheetDataList){
        if (sheetDataList==null || sheetDataList.size()==0) {
            throw new RuntimeException("data can not be empty.");
        }

        Class<?> sheetClass = sheetDataList.get(0).getClass();
        ExcelSheet excelSheet = sheetClass.getAnnotation(ExcelSheet.class);

        String sheetName = sheetDataList.get(0).getClass().getSimpleName();
        int headColorIndex = -1;
        if (excelSheet != null) {
            if (excelSheet.name()!=null && excelSheet.name().trim().length()>0) {
                sheetName = excelSheet.name().trim();
            }
            headColorIndex = excelSheet.headColor().getIndex();
        }

        Sheet existSheet = workbook.getSheet(sheetName);
        if (existSheet != null) {
            for (int i = 2; i <= 1000; i++) {
                String newSheetName = sheetName.concat(String.valueOf(i));  // avoid sheetName repetition
                existSheet = workbook.getSheet(newSheetName);
                if (existSheet == null) {
                    sheetName = newSheetName;
                    break;
                } else {
                    continue;
                }
            }
        }

        Sheet sheet = workbook.createSheet(sheetName);

        // sheet field
        List<Field> fields = new ArrayList<Field>();
        if (sheetClass.getDeclaredFields()!=null && sheetClass.getDeclaredFields().length>0) {
            for (Field field: sheetClass.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                fields.add(field);
            }
        }

        if (fields==null || fields.size()==0) {
            throw new RuntimeException("data field can not be empty.");
        }

        // sheet header row
        CellStyle[] fieldDataStyleArr = new CellStyle[fields.size()];
        int[] fieldWidthArr = new int[fields.size()];
        Row headRow = sheet.createRow(0);
        for (int i = 0; i < fields.size(); i++) {
            // field
            Field field = fields.get(i);
            ExcelField excelField = field.getAnnotation(ExcelField.class);

            String fieldName = field.getName();
            int fieldWidth = 0;
            HorizontalAlignment align = null;
            if (excelField != null) {
                if (excelField.name()!=null && excelField.name().trim().length()>0) {
                    fieldName = excelField.name().trim();
                }
                fieldWidth = excelField.width();
                align = excelField.align();
            }

            // field width
            fieldWidthArr[i] = fieldWidth;

            // head-style、field-data-style
            CellStyle fieldDataStyle = workbook.createCellStyle();
            if (align != null) {
                fieldDataStyle.setAlignment(align);
            }
            fieldDataStyleArr[i] = fieldDataStyle;

            CellStyle headStyle = workbook.createCellStyle();
            headStyle.cloneStyleFrom(fieldDataStyle);
            if (headColorIndex > -1) {
                headStyle.setFillForegroundColor((short) headColorIndex);
                headStyle.setFillBackgroundColor((short) headColorIndex);
                headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            }

            // head-field data
            Cell cellX = headRow.createCell(i, CellType.STRING);
            cellX.setCellStyle(headStyle);
            cellX.setCellValue(String.valueOf(fieldName));
        }

        // sheet data rows
        for (int dataIndex = 0; dataIndex < sheetDataList.size(); dataIndex++) {
            int rowIndex = dataIndex+1;
            Object rowData = sheetDataList.get(dataIndex);

            Row rowX = sheet.createRow(rowIndex);

            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                try {
                    field.setAccessible(true);
                    Object fieldValue = field.get(rowData);

                    String fieldValueString = formatValue(field, fieldValue);

                    Cell cellX = rowX.createCell(i, CellType.STRING);
                    cellX.setCellValue(fieldValueString);
                    cellX.setCellStyle(fieldDataStyleArr[i]);
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            }
        }

        // sheet finally
        for (int i = 0; i < fields.size(); i++) {
            int fieldWidth = fieldWidthArr[i];
            if (fieldWidth > 0) {
                sheet.setColumnWidth(i, fieldWidth);
            } else {
                sheet.autoSizeColumn((short)i);
            }
        }
    }
	
	public static void exportToFile(String filePath, List<?>... sheetDataListArr){
        // workbook
        Workbook workbook = exportWorkbook(sheetDataListArr);

        FileOutputStream fileOutputStream = null;
        try {
            // workbook 2 FileOutputStream
            fileOutputStream = new FileOutputStream(filePath);
            workbook.write(fileOutputStream);

            // flush
            fileOutputStream.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (fileOutputStream!=null) {
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
    }
	
	private static Boolean parseBoolean(String value) {
		value = value.replaceAll("　", "");
		if (Boolean.TRUE.toString().equalsIgnoreCase(value)) {
			return Boolean.TRUE;
		} else if (Boolean.FALSE.toString().equalsIgnoreCase(value)) {
			return Boolean.FALSE;
		} else {
			throw new RuntimeException("parseBoolean but input illegal input=" + value);
		}
	}
	
	private static Integer parseInt(String value) {
		try {	
			value = value.replaceAll("　", "");
			return Integer.valueOf(value);
		} catch(NumberFormatException e) {
			throw new RuntimeException("parseInt but input illegal input=" + value, e);
		}
	}
	
	private static Short parseShort(String value) {
		try {
			value = value.replaceAll("　", "");
			return Short.valueOf(value);
		} catch(NumberFormatException e) {
			throw new RuntimeException("parseShort but input illegal input=" + value, e);
		}
	}
	
	private static Long parseLong(String value) {
		try {
			value = value.replaceAll("　", "");
			return Long.valueOf(value);
		} catch(NumberFormatException e) {
			throw new RuntimeException("parseLong but input illegal input=" + value, e);
		}
	}
	
	private static Float parseFloat(String value) {
		try {
			value = value.replaceAll("　", "");
			return Float.valueOf(value);
		} catch(NumberFormatException e) {
			throw new RuntimeException("parseFloat but input illegal input=" + value, e);
		}
	}

	private static Double parseDouble(String value) {
		try {
			value = value.replaceAll("　", "");
			return Double.valueOf(value);
		} catch(NumberFormatException e) {
			throw new RuntimeException("parseDouble but input illegal input=" + value, e);
		}
	}
	
	private static Date parseDate(String value, ExcelField excelField) {
		try {
			String datePattern = "yyyy-MM-dd HH:mm:ss";
			if (excelField != null) {
				datePattern = excelField.dateformat();
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
			return dateFormat.parse(value);
		} catch(ParseException e) {
			throw new RuntimeException("parseDate but input illegal input=" + value, e);
		}
	}
	
	private static Object parseValue(Field field, String value) {
		Class<?> fieldType = field.getType();

		ExcelField excelField = field.getAnnotation(ExcelField.class);
		if(value==null || value.trim().length()==0)
			return null;
		value = value.trim();

		/*if (Byte.class.equals(fieldType) || Byte.TYPE.equals(fieldType)) {
			return parseByte(value);
		} else */if (Boolean.class.equals(fieldType) || Boolean.TYPE.equals(fieldType)) {
			return parseBoolean(value);
		}/* else if (Character.class.equals(fieldType) || Character.TYPE.equals(fieldType)) {
			 return value.toCharArray()[0];
		}*/ else if (String.class.equals(fieldType)) {
			return value;
		} else if (Short.class.equals(fieldType) || Short.TYPE.equals(fieldType)) {
			 return parseShort(value);
		} else if (Integer.class.equals(fieldType) || Integer.TYPE.equals(fieldType)) {
			return parseInt(value);
		} else if (Long.class.equals(fieldType) || Long.TYPE.equals(fieldType)) {
			return parseLong(value);
		} else if (Float.class.equals(fieldType) || Float.TYPE.equals(fieldType)) {
			return parseFloat(value);
		} else if (Double.class.equals(fieldType) || Double.TYPE.equals(fieldType)) {
			return parseDouble(value);
		} else if (Date.class.equals(fieldType)) {
			 return parseDate(value, excelField);
		} else {
			throw new RuntimeException("request illeagal type, type must be Integer not int Long not long etc, type=" + fieldType);
		}
	}
	
	private static String formatValue(Field field, Object value) {
		Class<?> fieldType = field.getType();

		ExcelField excelField = field.getAnnotation(ExcelField.class);
		if(value==null) {
			return null;
		}

		if (Boolean.class.equals(fieldType) || Boolean.TYPE.equals(fieldType)) {
			return String.valueOf(value);
		} else if (String.class.equals(fieldType)) {
			return String.valueOf(value);
		} else if (Short.class.equals(fieldType) || Short.TYPE.equals(fieldType)) {
			return String.valueOf(value);
		} else if (Integer.class.equals(fieldType) || Integer.TYPE.equals(fieldType)) {
			return String.valueOf(value);
		} else if (Long.class.equals(fieldType) || Long.TYPE.equals(fieldType)) {
			return String.valueOf(value);
		} else if (Float.class.equals(fieldType) || Float.TYPE.equals(fieldType)) {
			return String.valueOf(value);
		} else if (Double.class.equals(fieldType) || Double.TYPE.equals(fieldType)) {
			return String.valueOf(value);
		} else if (Date.class.equals(fieldType)) {
			String datePattern = "yyyy-MM-dd HH:mm:ss";
			if (excelField != null && excelField.dateformat()!=null) {
				datePattern = excelField.dateformat();
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
			return dateFormat.format(value);
		} else {
			throw new RuntimeException("request illeagal type, type must be Integer not int Long not long etc, type=" + fieldType);
		}
	}

}
