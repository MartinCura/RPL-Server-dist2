package com.rpl.service.util;

import java.util.Arrays;

public class ArrayUtils {

    public static String[] addElement(String[] array, String... values) {
        int len = array.length;
        array = Arrays.copyOf(array, array.length + values.length);
        System.arraycopy(values, 0, array, len, values.length);
        return array;
    }

}
