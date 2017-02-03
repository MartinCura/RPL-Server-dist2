package com.rpl.service.util;

import java.util.Arrays;

public class ArrayUtils {

    public static String[] addElement(String[] array, String... values) {
        int len = array.length;
        array = Arrays.copyOf(array, array.length + values.length);
        for (int i = 0; i < values.length; i++) {
            array[len + i] = values[i];
        }
        return array;
    }
}
