package com.msgsystems.jbugger.echipa2.backend.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name="id_permission")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "type")
    private String type;

    public Role() { }
    public Role(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
