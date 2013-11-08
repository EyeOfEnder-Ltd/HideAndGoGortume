package com.eyeofender.gortume.util;

import javax.persistence.Id;

import com.avaje.ebean.validation.Length;
import com.avaje.ebean.validation.NotNull;

public class Passes {

    @Id
    @Length(max = 16)
    private String name;

    @NotNull
    private int passes;

    public String getName() {
        return name;
    }

    public int getPasses() {
        return passes;
    }

    public boolean hasPass() {
        return passes > 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasses(int passes) {
        this.passes = passes;
    }

    public void removePass() {
        this.passes--;
    }

}
