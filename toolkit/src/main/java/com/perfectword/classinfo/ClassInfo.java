package com.perfectword.classinfo;


import java.util.LinkedHashMap;

public class ClassInfo {
    public Type type;
    public Type elementType ;
    public LinkedHashMap<String, ClassInfo> attributes;

    public ClassInfo() {
        attributes = new LinkedHashMap<>();
    }

    @Override
    public boolean equals(Object o1) {
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
