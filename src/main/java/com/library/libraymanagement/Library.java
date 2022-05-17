package com.library.libraymanagement;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Random;

public class Library {
    private int MAX_BOOK_LIMIT = 3;
    private int MAX_LOAN_DAYS = 14;
    private boolean DEBUG_DATE=false;
    public String url = "jdbc:sqlite:lib.db";

    public TransactionDAO transactions = new TransactionDAO(url);
    public UserDAO users = new UserDAO(url);
    public BookDAO books = new BookDAO(url);
    public ArrayList<String> msgLog = new ArrayList<>();

    public Library() throws SQLException {
    }


    //-----------------------------------------------------------------------------
    // adds object book to arraylist books
    public void addBook(Book book) throws SQLException {
        msgLog.add("Book ID "+book.getID()+" added to Library");
        books.insert(book);
    }

    //-----------------------------------------------------------------------------
    // adds object user to arraylist users
    public void addUser(User user) throws SQLException {
        // msgLog.add("User ID "+user.getID()+" added to Library");
        users.insert(user);
    }

    //-----------------------------------------------------------------------------
    // adds object transaction to arraylist transactions
    public void addTransaction(Transaction transaction) throws SQLException {
        // setting object transaction status as true meaning book checked out
        transaction.setStatus(true);
        // adding object transaction to array list transaction
        transactions.insert(transaction);
        //getDueDate(transaction.getIssueDate());

    }
    //-----------------------------------------------------------------------------
    // this method simply iterates through transactions array list using a for-loop
    // and checks for a bookID and if isStatus is true;.
    public boolean isAvailable(int bookID) throws SQLException {

        ArrayList<Transaction> temp = transactions.getAll();
        for(Transaction t:temp){
            if (t.getBookID() == bookID && t.isStatus())
                return false;
        }
        return true;

    }

    //-----------------------------------------------------------------------------
    // this method simply iterates through transactions array list using a for-loop
    // and checks two comparators to get users loan count.
    public int getBorrowCount(int userID) throws SQLException {
        ArrayList<Transaction> temp;
        // variable to store the users loan count
        int count = 0;
        // enhanced for-loop to iterate through array list transactions
        temp = transactions.getAll();
        // check objects for user id and is book status is true.
        for (Transaction transaction : temp)
            // if both conditions are true count = count + 1
            if (transaction.getUserID() == userID && transaction.isStatus())
                count++;

        if(count >= 3){msgLog.add("User has reached the maximum limit of borrowed books!");}

        return count;// return count as the users total rented out books
    }

    //-----------------------------------------------------------------------------
    // This method returns the due date for a transaction by manipulating the Date Object
    // with a Calendar Object.
    public LocalDate getDueDate(LocalDate issueDate) {
        return issueDate.plusDays(MAX_LOAN_DAYS);
    }

    //-----------------------------------------------------------------------------
    // issueBook method return whether or not a user can check out a book. Multiple
    // checks must validate before a user can check out a book. If and only if the
    // book is available to be borrow, the user has not reached their max borrow count
    // and the user does not have any outstanding balances. If all checks are good, the
    // user can then rent out a book
    public boolean issueBook(int userID, int bookID) throws SQLException {
        User u = getUser(userID);
        Book b = getBook(bookID);

        //check if book is unavailable
        if (!isAvailable(bookID)){
            msgLog.add("This book is currently unavailable!");
            return false;
        }

        //check if max books limit has been reached or outstanding fines
        if (getBorrowCount(userID) >= MAX_BOOK_LIMIT) {
            msgLog.add("User has reached the maximum limit of borrowed books!");
            return false;
        }
        //check if user has outstanding balance
        if (u.getBalance() > 0){
            msgLog.add("User has an outstanding balance of $"+u.getBalance()+"!");
            return false;
        }

        // ready to issue book.
        Transaction trans = new Transaction(bookID,userID);
        addTransaction(trans);
        msgLog.add(b.getName()+" has been issued to "+u.getName()+"." );
        msgLog.add("The due date is "+getDueDate(trans.getIssueDate()));
        return true;
    }

    //-----------------------------------------------------------------------------
    // this method iterates through array list transactions to find matching book ids
    // using a enhanced forloop and comparing Object arraylist bookID's to the passed
    // in variable. I pulled this right from milestone 1 solution
    public boolean returnBook(int bookID) throws SQLException {
        Transaction trans = null;
        ArrayList<Transaction> temp;
        temp = transactions.getAll();
        for (Transaction t: temp)
            if (t.getBookID()==bookID && t.isStatus()){
                trans = t;
                break;
            }
        if (trans==null){
            msgLog.add("Book currently not borrowed");
            return false;
        }

        //compute Fines
        int userID = trans.getUserID();
        User u = getUser(userID);
        Book b = getBook(bookID);
        double fine = computeFine(trans);
        u.setBalance(u.getBalance()+fine);
        users.update(u);
        trans.setStatus(false);
        transactions.update(trans);
        if(fine==0)
            msgLog.add("Thanks for returning "+b.getName()+"!");
        else {
            msgLog.add("You returned " + b.getName() + " " + fine + " days late!");
            msgLog.add("Your outstanding balance is $" + u.getBalance());
        }
        return true;
    }// end return book

    //-----------------------------------------------------------------------------
    // collect sets the user fine to 0 by calling the .setbalance() and passing in 0
    // I pulled this right from milestone 1 solution
    public void collectFine(User user) throws SQLException {
        // user balance is less than or equal 0
        if (user.getBalance()<=0){
            //adds string to msgLog
            msgLog.add("User has no outstanding balances..");
        }
        else{
            // set userBalance to 0
            user.setBalance(0);
            // update in database
            users.update(user);
        }
    }// end collectFine

    //-----------------------------------------------------------------------------
    // computeFine logic is that we get the number of days between the users due date
    // and the date of today. if the number of days is greater than 1. We multiply that number
    // of days by one Dollar to calc the fine.
    private double computeFine(Transaction t)
    {
        // debug_date is set to false top of library
        if (DEBUG_DATE)
            return new Random().nextInt(20);

        // calculates the amount of days between now and the issued date
        Period interval = Period.between(LocalDate.now() ,t.getIssueDate());
        // saves the difference of above into a int ms.
        int ms = interval.getDays();
        // if int is <= max amount of days
        if (ms<=14) //change in final
            return 0;
        return ms*1.0;
    }// end of computeFine

    //-----------------------------------------------------------------------------
    // Method loops through arraylist book to find matching bookID's
    // using a enhanced forloop and comparing Object arraylist bookID's to the passed
    // in variable
    public Book getBook(int id) throws SQLException {
        Book temp =books.getBook(id);
        return temp;
    }// end getBook

    //-----------------------------------------------------------------------------
    // method loops through arraylist users to find matching userID
    // using a enhanced forloop and comparing Object arraylist userID's to the passed
    // in variable
    public User getUser(int userID) throws SQLException {
        User temp =users.getUser(userID);
        // return object user
        return temp;
    }// end getUser

    //------------------------------------------------------------------------------
    // this method takes in a userId int. matches the user id with any transactions that
    // have the user id and a true status for books checked out. Then takes bookId from
    // all matches and gets the book names from books
    public ArrayList<Book> userBooks(int userID) throws SQLException {
        // array list to store user checked out books
        ArrayList<Book> userCheckedBooks = new ArrayList<>();
        ArrayList<Book> tempBooks;
        ArrayList<Transaction> tempTrans;
        tempTrans = transactions.getAll();
        // for loop iterating through transactions array list
        for(Transaction trans: tempTrans){
            // if userId matches trans userId and the book is still checked out
            if(userID == trans.getUserID() && trans.isStatus()){
                // for loop iterating through books arraylist
                tempBooks = books.getAll();
                for (Book book:tempBooks) {
                    if (book.getID() == trans.getBookID()) {
                        // match so add the book name to userCheckedBooks by
                        // calling method getBook
                        userCheckedBooks.add(getBook(trans.getBookID()));
                    }// end inner if
                }
            }// end outer if
        }// end outer for
        // return array list
        return userCheckedBooks;
    }// end of userBooks

    //------------------------------------------------------------------------------
    // This method goes through transaction array list and searched for transactions
    // where the book is rented out still.
    public  ArrayList<Transaction> issued (ArrayList temp) throws SQLException {
        ArrayList<Transaction> tempTrans;
        tempTrans= transactions.getCurrent();
        // for-loop to loop through transactions
        for (Transaction trans : tempTrans) {
            // if transactions status is true add the index to arraylist temp
            if(trans.isStatus()){
                temp.add(trans);
            }// end if
        }// end of for loop
        // return temp
        return tempTrans;
    }// end of issued

    //------------------------------------------------------------------------------
    // This method takes in a String and returns an arraylist of any book titles that
    // contain a charSequence of the String passed into the method
    public ArrayList<Book>bookSearch(String searchWord) throws SQLException {
        // creating our temp arraylist to store user names that match criteria
        ArrayList<Book> temp;

        //trimming any whitespace off the end
        String word = searchWord.toLowerCase().trim();
        // if search word is empty return original list of users
        if(searchWord.equals("")){return books.getAll();}

        else{
            temp = books.getByQuery(word);
            // return arraylist temp
            return temp;
        }
    }// end of bookSearch

    //------------------------------------------------------------------------------
    // This method takes in a String and returns an arraylist of any user names that
    // contain a charSequence of the String passed into the method
    public ArrayList<User> userSearch(String searchWord) throws SQLException {
        // creating our temp arraylist to store user names that match criteria
        ArrayList<User> temp;

        //trimming any whitespace off the end
        String word = searchWord.toLowerCase().trim();
        // if search word is empty return original list of users
        if(searchWord.equals("")){return users.getAll();}

        else{
            temp = users.getByQuery(word);
            // return arraylist temp
            return temp;
        }

    }// end of userSearch

    //------------------------------------------------------------------------------
    // this method stores the strings stored in ArrayList msgLog into a concat String
    // called message. Then clears the ArrayList msgLog for future use.
    public String getlog(){
        // initializing string to empty
        String message ="";
        // for loop iterating through ArrayList msgLog
        for(String mes : msgLog){
            // add each string onto the end of message
            message = message +  " " + mes + "\n";
        }
        // clear the array list
        msgLog.clear();
        // return the String message
        return message;
    }// end of getLog
}
