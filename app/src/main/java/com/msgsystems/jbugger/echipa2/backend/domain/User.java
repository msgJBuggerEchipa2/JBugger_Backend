package com.msgsystems.jbugger.echipa2.backend.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name="id_user")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="firstName")
    private String firstName;
    @Column(name="lastName")
    private String lastName;
    @Column(name="mobileNumber")
    private String mobileNumber;
    @Column(name="email")
    private String email;
    @Column(name="username")
    private String username = "";
    @Column(name="password")
    private String password;

    @Column(name="status")
    private userStatus status;

    public User(){
        this.status = userStatus.ACTIVE;
    }
    public User(String _fName, String _lName, String _mobileNr, String _email){

        this.firstName = _fName;
        this.lastName = _lName;
        this.mobileNumber = _mobileNr;
        this.email = _email;
        this.status = userStatus.ACTIVE;

    }
    // Getters and Setters
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public String getMobileNumber(){
        return this.mobileNumber;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPassword(){
        return this.password;
    }
    public String getUsername(){
        return this.username;
    }

    public userStatus getStatus(){ return this.status;}

    public void setFirstName(String newFirstName){
        this.firstName = newFirstName;
    }
    public void setLastName(String newLastName){
        this.lastName = newLastName;
    }
    public void setMobileNumber(String newMobileNumber){
        this.mobileNumber = newMobileNumber;
    }
    public void setEmail(String newEmail){
        this.email = newEmail;
    }
    public void setPassword(String newPassword){
        this.password = newPassword;
    }
    public void setUsername(String newUsername){
        this.username = newUsername;
    }

    public void setStatus(userStatus newStatus){ this.status = newStatus;}
}

enum userStatus{
    ACTIVE,
    INACTIVE
}
