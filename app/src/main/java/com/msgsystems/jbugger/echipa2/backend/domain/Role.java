package com.msgsystems.jbugger.echipa2.backend.domain;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name="id_permission")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "type")
    private String type;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Permission> permissions;

    public Role() { }
    public Role(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }
}
