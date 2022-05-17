package com.library.libraymanagement;

public class Book {
    private int ID;
    private String name;
    private String author;
    private String publisher;
    private String genre;
    private String ISBN;
    private long year;
    private static int booksAdded;

    // constructor for predefined objects
    public Book(String name, String author, String publisher, String genre, String ISBN, long year){

        setName(name); // sets book name
        setAuthor(author); // sets author name
        setPublisher(publisher); // sets publisher name
        setGenre(genre);// sets genre
        setISBN(ISBN);// sets ISBN
        setYear(year); // sets year book was make/published

    }
    public Book(int ID, String name, String author, String publisher, String genre, String ISBN, long year){
        this.ID = ID;
        setName(name); // sets book name
        setAuthor(author); // sets author name
        setPublisher(publisher); // sets publisher name
        setGenre(genre);// sets genre
        setISBN(ISBN);// sets ISBN
        setYear(year); // sets year book was make/published

    }
    public boolean equals(Book book){

        if(this.getID() == book.getID()){
            return true;
        }
        return false;
    }
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    // toString method for output, anytime a user wants to view the data of the object
    @Override
    public String toString() {
        return "ID: " + getID() + " Name: " + getName()+ " Author: " + getAuthor()+ " publisher: "
                + getPublisher()+ " genre: " + getGenre()+ " ISBN: " + getISBN()+ " year: " + getYear();
    }
}
