package com.library.libraymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserView implements Initializable {

    @FXML private Label lblNameDisplay;
    @FXML private Label lblEmailDisplay;
    @FXML private Label lblAddressDisplay;
    @FXML private Label lblUserTypeDisplay;
    @FXML private Label lblBalanceDisplay;
    @FXML private Label lblBirthdayDisplay;
    @FXML private ListView<Book> listViewCheckedBooks;
    @FXML private ComboBox<User> comboUserChoice;
    @FXML private Label lblName1;
    @FXML private Button btnClearBal;

    private String userBalance;
    // Observable list to store all the users for combo box
    ObservableList<User> userList = FXCollections.observableArrayList(Main.library.users.getAll());
    // Observable list to store all the checked out books of a user
    ObservableList<Book> olist;

    public UserView() throws SQLException {
    }

    //---------------------------------------------------------------------------------------
    // This is event listener is fired when the btnClearBal is clicked
    // the button is initially hidden until a user with a balance is selected
    @FXML void balanceAction(ActionEvent event) throws SQLException {
        // call the method collectFine from library and passes the object selected from
        // comboBox
        Main.library.collectFine(comboUserChoice.getValue());
        // sets the button to invisible
        btnClearBal.setVisible(false);
        // gets the users balance and parses it into a string
        userBalance= String.valueOf(comboUserChoice.getValue().getBalance());
        //setting the text of lblBalance to userBalance String
        lblBalanceDisplay.setText(userBalance);
        // setting the label that displays balances to green
        lblBalanceDisplay.setTextFill(Color.LIGHTGREEN);
    }// end of event listener

    //---------------------------------------------------------------------------------------
    //This method is initialized upon opening the page view
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // setting the combo box items to userList
        comboUserChoice.getItems().addAll(userList);
        // make the button invisible
        btnClearBal.setVisible(false);
        // event listener for comboBox
        comboUserChoice.setOnAction(e->{
            // clear list and retrieves new one based on user selected
            listViewCheckedBooks.getItems().clear();
            // setting olist to the books that a user currently has checked out
            try {
                olist = FXCollections.observableArrayList(Main.library.userBooks(comboUserChoice.getValue().getID()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            // setting text for selected users name
            lblNameDisplay.setText(comboUserChoice.getValue().getName());
            // setting text for selected users email
            lblEmailDisplay.setText(comboUserChoice.getValue().getEmail());
            // setting text for selected users address
            lblAddressDisplay.setText(comboUserChoice.getValue().getAddress());
            // setting text for selected users Birthday
            lblBirthdayDisplay.setText(comboUserChoice.getValue().getDateOfBirth().toString());
            // setting text for user type by passing boolean to method userType()
            lblUserTypeDisplay.setText(userType(comboUserChoice.getValue().isStudent()));
            // getting selected user balance and storing into string for setting
            userBalance= String.valueOf(comboUserChoice.getValue().getBalance());
            // setting text for selected users balance
            lblBalanceDisplay.setText(userBalance);
            // method call
            balanceOption();
            // setting the listView of checked out books from olist
            listViewCheckedBooks.setItems(olist);

        });// end of event listener
    }// end of initialize

    //---------------------------------------------------------------------------------------
    // This method takes in a boolean of the the user type then returns a string of either
    // "Student" or "Faculty".
    public String userType(boolean bool){
        // holds the String value of what the user is
        String user;
        // if passed in boolean is true
        if(bool){
            // user string is student
            user = "Student";
        }
        // passed in bool is not true then must be faculty
        else{ user = "Faculty";}
        // return the string user
        return user;
    }// end of user type

    //---------------------------------------------------------------------------------------
    // this makes the btnClearBal visible if a user has a positive balance and
    // changes the color of lblBalance if balance is positive
    public void balanceOption (){
        // if the balance is greater than 0
        if(comboUserChoice.getValue().getBalance() > 0){
            // change font color to red of balance display
            lblBalanceDisplay.setTextFill(Color.RED);
            // make the btnClearBal visible
            btnClearBal.setVisible(true);
        }// end if
        else{ lblBalanceDisplay.setTextFill(Color.LIGHTGREEN);}
    }// end of balanceOption
}// end of UserView
