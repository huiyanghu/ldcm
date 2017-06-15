package com.lvdun.util;


public class StringUtil {
    public static boolean isNotEmpty(Object object) {
        if (object != null && !"".equals(object)) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Object object) {
        if (object == null || "".equals(object)) {
            return true;
        }
        return false;
    }

}
