package org.translation;


import com.jfoenix.controls.*;
import com.opencsv.CSVReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {



    /*ID Changer CONTROLLER*/
    @FXML
    private JFXButton chooseButtonSrc;
    @FXML
    private JFXRadioButton importradioBtn;
    @FXML
    private JFXRadioButton exportradioBtn;
    @FXML
    private JFXButton chooseButtonTra;
    @FXML
    private TextField filepathSrc;
    @FXML
    private TextField filePathTra;
    @FXML
    private JFXButton startButton;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private CheckBox shiftColmns;




    /**
     * Initialize the tabs
     *
     * @param location:  URL
     * @param resources: resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         //Nothing to initialize
    }

    /**
     * Cancel Button Function
     *
     * @param event
     */
    @FXML
    void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * open the file to copy the Ids from
     *
     * @param event
     */
    @FXML
    void handleChooseButtonSrc(ActionEvent event) {

        utils.loadOpenDialog(filepathSrc, chooseButtonSrc);
    }

    /**
     * Open the file to copy the Ids into
     *
     * @param event
     */
    @FXML
    void handleChooseButtonTra(ActionEvent event) {

        utils.loadOpenDialog(filePathTra, chooseButtonTra);
    }


    /**
     * Export Radio Button
     *
     * @param event
     */
    @FXML
    void handleExportSelected(ActionEvent event) {

        if (this.exportradioBtn.isSelected()) {
            //Remove Selection from Shift columns
            if (this.shiftColmns.isSelected()) {
                this.shiftColmns.setSelected(false);
            }
            this.shiftColmns.setDisable(true);

            //Disable Translation Chooser
            this.filePathTra.setText("");
            this.filePathTra.setDisable(true);
            this.chooseButtonTra.setDisable(true);

        }
    }

    /**
     * Import Radio Button
     *
     * @param event
     */
    @FXML
    void handleImportSelected(ActionEvent event) {
        if (this.importradioBtn.isSelected()) {
            //Remove Selection from Shift columns
            if (this.shiftColmns.isDisable()) {
                this.shiftColmns.setDisable(false);
            }
            this.shiftColmns.setSelected(false);


            //Disable Translation Chooser
            if(this.filePathTra.isDisable())
            {this.filePathTra.setText("");
            this.filePathTra.setDisable(false);
            this.chooseButtonTra.setDisable(false);}
        }
    }

    /**
     * Trigger Import / Export  : Start Button Function
     *
     * @param event
     */
    @FXML
    void handleStartButton (ActionEvent event) {
        //Src file Path
        String excelWbPath = filepathSrc.getText();
        XSSFWorkbook wb = null;
        if(exportradioBtn.isSelected()){
            try {
                Path path = Paths.get(excelWbPath);
                String  outputPath=excelWbPath.substring(0, excelWbPath.indexOf(path.getFileName().toString()));
                wb =utils.loadExcelFile(excelWbPath);
                utils.writeDataInExcelSheet(outputPath,utils.getStringsFromExcelSheets(wb));
            } catch (FileNotFoundException e){
                utils.handleExceptions(e,"File Could not be found");
                e.printStackTrace();
            } catch (IOException e) {
                utils.handleExceptions(e,"Error when loading the excel file");
                e.printStackTrace();
            }
        }else{
            XSSFWorkbook traWb = null;
            String traWbPath = filePathTra.getText();

            try {
                wb =utils.loadExcelFile(excelWbPath);
                traWb =utils.loadExcelFile(traWbPath);
            } catch (IOException e) {
                utils.handleExceptions(e,"Error when loading one of the files");
                e.printStackTrace();
            }
            //TODO: get the translation in a key value store
            //TODO: copy the excel sheet
            //TODO: search the terms from the excel sheet in the csv and replace with translation
            //TODO: check if columns needs to be shifted
            if(shiftColmns.isSelected()){
                //ToDO:shift columns
            }
            //TODO:save the file




        }
        utils.triggerAlert("Info", "Done!");
    }
    }








