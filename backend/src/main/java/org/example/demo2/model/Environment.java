package org.example.demo2.model;

import java.util.Date;

public class Environment {
    private int Id;
    private String Name;
    private boolean Protected;
    private Date CreatedAt;

    public Environment(int id, String name, boolean defaultProtected, Date createdAt) {
        Id = id;
        Name = name;
        Protected = defaultProtected;
        CreatedAt = createdAt;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    public boolean isProtected() { return Protected; }

    public void setProtected(boolean defaultProtected) { Protected = defaultProtected; }
}
