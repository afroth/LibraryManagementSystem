package com.library.libraymanagement;

import java.time.LocalDate;

public class Transaction {
    private int bookID;
    private int userID;
    private LocalDate issueDate;
    private boolean status;

    // constructor for predefined objects
    public Transaction(int bookID, int userID){
        this.bookID = bookID;
        this.userID = userID;
        this.issueDate = LocalDate.now();
        this.status=true;
    }// end Constructor

    // constructor for predefined objects
    public Transaction(int bookID, int UserID, LocalDate date, boolean status){
        setBookID(bookID); // sets bookID for the Object
        setUserID(UserID); // sets userID for the Object
        setIssueDate(date);
        setStatus(status);

    }// end Constructor

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public LocalDate getIssueDate() { return issueDate; }

    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
