package com.msgsystems.jbugger.echipa2.backend.domain;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @Column(name="id_notification")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="type")
    private String type;

    @Column(name="url")
    private String url;

    @Column(name="message")
    private String message;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> users;

    @Column(name="date")
    private Date date;
}
