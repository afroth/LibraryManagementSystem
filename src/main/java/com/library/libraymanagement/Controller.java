package com.library.libraymanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML private Button btnAddUser;
    @FXML private Button btnIssueBook;
    @FXML private Button btnAddNewBook;
    @FXML private Button btnUserDetails;
    @FXML private Button btnIssuedBook;
    @FXML private Button btnReturnBook;


    @FXML
    void btnAction(ActionEvent event) throws IOException {
        if (event.getSource() == btnAddUser) {
            // method call to launch view AddUser.fxml
            addUserView(event);
        } else if (event.getSource() == btnIssueBook) {
            // method call to launch view IssueBook.fxml
            issueBookView(event);
        } else if (event.getSource() == btnAddNewBook) {
            // method call to launch view AddBook.fxml
            addBookView(event);
        } else if (event.getSource() == btnUserDetails) {
            // method call to launch view UserView.fxml
            userSelectView(event);
        } else if (event.getSource() == btnIssuedBook) {
            // method call to launch view IssuedBooks.fxml
            issuedBooksView(event);
        } else if (event.getSource() == btnReturnBook) {
            // method call to launch view BookReturn.fxml
            bookReturnView(event);
        }
    }// end of btnAction

    //------------------------------------------------------------------------
    // This Method launches the view for AddUser
    public void addUserView (ActionEvent e) throws IOException {
        // this initiates a loader and points the Adduser to the FXML file
        FXMLLoader loader=new FXMLLoader(getClass().getResource("AddUser.fxml"));
        // this loads the FXML file,calls initialize
        Parent root = loader.load();
        Stage primaryStage = new Stage(); // this gets the stage
        primaryStage.setTitle("Add New User");
        primaryStage.setScene(new Scene(root));
        Stage thisStage = (Stage) ((Node) e.getSource()).getScene().getWindow(); //get handle on parent stage
        primaryStage.initOwner(thisStage); //specify the owner
        primaryStage.initModality(Modality.WINDOW_MODAL); //blocks any event from being delivered to its owner.
        primaryStage.showAndWait(); //replace the scene.
    }

    //------------------------------------------------------------------------
    // This method launches the view for IssueBook
    public void issueBookView(ActionEvent e) throws IOException{
        // this initiates a loader and points the Adduser to the FXML file
        FXMLLoader loader=new FXMLLoader(getClass().getResource("IssueBook.fxml"));
        // this loads the FXML file,calls initialize
        Parent root = loader.load();
        Stage primaryStage = new Stage(); // this gets the stage
        primaryStage.setTitle("Issue Book");
        primaryStage.setScene(new Scene(root));
        Stage thisStage = (Stage) ((Node) e.getSource()).getScene().getWindow(); //get handle on parent stage
        primaryStage.initOwner(thisStage); //specify the owner
        primaryStage.initModality(Modality.WINDOW_MODAL); //blocks any event from being delivered to its owner.
        primaryStage.showAndWait(); //replace the scene.
    }// end issueBookView

    //------------------------------------------------------------------------
    // this Method launches the view for AddBook
    public void addBookView(ActionEvent e) throws IOException{
        // this initiates a loader and points the Adduser to the FXML file
        FXMLLoader loader=new FXMLLoader(getClass().getResource("AddBook.fxml"));
        // this loads the FXML file,calls initialize
        Parent root = loader.load();

        Stage primaryStage = new Stage(); // this gets the stage
        // setting the title for AddBook
        primaryStage.setTitle("Add New Book");
        primaryStage.setScene(new Scene(root));

        Stage thisStage = (Stage) ((Node) e.getSource()).getScene().getWindow(); //get handle on parent stage
        primaryStage.initOwner(thisStage); //specify the owner
        primaryStage.initModality(Modality.WINDOW_MODAL); //blocks any event from being delivered to its owner.

        primaryStage.showAndWait(); //replace the scene.
    }

    //------------------------------------------------------------------------
    //this method launches the view for ReturnBook
    public void bookReturnView(ActionEvent e) throws IOException{
        // this initiates a loader and points the Adduser to the FXML file
        FXMLLoader loader=new FXMLLoader(getClass().getResource("ReturnBook.fxml"));
        // this loads the FXML file,calls initialize
        Parent root = loader.load();

        Stage primaryStage = new Stage(); // this gets the stage
        primaryStage.setTitle("Return Book");
        primaryStage.setScene(new Scene(root));

        Stage thisStage = (Stage) ((Node) e.getSource()).getScene().getWindow(); //get handle on parent stage
        primaryStage.initOwner(thisStage); //specify the owner
        primaryStage.initModality(Modality.WINDOW_MODAL); //blocks any event from being delivered to its owner.

        primaryStage.showAndWait(); //replace the scene.
    }

    //------------------------------------------------------------------------
    //This Method Launches the view for UserView
    public void userSelectView(ActionEvent e) throws IOException{
        // this initiates a loader and points the Adduser to the FXML file
        FXMLLoader loader=new FXMLLoader(getClass().getResource("UserView.fxml"));
        // this loads the FXML file,calls initialize
        Parent root = loader.load();

        Stage primaryStage = new Stage(); // this gets the stage
        primaryStage.setTitle("User Select View");
        primaryStage.setScene(new Scene(root));

        Stage thisStage = (Stage) ((Node) e.getSource()).getScene().getWindow(); //get handle on parent stage
        primaryStage.initOwner(thisStage); //specify the owner
        primaryStage.initModality(Modality.WINDOW_MODAL); //blocks any event from being delivered to its owner.

        primaryStage.showAndWait(); //replace the scene.
    }

    //------------------------------------------------------------------------
    //This Method launches the view for IssuedBooks
    public void issuedBooksView(ActionEvent e) throws IOException{
        // this initiates a loader and points the Adduser to the FXML file
        FXMLLoader loader=new FXMLLoader(getClass().getResource("IssuedBooks.fxml"));
        // this loads the FXML file,calls initialize
        Parent root = loader.load();

        Stage primaryStage = new Stage(); // this gets the stage
        primaryStage.setTitle("User Select View");
        primaryStage.setScene(new Scene(root));

        Stage thisStage = (Stage) ((Node) e.getSource()).getScene().getWindow(); //get handle on parent stage
        primaryStage.initOwner(thisStage); //specify the owner
        primaryStage.initModality(Modality.WINDOW_MODAL); //blocks any event from being delivered to its owner.

        primaryStage.showAndWait(); //replace the scene.
    }
}