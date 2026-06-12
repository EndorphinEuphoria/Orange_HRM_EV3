package utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;

public class Excel {
    private static final String RUTA =
        "src/test/resources/testData/DatosPrueba_Admin.xlsx";

    public static String leerCelda(int fila, int columna) {
        try (FileInputStream fis = new FileInputStream(RUTA);
             Workbook wb = new XSSFWorkbook(fis)) {
            Sheet hoja = wb.getSheetAt(0);
            Row row    = hoja.getRow(fila - 1);
            Cell cell  = row.getCell(columna - 1);
            return cell.toString().trim();
        } catch (Exception e) {
            throw new RuntimeException(
                "Error leyendo Excel fila=" + fila + " col=" + columna + ": " + e.getMessage()
            );
        }
    }

    public static String leerCeldaDeHoja(String nombreHoja, int fila, int columna) {
        try (FileInputStream fis = new FileInputStream(RUTA);
             Workbook wb = new XSSFWorkbook(fis)) {
            Sheet hoja = wb.getSheet(nombreHoja);
            Row row    = hoja.getRow(fila - 1);
            Cell cell  = row.getCell(columna - 1);
            return cell.toString().trim();
        } catch (Exception e) {
            throw new RuntimeException(
                "Error leyendo Excel hoja=" + nombreHoja + " fila=" + fila + " col=" + columna + ": " + e.getMessage()
            );
        }
    }
}