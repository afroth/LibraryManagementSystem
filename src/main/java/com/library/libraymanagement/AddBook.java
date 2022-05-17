package com.library.libraymanagement;

import com.library.libraymanagement.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddBook {
    @FXML
    private Button btnRegister;
    @FXML private ListView<String> lstViewPublisher;
    @FXML private TextField txtName;
    @FXML private TextField txtAuthor;
    @FXML private TextField txtIsbn;
    @FXML private TextField txtYear;
    @FXML private ComboBox<String> cmbBoxGenre;

    ObservableList<String> publishers;
    ObservableList<String> genres;
    String dataToBePassed;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        publishers = FXCollections.observableArrayList ("Pearson", "Scholastic", "Allen and Unwin", "Wiley","Disney","Penguin").sorted();
        lstViewPublisher.setItems(publishers);
        genres = FXCollections.observableArrayList("Education", "Adventure", "Thriller","History", "Fantasy").sorted();
        cmbBoxGenre.setItems(genres);
    }


    public void registerAction(ActionEvent e) throws SQLException, IOException {

        String selectedPublisher=lstViewPublisher.getSelectionModel().getSelectedItem();
        String selectedGenre=cmbBoxGenre.getSelectionModel().getSelectedItem();
        Book newBook = new Book(txtName.getText(),txtAuthor.getText(),selectedPublisher,selectedGenre,txtIsbn.getText(),Long.parseLong(txtYear.getText()));

        Main.library.addBook(newBook); // update model
        dataToBePassed =txtName.getText();
        bookAddedDialog(e);
        // closes out the page
        Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        primaryStage.hide();
    }

    public void bookAddedDialog (ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../../../../../resources/com/gui/librarymanagement/userAddedDialog.fxml"));
        Parent root = loader.load(); // this loads the FXML file,calls initialize
        userAddedDialog controller = loader.getController(); // get handle on controller class instance.
        controller.initData2(dataToBePassed,this); //call appropriate method
        Stage secondary = new Stage(); // this gets the stage
        secondary.setTitle("Registration Successful");
        secondary.setScene(new Scene(root));

        Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //get handle on parent stage
        secondary.initOwner(thisStage); //specify the owner
        secondary.initModality(Modality.WINDOW_MODAL); //blocks any event from being delivered to its owner.
        // System.out.println(dataToBePassed);
        secondary.showAndWait(); //replace the scene.
    }

}
