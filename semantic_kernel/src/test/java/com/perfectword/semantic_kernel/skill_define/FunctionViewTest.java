package com.perfectword.semantic_kernel.skill_define;

import static org.junit.jupiter.api.Assertions.*;

class FunctionViewTest {

    @SKFunction(description = "get time")
    public static String GetTime(@SKDescription(description = "year") String year){
        return "2023";
    }



}