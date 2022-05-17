package com.library.libraymanagement;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserDAO {
    //String url;
    Connection connection = null;

    //------------------------------------------------------------------------------
    //constructor to establish connection and connect to database
    public UserDAO(String s) throws SQLException {
        // establish a connection
        connection = DriverManager.getConnection(s);

    }// end of Constructor

    //------------------------------------------------------------------------------
    // This method insert new user objects into the database
    public void insert(User u) throws SQLException {
        // setting a preparedStatement with a SQl insert for each object parameter
        PreparedStatement preparedStatement = connection.prepareStatement("insert into users (name,email,address,dateOfBirth,isStudent,balance) values (?,?,?,?,?,?)");
        // Inserts the name of the user into the database from User object passed in
        preparedStatement.setString(1,u.getName());
        // Inserts the email of the user into the database from the User object passed in
        preparedStatement.setString(2,u.getEmail());
        // Inserts the address of the user into the database from the User object passed in
        preparedStatement.setString(3,u.getAddress());
        // transfers the localDate into a sqlDate to store into database
        java.sql.Date sqlDate = java.sql.Date.valueOf( u.getDateOfBirth() );
        // Inserts the birthdate of the user into the database from the User object passed in
        preparedStatement.setDate(4,sqlDate);
        // Inserts the status of student or faculty of the user into the database from the User object passed in
        preparedStatement.setBoolean(5,u.isStudent());
        // Inserts the balance of the user into the database from the User object passed in
        preparedStatement.setDouble(6,u.getBalance());
        // closes to free up any resources
        preparedStatement.executeUpdate();
        // closes to free up any resources
        preparedStatement.close();
    }// end of insert()

    //------------------------------------------------------------------------------
    // This method deletes from the database based on the user id
    public void delete(User u) throws SQLException {
        // setting a preparedStatement with a SQl delete for each object parameter
        PreparedStatement preparedStatement = connection.prepareStatement("delete from users where ID = ?");
        // gets the ID of the user object passed in
        preparedStatement.setInt(1,u.getID());
        // executes the prepared statements above
        preparedStatement.executeUpdate();
        // closes to free up any resources
        preparedStatement.close();
    }// end of delete()

    //------------------------------------------------------------------------------
    //
    public void update(User u) throws SQLException {
        // setting a preparedStatement with a SQl update for each object parameter
        PreparedStatement preparedStatement = connection.prepareStatement("update users set name=?, email=?, " +
                "address=?, dateOfBirth = ?, isStudent =?, balance = ? where ID = ?");
        preparedStatement.setInt(7,u.getID());
        // Inserts the name of the user into the database from User object passed in
        preparedStatement.setString(1,u.getName());
        // Inserts the email of the user into the database from the User object passed in
        preparedStatement.setString(2,u.getEmail());
        // Inserts the address of the user into the database from the User object passed in
        preparedStatement.setString(3,u.getAddress());
        // transfers the localDate into a sqlDate to store into database
        java.sql.Date sqlDate = java.sql.Date.valueOf( u.getDateOfBirth() );
        // Inserts the birthdate of the user into the database from the User object passed in
        preparedStatement.setDate(4,sqlDate);
        // Inserts the status of student or faculty of the user into the database from the User object passed in
        preparedStatement.setBoolean(5,u.isStudent());
        // Inserts the balance of the user into the database from the User object passed in
        preparedStatement.setDouble(6,u.getBalance());
        // closes to free up any resources
        preparedStatement.executeUpdate();
        // closes to free up any resources
        preparedStatement.close();
    }// end of update()

    //------------------------------------------------------------------------------
    // setting a preparedStatement with a SQl select all from users
    public ArrayList<User> getAll() throws SQLException {
        // setting a preparedStatement with a SQl select all users from users table
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
        ArrayList<User> arr = new ArrayList<>();
        // getting the result for query
        ResultSet rs = preparedStatement.executeQuery();
        // while loop while results has another
        while(rs.next()){
            LocalDate localDate = rs.getDate(5).toLocalDate();
            // creating new object passing in the values from ResultSet
            User temp = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),localDate,
                    rs.getBoolean(6),rs.getDouble(7));
            arr.add(temp);// adding the temp object into the array list
        }// end of while
        // closes to free up any resources
        preparedStatement.close();

        return arr;// return arr
    }// end of getAll()

    //------------------------------------------------------------------------------
    // this method returns a book object from the database based on the passed in bookID
    public User getUser (int userID) throws SQLException {
        // setting a preparedStatement with a SQl select all from users where id matches userID passed in
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where ID = ?");
        preparedStatement.setInt(1, userID);
        // getting the result from query
        ResultSet rs =  preparedStatement.executeQuery();
        LocalDate localDate = rs.getDate(5).toLocalDate();
        // creating new object passing in the values from ResultSet
        User temp = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), localDate,
                rs.getBoolean(6), rs.getDouble(7));
        // closes to free up any resources
        preparedStatement.close();

        return temp;// return arr
    }// end of getUser()

    //------------------------------------------------------------------------------
    // returns an array list based on user names that are close to the search results of string passed in
    public  ArrayList<User> getByQuery(String s) throws SQLException {
        // setting a preparedStatement with a SQl select all where string s is like for each object parameter
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where name like ?");
        // temp array list to return
        ArrayList<User> arr = new ArrayList<>();
        // looking for anything that is wildcard s wildcard to get anything that contains string passed in
        preparedStatement.setString(1,"%" + s + "%");
        // getting the result for query
        ResultSet rs = preparedStatement.executeQuery();
        // while loop while results has another
        while(rs.next()){
            LocalDate localDate = rs.getDate(5).toLocalDate();
            // creating new object passing in the values from ResultSet
            User temp = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),localDate,
                    rs.getBoolean(6),rs.getDouble(7));
            arr.add(temp);// adding the temp object into the array list
        }// end of while
        // closes to free up any resources
        preparedStatement.close();

        return arr;// return arr
    }// end of getByQuery()

}
