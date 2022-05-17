package com.library.libraymanagement;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionDAO {
    // String url;
    Connection connection = null;

    //------------------------------------------------------------------------------
    //constructor to establish connection and connect to database
    public TransactionDAO(String s) throws SQLException {
        // establish a connection
        connection = DriverManager.getConnection(s);
        System.out.println("Connection established!");
    }// end of TransactionDAO()

    //------------------------------------------------------------------------------
    // This method insert new transaction object into the database
    public void insert(Transaction t) throws SQLException {
        // setting a preparedStatement with a SQl update for each object parameter
        PreparedStatement preparedStatement = connection.prepareStatement("insert into libTransactions (bookID,userID,issueDate,status) values (?,?,?,?)");
        // Inserts the bookID of the object passes into the database
        preparedStatement.setInt(1,t.getBookID());
        // inserts the userID of the object and passes into the database
        preparedStatement.setInt(2,t.getUserID());
        // setting the sqlDate to the object passed in localDate
        java.sql.Date sqlDate = java.sql.Date.valueOf( t.getIssueDate() );
        // Inserting the sqldate into the
        preparedStatement.setDate(3,  sqlDate);
        // Inserts the boolean of the book always starts off as true,into the database
        preparedStatement.setBoolean(4,t.isStatus());
        // executes the prepared statements above
        preparedStatement.executeUpdate();
        // closes to free up any resources
        preparedStatement.close();
    }// end of insert()

    //------------------------------------------------------------------------------
    // This method deletes from the database based on the Book ID passed in
    public void delete(Transaction t) throws SQLException {
        // setting a preparedStatement with a SQl delete for each object parameter
        PreparedStatement preparedStatement = connection.prepareStatement("delete from libTransactions where bookID = ?");
        // gets the ID of the book object passed in
        preparedStatement.setInt(1,t.getBookID());
        // executes the prepared statements above
        preparedStatement.executeUpdate();
        // closes to free up any resources
        preparedStatement.close();
    }// end of delete()

    //------------------------------------------------------------------------------
    // updates the transactions in the database based on the primary keys attributes passed in from
    // the object
    public void update(Transaction t) throws SQLException {
        // setting a preparedStatement with a SQl update and set the status based on the primarykey(bookID,userID, issueDate)
        PreparedStatement preparedStatement = connection.prepareStatement("update libTransactions set status = ? where bookID =? AND userID =? AND issueDate = ?" );
        preparedStatement.setInt(2,t.getBookID());
        preparedStatement.setInt(3,t.getUserID());
        java.sql.Date sqlDate = java.sql.Date.valueOf( t.getIssueDate() );
        preparedStatement.setDate(4,  sqlDate);
        preparedStatement.setBoolean(1,t.isStatus());
        // executes the prepared statements above
        preparedStatement.executeUpdate();
        // closes to free up any resources
        preparedStatement.close();
    }// end of update()

    //------------------------------------------------------------------------------
    // returns and array list with all the transaction from the database
    public ArrayList<Transaction> getAll() throws SQLException {
        // setting a preparedStatement with a SQl select all from libTransactions for each object parameter
        PreparedStatement preparedStatement = connection.prepareStatement("select * from libTransactions");
        // temp array list to return
        ArrayList<Transaction> arr = new ArrayList<>();
        // getting the result for query
        ResultSet rs = preparedStatement.executeQuery();

        // while loop while results has another
        while(rs.next()){
            LocalDate localDate = rs.getDate(3).toLocalDate();
            // creating new object passing in the values from ResultSet
            Transaction temp = new Transaction(rs.getInt(1),rs.getInt(2),localDate,
                    rs.getBoolean(4));
            arr.add(temp);// adding the temp object into the array list
        }// end of while
        // closes to free up any resources
        preparedStatement.close();

        return arr;// return arr
    }// end of getAll()

    //------------------------------------------------------------------------------
    // get the transaction from the database based on the status of the transactions returns
    // temp arr with the transactions where books are checked out
    public ArrayList<Transaction> getCurrent () throws SQLException {
        // setting a preparedStatement with a SQl select all from libTransactions for each status that is true
        PreparedStatement preparedStatement = connection.prepareStatement("select * from libTransactions where status = 1");
        // temp array list to return
        ArrayList<Transaction> arr = new ArrayList<>();
        // getting the result for query
        ResultSet rs = preparedStatement.executeQuery();
        // while loop while results has another
        while(rs.next()){
            LocalDate localDate = rs.getDate(3).toLocalDate();
            // creating new object passing in the values from ResultSet
            Transaction temp = new Transaction(rs.getInt(1),rs.getInt(2),localDate,
                    rs.getBoolean(4));
            arr.add(temp);// adding the temp object into the array list
        }// end of while
        // closes to free up any resources
        preparedStatement.close();

        return arr;// return arr

    }

    //------------------------------------------------------------------------------
    // get the transaction from the database based on the userID passed in and returns
    // temp arr with the matching transactions
    public ArrayList<Transaction> getByUser (int userID) throws SQLException {
        // setting a preparedStatement with a SQl select all from libTransactions where the userID matches.
        PreparedStatement preparedStatement = connection.prepareStatement("select * from libTransactions where userID = ?");
        // sending the userID to find the matching userID's in the database
        preparedStatement.setInt(1, userID);
        // temp array list to return
        ArrayList<Transaction> arr = new ArrayList<>();
        // getting the result for query
        ResultSet rs = preparedStatement.executeQuery();
        // while loop while results has another
        while(rs.next()){
            LocalDate localDate = rs.getDate(3).toLocalDate();
            // creating new object passing in the values from ResultSet
            Transaction temp = new Transaction(rs.getInt(1),rs.getInt(2),localDate,
                    rs.getBoolean(4));
            arr.add(temp);// adding the temp object into the array list
        }// end of while
        // closes to free up any resources
        preparedStatement.close();

        return arr;// return arr
    }

    //------------------------------------------------------------------------------
    // get the transaction from the database based on the bookID passed in and returns
    // temp arr with the matching transactions
    public ArrayList<Transaction> getByBook (int bookID) throws SQLException {
        // setting a preparedStatement with a SQl select all from libTransactions where the bookID matches.
        PreparedStatement preparedStatement = connection.prepareStatement("select * from libTransactions where bookID = ?");
        // sending the userID to find the matching bookID's in the database
        preparedStatement.setInt(1, bookID);
        // temp array list to return
        ArrayList<Transaction> arr = new ArrayList<>();
        // getting the result for query
        ResultSet rs = preparedStatement.executeQuery();
        // while loop while results has another
        while(rs.next()){
            LocalDate localDate = rs.getDate(3).toLocalDate();
            // creating new object passing in the values from ResultSet
            Transaction temp = new Transaction(rs.getInt(1), rs.getInt(2), localDate,
                    rs.getBoolean(4));
            arr.add(temp);// adding the temp object into the array list
        }// end of while
        // closes to free up any resources
        preparedStatement.close();

        return arr;// return arr
    }// end of getByBook()
}
