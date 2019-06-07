package com.edp.system;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class Utilities {

    public static String GenerateId(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * Encoding method from passwordEncoderFactory
     */
    public static String passwordEncode(String password) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder.encode(password);
    }



    public static JSONObject JSONObjectFileReader(String fileName){
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(new String(Files.readAllBytes(Paths.get(fileName))));
        } catch ( IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONArray JSONArrayFileReader(String fileName){
        JSONArray jsonObject = null;

        try {
            jsonObject = new JSONArray(new String(Files.readAllBytes(Paths.get(fileName))));
        } catch ( IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static   JSONArray excelFileRead(String filename) {

        try {

            FileInputStream excelFile = new FileInputStream(new File(filename));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            int row =0;
            System.out.println(filename+":  "+datatypeSheet.getFirstRowNum()+"--"+datatypeSheet.getLastRowNum());

            JSONArray array = new JSONArray();
            ArrayList<String> title = new ArrayList<>();

            while (iterator.hasNext()) {
                //System.out.print(row+ "   ");

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                JSONObject object = new JSONObject();
                int col =0;

                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    if(row == 0){
//                        System.out.print(currentCell.getStringCellValue() + "--");
                        title.add(currentCell.getStringCellValue());
                    }
                    else{
                        if (currentCell.getCellType() == CellType.STRING) {
                            //System.out.print(currentCell.getStringCellValue() + "--");
                            object.put(title.get(col),currentCell.getStringCellValue());
                        } else if (currentCell.getCellType() == CellType.NUMERIC) {
                            //System.out.print(currentCell.getNumericCellValue() + "--");
                            object.put(title.get(col),currentCell.getNumericCellValue());
                        }else if (currentCell.getCellType() == CellType.BOOLEAN)  {
                            object.put(title.get(col),currentCell.getBooleanCellValue());
                        }

                    }
                    col++;

                }
                if(!object.keySet().isEmpty()){
                    array.put(object);
                    //System.out.println(object);
                }

                row ++;
            }
            return array;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }
}
