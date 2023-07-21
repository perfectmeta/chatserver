package com.perfectword.semantic_kernel.skill_define;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SKFunction {
    String name() default "";  // 如果为空，就读method name

    String description();
}
