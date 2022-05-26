package com.library.libraymanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.library.libraymanagement.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class AddUser implements Initializable {
    @FXML
    private Button btnRegister;
    @FXML private RadioButton rdoStudent;
    @FXML private RadioButton rdoFaculty;
    @FXML private TextField txtName;
    @FXML private TextField txtEmail;
    @FXML private TextArea txtAddress;
    @FXML private DatePicker txtDate;

    private LocalDate localDate;
    AtomicBoolean atomicStudent = new AtomicBoolean(false);

    String dataToBePassed;

    // event listener for DatePicker
    @FXML void datePickerAction(ActionEvent event) {
        localDate = txtDate.getValue();
    }

    // event listener for Registering a new user
    @FXML void registerAction(ActionEvent event) throws IOException, SQLException {
        // Method call to register the user
        registerUser();
        Main.library.getlog();// clear log
        // method call for dialog box that says we successfully registered new user
        userAddedDialog(event);
        // closes out the page
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.hide();
    }// end of registerAction

    //---------------------------------------------------------------------------------------
    // method initialized upon opening the page AddUser
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initializing a new toggle group
        ToggleGroup group = new ToggleGroup();
        // adding rdoStudent to toggle group and setting it to true
        rdoStudent.setToggleGroup(group);
        rdoStudent.setSelected(true);
        // adding rdoFaculty to toggle group
        rdoFaculty.setToggleGroup(group);
    }// end of initialize

    //---------------------------------------------------------------------------------------
    // This created a newUser object and adds it to the Main.library object. The data
    // is pulled from the data entered in the boxes the user fills out
    public void registerUser() throws SQLException {
        // boolean to hold value of student or faculty
        boolean student;
        // if radio button student is selected
        if(rdoStudent.isSelected()){student = true;}

        // if student is not selected faculty must be selected
        else{student = false;}

        // creating new user object and adding values to it based on selected
        User newUser = new User(txtName.getText(),txtEmail.getText(),txtAddress.getText(),localDate,student);
        // adding new object of user to the library arraylist user
        Main.library.addUser(newUser);
        // setting dataToBePassed to users name
        dataToBePassed = txtName.getText();
    }

    //---------------------------------------------------------------------------------------
    // This method pops up the userAddedDialog window
    public void userAddedDialog (ActionEvent event) throws IOException {
        // get the fmxl file for the loader
        FXMLLoader loader=new FXMLLoader(getClass().getResource("userAddedDialog.fxml"));
        Parent root = loader.load(); // this loads the FXML file,calls initialize
        userAddedDialog controller = loader.getController(); // get handle on controller class instance.
        controller.initData(dataToBePassed,this); //call appropriate method
        Stage secondary = new Stage(); // this gets the stage
        secondary.setTitle("Registration Successful");
        secondary.setScene(new Scene(root));

        Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //get handle on parent stage
        secondary.initOwner(thisStage); //specify the owner
        secondary.initModality(Modality.WINDOW_MODAL); //blocks any event from being delivered to its owner.
        secondary.showAndWait(); //replace the scene.
    }// end of userAddedDialog

}
