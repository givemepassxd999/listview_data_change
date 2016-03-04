package com.example.givemepass.listviewdatachangedemo;

import java.util.ArrayList;
import java.util.List;

public class MyData {
    private static MyData ourInstance = new MyData();
    private List<String> mData;
    public static MyData getInstance() {
        return ourInstance;
    }

    private MyData() {
        mData = new ArrayList<>();
    }

    public static List<String> getData(){
//        return ourInstance.mData;
        List<String> list = new ArrayList<>();
        list.addAll(ourInstance.mData);
        return list;
    }

    public static void clear(){
        ourInstance.mData.clear();
    }
    public static void removeItem(int i){
        ourInstance.mData.remove(i);
    }
    public static void addItem(String s){
        if(s != null) {
            ourInstance.mData.add(s);
        }
    }
}
