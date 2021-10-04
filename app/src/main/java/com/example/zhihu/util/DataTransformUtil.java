package com.example.zhihu.util;

public class DataTransformUtil {
    public static String convertToString(String[] array){
        StringBuilder builder = new StringBuilder();
        if (array != null && array.length > 0){
            for(String item : array){
                builder.append(item).append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            return builder.toString();
        }
        return null;
    }

    public static String[] convertToArray(String text){
        if (text != null)
            return text.split(",");
        else
            return null;
    }
}
