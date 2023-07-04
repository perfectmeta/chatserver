package com.perfectword.semantic_kernal;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static boolean nullOrWhitespace(String content) {
        return content == null || content.strip().length() == 0;
    }

    public static <T> T[] toArray(Iterable<T> iterable) {
        List<T> tempList = new ArrayList<>();
        if (iterable instanceof List) {
            tempList = (List<T>)iterable;
        } else {
            for (T t : iterable) {
                tempList.add(t);
            }
        }
        return (T[]) tempList.toArray();
    }
}