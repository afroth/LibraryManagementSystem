module com.library.libraymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.library.libraymanagement to javafx.fxml;
    exports com.library.libraymanagement;
}