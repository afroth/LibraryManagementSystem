package com.library.libraymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import com.library.libraymanagement.Book;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReturnBook implements Initializable {

    @FXML private Label lblReturnBook;
    @FXML private Button btnReturnBook;
    @FXML private Label lblBookId;
    @FXML private ListView<Book> listViewBookSearch;
    @FXML private TextField txtBookId;
    @FXML private TextField txtBookSearch;
    @FXML private Label lblBookSearch;
    @FXML private Label lblStatus;

    private ObservableList<Book> bookList;

    // fires upon click of btnReturnBook
    @FXML void returnBookAction(ActionEvent event) throws SQLException {
        // setting status equal to return of method errorCheck();
        lblStatus.setText(errorCheck());
    }// end returnBookAction

    //---------------------------------------------------------------------------------------
    //runs upon opening of ReturnBook Page
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.library.getlog();// clear log
        // Method call for bookSearch
        try {
            bookSearch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //---------------------------------------------------------------------------------------
    //getBooks gets the list of books that is passed to it from the list books passed into the
    // method from bookSearch()
    public void getBooks(ArrayList books){
        // setting observableList bookList equal to books
        bookList =  FXCollections.observableArrayList(books);
        // adding all the items form bookList to list view of books
        listViewBookSearch.getItems().addAll(bookList);
        // event listener for list view that updates txtbookId based on clicked listview item
        listViewBookSearch.getSelectionModel().selectedItemProperty().addListener((observable, previous, selected) -> {
            // checking is selected is null, if so make string empty
            if (selected == null){txtBookId.setText("");}
            // selected is not empty set the txt bookId equal to selected.getID()
            else {txtBookId.setText(String.valueOf(selected.getID()));}
        });// end of event listener
    }// end of getBooks

    //---------------------------------------------------------------------------------------
    //bookSearch initiates the search for books based on whats in the bookSearch
    // searchBox txtBookSearch
    public void bookSearch () throws SQLException {
        // method call getBooks
        getBooks(Main.library.books.getAll());
        // creating listener for book search bar
        txtBookSearch.textProperty().addListener((observable, previous, selected) -> {
            // clears the list view to refresh
            listViewBookSearch.getItems().clear();
            // setting search keyword as selected
            String searchKeyWord = selected;
            // sending search keyword in method call getBooks()
            try {
                getBooks(Main.library.bookSearch(searchKeyWord));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });// end of listener
    }// end of bookSearch

    //---------------------------------------------------------------------------------------
    //checks for errors if txtBookId is empty and sets font color for lblStatus based on results
    public  String errorCheck() throws SQLException {
        boolean issued;

        // if txtBookId is empty
        if(txtBookId.getText().equals("")){
            // setting lblStatus text color to red
            lblStatus.setTextFill(Color.RED);
            // string returned
            return "bookID is empty!";
        }
        // txtBookId is not empty
        else{
            // parsing value of txtBookId sotring in int bookId
            int bookId = Integer.parseInt(txtBookId.getText());
            //storing value in issued based on whether or not book was returned
            issued = Main.library.returnBook(bookId);
            // if book was successfully returned set lblStatus to lightgreen
            if(issued == true){lblStatus.setTextFill(Color.LIGHTGREEN);}
            // book was not successfully returned set lblStatus color to red
            else{lblStatus.setTextFill(Color.RED);}
            // return msgLog as strings from getLog().
            return Main.library.getlog();
        }// end else
    }// end of errorCheck()

}// end of ReturnBook

