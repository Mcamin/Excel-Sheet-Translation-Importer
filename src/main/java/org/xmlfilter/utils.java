package org.xmlfilter;

import com.jfoenix.controls.JFXButton;
import com.opencsv.CSVWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;

import org.dom4j.io.XMLWriter;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.*;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.IOException;



public class utils {


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
    static void HandleExceptions(Exception e, String Message) {
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

    /**
     * Load Excel File
     *
     * @param path:file path
     * @return Document
     */
    static Workbook loadDocument(String path) throws IOException {

            Workbook workbook = WorkbookFactory.create(new File(path));
            return workbook;
    }



    /**
     * Save one document
     *
     * @param p        path
     * @param ext      name_extension
     * @param document Excel file to write
     * @throws IOException
     */
    static void savedocument(String p, String ext, Workbook document)
             {

        Path path = Paths.get(p);
        String filename, outputPath;
        filename = path.getFileName().toString();
        outputPath = p.substring(0, p.indexOf(filename));

        String file = outputPath + filename.substring(0,
                filename.indexOf('.')) + ext;
                 OutputStream out = null;
                 try {
                     out = new FileOutputStream(file);
                 } catch (FileNotFoundException e) {
                     e.printStackTrace();
                 }
                 OutputFormat outFormat = new OutputFormat();
        outFormat.setEncoding("UTF-8");
                 XMLWriter writer = null;
                 try {
                     writer = new XMLWriter(out, outFormat);
                 } catch (UnsupportedEncodingException e) {
                     e.printStackTrace();
                 }
                 try {
                     writer.write(document);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
                 try {
                     writer.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }






    /**
     * Create an output document
     *
     * @return output document
     */
    public static Document createDocument() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("strings")
                .addAttribute("version", "1.0");
        return document;

    }









    /**
     * Write results in csv file
     *
     * @param filePath : where to write the csv
     * @param strings  the multiple translation strings
     * @throws IOException
     */
    public static void writeDataInCsv(String filePath,
                                      ArrayList<String[]> strings)
            throws IOException {
        if (strings.size() > 0) {
            Writer writer = Files.newBufferedWriter(Paths.get(filePath + "toTranslate.csv"));

            CSVWriter csvWriter = new CSVWriter(writer,
                    ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);


            // adding data to csv
            for (int i = 0; i < strings.size(); i++)
                csvWriter.writeNext(strings.get(i));
            // closing writer connection
            writer.close();
        }

    }






}

