package com.hld.utils;

/**
 * @description:
 * @author: boood
 * @time: 2020/11/13 10:24
 */
public class XssFilter {
    public static String filterWord(String input) {
        String[] words = new String[]{"<", ">", "\"", "\'", "=", "(", ")"};
        for (String word : words) {
            input = input.replace(word, "");
        }
        return input;
    }
}
