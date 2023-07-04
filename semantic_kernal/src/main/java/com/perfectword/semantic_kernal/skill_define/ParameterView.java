package com.perfectword.semantic_kernal.skill_define;

public class ParameterView {

    private String name = "";
    private String description = "";
    private String defaultValue = "";

    public ParameterView() {

    }

    public ParameterView(String name, String description, String defaultValue) {
        this.name = name;
        this.description = description;
        this.defaultValue = defaultValue;
    }
}
