package org.example.demo2.model;

public class Feature {
    private int Id;
    private String Identifier;
    private String Name;
    private String Description;
    private boolean Enabled;

    public Feature() {}

    public Feature(int id, String identifier, String name, String description, Boolean enabled) {
        Id = id;
        Identifier = identifier;
        Name = name;
        Description = description;
        Enabled = enabled;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean enabled) {
        Enabled = enabled;
    }
}
