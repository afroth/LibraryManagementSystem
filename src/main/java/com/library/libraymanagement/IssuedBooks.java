package com.library.libraymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.library.libraymanagement.Transaction;
import com.library.libraymanagement.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class IssuedBooks implements Initializable {

    @FXML private Label lblBooksTable;
    @FXML private TableView<Transaction> tblIssuedBooks;
    @FXML private TableColumn<Transaction, Integer> tblColumnBookID;
    @FXML private TableColumn<Transaction, Integer> tblColumnUserID;
    @FXML private TableColumn<Transaction, Date> tblColumnIssueDate;

    //array list to store transactions of checked out books
    private ArrayList<Transaction> temp = new ArrayList<>();
    // creating observable list of the arraylist list
    private ObservableList <Transaction> list;

    // method initialized upon opening the page IssuedBooks
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            list = FXCollections.observableArrayList(Main.library.issued(temp));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tblIssuedBooks.setItems(list);
        setBookIdColumn();
        setUserIdColumn();
        setDateColumn();
    }// end initialize

    public void setBookIdColumn (){
        PropertyValueFactory<Transaction, Integer> bookId = new PropertyValueFactory<>("bookID");
        tblColumnBookID.setCellValueFactory(bookId);
    }

    public void setUserIdColumn (){
        PropertyValueFactory<Transaction, Integer> userId = new PropertyValueFactory<>("userID");
        tblColumnUserID.setCellValueFactory(userId);
    }

    public void setDateColumn (){
        PropertyValueFactory<Transaction, Date> date = new PropertyValueFactory<>("issueDate");
        tblColumnIssueDate.setCellValueFactory(date);
    }
}

