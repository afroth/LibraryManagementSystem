package com.library.libraymanagement;

import java.time.LocalDate;

public class User {
    private int ID;
    private String name;
    private String email;
    private String address;
    private LocalDate dateOfBirth;
    private boolean isStudent;
    private double balance = 0.0;
    private static int usersAdded;

    public User(String name, String email, String address, LocalDate dateOfBirth, boolean isStudent){
        setName(name);
        setEmail(email);
        setAddress(address);
        setDateOfBirth(dateOfBirth);
        setStudent(isStudent);

    }
    public User(int ID, String name, String email, String address, LocalDate dateOfBirth, boolean isStudent,double balance){
        this.ID = ID;
        setName(name);
        setEmail(email);
        setAddress(address);
        setDateOfBirth(dateOfBirth);
        setStudent(isStudent);
        setBalance(balance);

    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

    // toString method for output, anytime a user wants to view the data of the object
    @Override
    public String toString() {
        return
                "ID: " + getID() +" Name:" + getName();
    }
}
