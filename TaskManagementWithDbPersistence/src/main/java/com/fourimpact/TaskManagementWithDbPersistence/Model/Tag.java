package com.fourimpact.TaskManagementWithDbPersistence.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tag")
public class Tag {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Task> tasks = new ArrayList<>();

    // Constructors
    public Tag() {}

    public Tag(String name) {
        this.name = name;
    }

    // Getter and Setters


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
