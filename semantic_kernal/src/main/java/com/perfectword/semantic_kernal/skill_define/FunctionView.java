package com.perfectword.semantic_kernal.skill_define;

import java.util.List;

public record FunctionView (String name, String skillName, String description, boolean isSemantic, List<ParameterView> parameters){
}
