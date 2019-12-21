package app.domain.models.binding;

import app.domain.entities.Claz;

import java.math.BigDecimal;

public class JobCreateBindingModel {
    private String name;
    private Claz claz;
    private String level;

    public JobCreateBindingModel() {
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