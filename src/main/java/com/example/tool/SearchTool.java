package com.example.tool;

public class SearchTool {

    public static String nodeSearch(String En)
    {
        String str=En.substring(En.indexOf("(")+1,En.indexOf(")"));
        return En.replace(str,DictTool.searchZhFrom(str));
    }
}
