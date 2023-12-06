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

    }
    public User(String _fName, String _lName, String _mobileNr, String _email){

        this.firstName = _fName;
        this.lastName = _lName;
        this.mobileNumber = _mobileNr;
        this.email = _email;
        this.status = userStatus.OFFLINE;

    }

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

    public void setUsername(String newUsername){
        this.username = newUsername;
    }
}

enum userStatus{
    ONLINE,
    OFFLINE,
    AWAY
}
