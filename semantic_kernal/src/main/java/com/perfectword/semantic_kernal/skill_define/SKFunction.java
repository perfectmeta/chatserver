package com.perfectword.semantic_kernal.skill_define;

import com.perfectword.semantic_kernal.ai.text_completion.CompleteRequestSettings;

import java.lang.reflect.Method;
import java.util.List;

import static com.perfectword.semantic_kernal.Util.nullOrWhitespace;

public class SKFunction {
    private String name;
    private String skillName;
    private String description;
    private boolean isSemantic;
    private CompleteRequestSettings aiRequestSettings;
    private List<ParameterView> parameters;
    public String getName() {
        return name;
    }
    public String getSkillName() {
        return skillName;
    }
    public String getDescription() {
        return description;
    }
    public boolean isSemantic() {
        return isSemantic;
    }
    public CompleteRequestSettings getRequestSettings() {
        return aiRequestSettings;
    }
    public List<ParameterView> getParameters() {
        return parameters;
    }

    public static SKFunction fromNativeMethod(
            Method methodSignature,
            Object methodContainerInstance,
            String skillName
    ) {
        // todo implement this
        if (nullOrWhitespace(skillName)) {
            // skillName = SkillCollection.getGlobalSkill
        }
        return null;
    }
}
