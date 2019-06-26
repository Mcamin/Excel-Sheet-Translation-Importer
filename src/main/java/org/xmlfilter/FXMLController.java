package org.xmlfilter;


import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.dom4j.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    }
    /**
     * Cancel Button Function
     *
     * @param event
     */
    @FXML
    void handlecancelButton(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * open the file to copy the Ids from
     *
     * @param event
     */
    @FXML
    void handlechooseButtonSrc(ActionEvent event) {

        utils.loadOpenDialog(filepathSrc, chooseButtonSrc);
    }

    /**
     * Open the file to copy the Ids into
     *
     * @param event
     */
    @FXML
    void handlechooseButtonTra(ActionEvent event) {

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
         * Trigger Changing ID : Start Button Function
         *
         * @param event
         */
        @FXML
        void handleStartButton (ActionEvent event){
            //Src file Path
            String src = filepathSrc.getText();
            utils.triggerAlert("Info", "Done!");


        }


    }








