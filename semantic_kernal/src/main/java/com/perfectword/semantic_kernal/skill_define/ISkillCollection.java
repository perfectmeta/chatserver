package com.perfectword.semantic_kernal.skill_define;

public interface ISkillCollection extends IReadOnlySkillCollection{
    IReadOnlySkillCollection getReadOnlySkillCollection();
    ISkillCollection addFunction(ISKFunction functionInstance);
}
