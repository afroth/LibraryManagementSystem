package com.library.libraymanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class userAddedDialog {

    @FXML private Button btnUserAdded;
    @FXML private Label lblAddedUser;
    private AddUser pController;
    private AddBook pController1;

    //----------------------------------------------------------------------------------
    // listener event is fired when the button btnUserAdded is clicked closing the
    // screen
    @FXML void btnAction(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.hide();
    }

    //----------------------------------------------------------------------------------
    // method is called from AddUser passing in the name of the newly added user
    public void initData(String message, AddUser pController){
        // setting the text of lblAddedUser to message
        lblAddedUser.setText("Registered " +message );
        //setting controller equal to this page
        this.pController=pController;
    }// end of initData

    //----------------------------------------------------------------------------------
    // method is called from AddBook passing in the name of the newly added book
    public void initData2(String message, AddBook pController){
        // setting the text of lblAddedUser to message
        lblAddedUser.setText("Registered " +message );
        //setting controller equal to this page
        this.pController1=pController;

    }// end of initData2
}// end of UserAddedDialog
