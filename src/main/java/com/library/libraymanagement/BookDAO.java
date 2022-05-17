package com.library.libraymanagement;

import java.sql.*;
import java.util.ArrayList;

public class BookDAO {
    Connection connection = null;

    //------------------------------------------------------------------------------
    //constructor to establish connection and connect to database
    public BookDAO(String s) throws SQLException {
        // establish a connection
        connection = DriverManager.getConnection(s);

    }// end of Constructor

    //------------------------------------------------------------------------------
    // This method insert new Book objects into the database
    public void insert(Book b) throws SQLException {
        // setting a preparedStatement with a SQl insert for each object parameter
        PreparedStatement preparedStatement = connection.prepareStatement("insert into books (name,author,publisher,genre,ISBN,year) values (?,?,?,?,?,?)");
        // Inserts the name of the book into the database from Book object passed in
        preparedStatement.setString(1,b.getName());
        // Inserts the author of the book into the database from the Book object passed in
        preparedStatement.setString(2,b.getAuthor());
        // Inserts the publisher of the book into the database from the Book object passed in
        preparedStatement.setString(3,b.getPublisher());
        // Inserts the genre of the book into the database from the Book object passed in
        preparedStatement.setString(4,b.getGenre());
        // Inserts the ISBN of the book into the database from the Book object passed in
        preparedStatement.setString(5,b.getISBN());
        // Inserts the year of the book into the database from the Book object passed in
        preparedStatement.setLong(6,b.getYear());
        // executes the prepared statements above
        preparedStatement.executeUpdate();
        // closes to free up any resources
        preparedStatement.close();
    }// end of Insert()

    //------------------------------------------------------------------------------
    // This method deletes from the database based on the Book ID passed in
    public void delete(Book b) throws SQLException {
        // setting a preparedStatement with a SQl delete for each object parameter
        PreparedStatement preparedStatement = connection.prepareStatement("delete from books where ID = ?");
        // gets the ID of the book object passed in
        preparedStatement.setInt(1,b.getID());
        // executes the prepared statements above
        preparedStatement.executeUpdate();
        // closes to free up any resources
        preparedStatement.close();
    }// end of delete()

    //------------------------------------------------------------------------------
    // updates the book in the database based on the books ID passed in
    public void update(Book b) throws SQLException {
        // setting a preparedStatement with a SQl update for each object parameter
        PreparedStatement preparedStatement = connection.prepareStatement("update books set name=?, email=?, " +
                "address=?, dateOfBirth = ?, isStudent =?, balance = ? where ID = ?");
        // uses the books id to get
        preparedStatement.setInt(7,b.getID());
        // inserts and updates the objects passed in name
        preparedStatement.setString(1,b.getName());
        // inserts and updates the objects passed in author
        preparedStatement.setString(2,b.getAuthor());
        // inserts and updates the objects passed in publisher
        preparedStatement.setString(3,b.getPublisher());
        // inserts and updates the objects passed in genre
        preparedStatement.setString(4,b.getGenre());
        // inserts and updates the objects passed in ISBN
        preparedStatement.setString(5,b.getISBN());
        // inserts and updates the objects passed in year
        preparedStatement.setLong(6,b.getYear());
        preparedStatement.executeUpdate();
        // closes to free up any resources
        preparedStatement.close();
    }// end of update()

    //------------------------------------------------------------------------------
    //
    public ArrayList<Book> getAll() throws SQLException {
        // setting a preparedStatement with a SQl select all from books
        PreparedStatement preparedStatement = connection.prepareStatement("select * from books");
        // temp array list to return
        ArrayList<Book> arr = new ArrayList<>();
        // getting the result ofr query
        ResultSet rs = preparedStatement.executeQuery();
        // while loop while results has another
        while(rs.next()){
            // creating new object passing in the values from ResultSet
            Book temp = new Book(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),
                    rs.getString(6),rs.getLong(7));
            arr.add(temp);// adding the temp object into the array list
        }// end of while
        // closes to free up any resources
        preparedStatement.close();

        return arr; // return arr
    }// end of getAll()

    //------------------------------------------------------------------------------
    // this method returns a book object from the database based on the passed in bookID
    public Book getBook (int bookID) throws SQLException {
        // setting a preparedStatement with a SQl select all from books where id matches bookID passed in
        PreparedStatement preparedStatement = connection.prepareStatement("select * from books where ID = ?");
        preparedStatement.setInt(1, bookID);
        ResultSet rs =  preparedStatement.executeQuery();
        // creating new object passing in the values from ResultSet
        Book temp = new Book(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),
                rs.getString(6),rs.getLong(7));

        // closes to free up any resources
        preparedStatement.close();

        return temp; // return temp
    }// end of getBook()

    //------------------------------------------------------------------------------
    // returns an array list based on book names that are close to the search results of string passed in
    public  ArrayList<Book> getByQuery(String s) throws SQLException {
        // setting a preparedStatement with a SQl select all where string s is like for each object parameter
        PreparedStatement preparedStatement = connection.prepareStatement("select * from books where name like ?");
        // temp array list to return
        ArrayList<Book> arr = new ArrayList<>();
        // looking for anything that is wildcard s wildcard to get anything that contains string passed in
        preparedStatement.setString(1,"%" + s + "%");
        // getting the result for query
        ResultSet rs = preparedStatement.executeQuery();
        // while loop while results has another
        while(rs.next()){
            // creating new object passing in the values from ResultSet
            Book temp = new Book(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),
                    rs.getString(6),rs.getLong(7));
            arr.add(temp);// adding the temp object into the array list
        }// end of while
        // closes to free up any resources
        preparedStatement.close();

        return arr; // return arr
    }// end of getByQuery()
}
