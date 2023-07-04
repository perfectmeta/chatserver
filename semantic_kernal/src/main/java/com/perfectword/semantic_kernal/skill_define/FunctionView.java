package com.perfectword.semantic_kernal.skill_define;

import java.util.ArrayList;
import java.util.List;

public class FunctionView {
    private String name = "";
    private String skillName = "";
    private String description = "";
    private boolean semantic;
    private boolean asynchronous;
    private List<ParameterView> parameters = new ArrayList<>();

    public FunctionView() {}

    public FunctionView(
            String name,
            String skillName,
            String description,
            List<ParameterView> parameters,
            boolean isSemantic,
            boolean isAsynchronous
    ) {
        this.name = name;
        this.skillName = skillName;
        this.description = description;
        this.parameters = parameters;
        this.semantic = isSemantic;
        this.asynchronous = isAsynchronous;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSemantic() {
        return semantic;
    }

    public void setSemantic(boolean semantic) {
        this.semantic = semantic;
    }

    public boolean isAsynchronous() {
        return asynchronous;
    }

    public void setAsynchronous(boolean asynchronous) {
        this.asynchronous = asynchronous;
    }

    public List<ParameterView> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterView> parameters) {
        this.parameters = parameters;
    }

}
