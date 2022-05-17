package com.library.libraymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import com.library.libraymanagement.Book;
import com.library.libraymanagement.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class IssueBook {

    @FXML
    private Label lblBanner;
    @FXML private Label lblBookSearch;
    @FXML private Label lblUserSearch;
    @FXML private Label lblBookId;
    @FXML private Label lblUserId;
    @FXML private Button btnBookIssue;
    @FXML private TextField txtUserID;
    @FXML private TextField txtBookId;
    @FXML private TextField txtBookSearch;
    @FXML private TextField txtUserSearch;
    @FXML private ListView<Book> listViewBook;
    @FXML private ListView<User> listViewUser;
    @FXML private Label lblStatus;


    private ObservableList<User> userList;
    private ObservableList<Book> bookList;
    private ArrayList<Book> bookSearch = new ArrayList<>();

    // event listener is fired when btnBookIssue is clicked
    @FXML void ActionBookIssue(ActionEvent event) throws SQLException {
        lblStatus.setText(errorCheck());
    }

    //---------------------------------------------------------------------------------------
    //This method is initialized upon opening the page view IssueBook
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Method calls
        try {
            userSearch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            bookSearch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }// end of initialize

    //---------------------------------------------------------------------------------------
    //getBooks gets the list of books that is passed to it from the list books passed into the
    // method from bookSearch()
    public void getBooks(ArrayList books){
        // setting observableList bookList equal to books
        bookList =  FXCollections.observableArrayList(books);
        // adding all the items form bookList to list view of books
        listViewBook.getItems().addAll(bookList);
        // event listener for list view that updates txtBookId based on clicked listview item
        listViewBook.getSelectionModel().selectedItemProperty().addListener((observable, previous, selected) -> {
            // checking is selected is null, if so make string empty
            if (selected == null){txtBookId.setText("");}
            // selected is not empty set the txt bookId equal to selected.getID()
            else {txtBookId.setText(String.valueOf(selected.getID()));}
        });// end of event listener
    }// end getBooks()

    //---------------------------------------------------------------------------------------
    //getUsers gets the list of users that is passed to it from the list users passed into the
    // method from userSearch()
    public void getUsers(ArrayList users){
        // setting observableList bookList equal to user
        userList = FXCollections.observableList(users);
        // adding all the items form userList to list view of books
        listViewUser.getItems().addAll(userList);
        // event listener for list view that updates txtUserId based on clicked listview item
        listViewUser.getSelectionModel().selectedItemProperty().addListener((observable, previous, selected) -> {
            // checking is selected is null, if so make string empty
            if (selected == null){txtUserID.setText("");}
            // selected is not empty set the txt bookId equal to selected.getID()
            else {txtUserID.setText(String.valueOf(selected.getID()));}
        });// end of event listener
    }// end of getUsers

    //---------------------------------------------------------------------------------------
    // userSearch initiates the search for users based on whats in the userSearch
    // searchBox txtUserSearch
    public void userSearch () throws SQLException {
        // method call getBooks
        getUsers(Main.library.users.getAll());
        // creating listener for user search bar
        txtUserSearch.textProperty().addListener((observable, previous, selected) -> {
            // clears the list view to refresh
            listViewUser.getItems().clear();
            // setting search keyword as selected
            String searchKeyWord = selected;
            // sending search keyword in method call getUsers()
            try {
                getUsers(Main.library.userSearch(searchKeyWord));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });// end of event listener
    }// end of userSearch

    //---------------------------------------------------------------------------------------
    //bookSearch initiates the search for books based on whats in the bookSearch
    // searchBox txtBookSearch
    public void bookSearch () throws SQLException {
        // method call getBooks
        getBooks(Main.library.books.getAll());
        // creating listener for book search bar
        txtBookSearch.textProperty().addListener((observable, previous, selected) -> {
            // clears the list view to refresh
            listViewBook.getItems().clear();
            // setting search keyword as selected
            String searchKeyWord = selected;
            // sending search keyword in method call getBooks()
            try {
                getBooks(Main.library.bookSearch(searchKeyWord));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });// end of event listener
    }// end of bookSearch

    //---------------------------------------------------------------------------------------
    //checks for errors if txtBookId or txtUserId is empty and sets font color for lblStatus
    // based on results
    public  String errorCheck() throws SQLException {
        boolean issued;
        // if txtBookId is empty
        if(txtBookId.getText().equals("")|| txtUserID.getText().equals("")){
            // setting lblStatus text color to red
            lblStatus.setTextFill(Color.RED);
            // string returned
            return "userID or bookID is empty!";
        }
        // txtBookId or txtUserId not empty
        else{
            // parsing value of txtUserId storing in int userId
            int userId = Integer.parseInt(txtUserID.getText());
            // parsing value of txtBookId storing in int bookId
            int bookId = Integer.parseInt(txtBookId.getText());
            //storing value in issued based on whether or not book issued to the user
            issued = Main.library.issueBook(userId, bookId);
            // if book was successfully issued set lblStatus to lightgreen
            if(issued == true){lblStatus.setTextFill(Color.LIGHTGREEN);}
            // book was not successfully issued to user set lblStatus color to red
            else{lblStatus.setTextFill(Color.RED);}
            // return msgLog as strings from getLog().
            return Main.library.getlog();
        }// end of else
    }// end of errorCheck()
}
