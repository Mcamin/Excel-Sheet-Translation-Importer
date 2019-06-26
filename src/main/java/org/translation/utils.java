package org.translation;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class utils {



    /**
     * Load Excel File
     *
     * @param path: file path
     * @return workbook: XSSFWorkbook
     */
    protected static XSSFWorkbook loadExcelFile(String path) throws IOException {
        FileInputStream file=new FileInputStream(path);
        XSSFWorkbook workbook =new XSSFWorkbook(file);
            return workbook;
    }




    /**
     * Get the strings from all Excel sheets
     * @param wb: XSSFWorkbook
     * @return stringSet: Set of Strings to translate
     */
     static Set getStringsFromExcelSheets(XSSFWorkbook wb){
         Set stringSet= new HashSet();
         wb.forEach(sheet -> {
            sheet.forEach(row -> {
                row.forEach(cell -> {
                    String val;
                    if(isCellValueString(cell)){
                        val=cell.getStringCellValue();
                        if(!(isNumeric(val) ||  isDate(val)))
                        {stringSet.add(val);
                        }}
                });
            });


        });

        return stringSet;
    }

    /**
     * get the Strings tuples from the translation workbook
     * @param wb translation workbook
     * @return stringTuples : Hashmap containing the tuples
     */
    static HashMap<String,String> getStringTuples(XSSFWorkbook wb){
        XSSFSheet sheet = wb.getSheetAt(0);
        HashMap<String,String> stringTuples = new HashMap<>();
        sheet.forEach(row -> {
                Cell originalCell = row.getCell(0);
                Cell translationCell = row.getCell(1);
                    String val;
                    if(isCellValueString(originalCell) && isCellValueString(translationCell)){
                         stringTuples.put(originalCell.getStringCellValue(),translationCell.getStringCellValue());
                    }

            });
        return stringTuples;
    }

    /**
     * Replace the strings in all Excel sheets
     * @param wb: XSSFWorkbook
     *
     * @return stringSet: Set of Strings to translate
     */
    static XSSFWorkbook replaceStringsInExcelSheets(XSSFWorkbook wb,HashMap<String,String> traItems){
        wb.forEach(sheet -> {
            sheet.forEach(row -> {
                row.forEach(cell -> {
                    String val;
                    if(isCellValueString(cell)){
                        val=cell.getStringCellValue();
                       if(traItems.containsKey(val))
                           cell.setCellValue((traItems.get(val)));
                    }
                });
            });
        });
        return wb;
    }

    /**
     * check if the cell contains a string
     * @param cell: the cell to test
     * @return boolean
     */
    private static boolean isCellValueString(Cell cell) {
        if (cell.getCellType() == CellType.STRING) {
            return true;
        }
        return false;
    }

    public static boolean isDate(String val) {
        return val.matches("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");
    }

    public static boolean isNumeric(String val) {
        return  val.matches("[-+]?[0-9]*\\.?[0-9]+") ||val.matches("[-+]?[0-9]*\\,?[0-9]+");

    }




    /**
     * Write Strings to be translated into Excel Sheet
     * @param filePath where to save the file
     * @param strings the set of strings
     * @throws IOException
     */
    public static void writeDataInExcelSheet(String filePath,
                                      Set strings) throws IOException {

        String[] columns = {"originalText", "translation"};
        if (strings.size() > 0) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Translation");
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.GREEN.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            Row headerRow = sheet.createRow(0);
            //Initiate Header
            for(int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }
            int rowNum = 1;

            for(Object item: strings) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0)
                        .setCellValue((String)item);

                row.createCell(1)
                        .setCellValue("");
            }
            // Resize all columns to fit the content size
            for(int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            writeExcelSheet(filePath +"toTranslate.xlsx",workbook);

        }

    }

    /**
     *  Create an Excelsheet file
     * @param path path to generate the excel sheet into
     * @param workbook workbook to use
     * @throws IOException
     */
  public static void writeExcelSheet(String path,XSSFWorkbook workbook) throws IOException {
      // Write the output to a file
      FileOutputStream fileOut = new FileOutputStream(path);
      workbook.write(fileOut);
      fileOut.close();
      // Closing the workbook
      workbook.close();
  }

    static XSSFWorkbook changeColmnsAlignments(XSSFWorkbook wb){
        wb.forEach(sheet -> {
                if(sheet.isRightToLeft())
                 sheet.setRightToLeft(false);
                else
                    sheet.setSelected(true);

        });

return wb;
    }
/*===========================JFX Functions =============================*/
    /**
     * Trigger Alerts
     *
     * @param title : alert title
     * @param msg   : message to display
     */
    static void triggerAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Display The Exception to the user
     *
     * @param e       Exception
     * @param Message The Message to display
     */
    static void handleExceptions(Exception e, String Message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        if (Message == null)
            alert.setContentText(e.toString());
        else alert.setContentText(Message);
        alert.showAndWait();
    }

    /**
     * Configure the filechooser Window
     *
     * @param fileChooser : a file Chooser
     */
    static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Open Excel File");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
       /* fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Xml", "*.xml"),
                new FileChooser.ExtensionFilter("Txt", "*.txt")
        );*/
    }


    /**
     * Load open dialog window
     *
     * @param t   the textfield to set the path to
     * @param btn the btn to open the window
     */
    static void loadOpenDialog(TextField t, JFXButton btn) {
        btn.getId();
        Stage stage = (Stage) btn.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            t.setText(selectedFile.getAbsolutePath());
        }
    }


}

