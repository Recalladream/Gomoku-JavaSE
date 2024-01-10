package dao;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class ThirdPartyLibrary {
    public void into_excel(Connection con,String sql) throws SQLException {

        try (PreparedStatement preparedStatement = con.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Sheet1");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 创建行，用于存储列名
            XSSFRow headerRow = sheet.createRow(0);
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                XSSFCell cell = headerRow.createCell(i - 1);
                cell.setCellValue(columnName);
            }

            int rowNumber = 1;
            while (resultSet.next()) {
                // 创建行，用于存储数据
                XSSFRow row = sheet.createRow(rowNumber++);
                for (int i = 1; i <= columnCount; i++) {
                    XSSFCell cell = row.createCell(i - 1);
                    cell.setCellValue(resultSet.getString(i));
                }
            }

            try {
                FileOutputStream fileOutputStream = new FileOutputStream("resource//output.xlsx");
                workbook.write(fileOutputStream);
                System.out.println("excel数据写入成功");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
