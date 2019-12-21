package app.domain.models.service;

import app.domain.entities.Claz;

import java.math.BigDecimal;

public class JobApplicationServiceModel {
    private String id;
    private String name;
    private Claz claz;
    private String level;

    public JobApplicationServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Claz getClaz() {
        return claz;
    }

    public void setClaz(Claz claz) {
        this.claz = claz;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}