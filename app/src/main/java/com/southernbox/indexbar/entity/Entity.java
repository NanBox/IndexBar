package com.southernbox.indexbar.entity;

/**
 * Created by nanquan.lin on 2016/10/25 0025.
 */

public class Entity {

    private String name;
    private String firstword;
    private boolean isIndex;

    public Entity() {
    }

    public Entity(String name, String firstword) {
        this.name = name;
        this.firstword = firstword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstword() {
        return firstword;
    }

    public void setFirstword(String firstword) {
        this.firstword = firstword;
    }

    public boolean isIndex() {
        return isIndex;
    }

    public void setIndex(boolean index) {
        isIndex = index;
    }
}
