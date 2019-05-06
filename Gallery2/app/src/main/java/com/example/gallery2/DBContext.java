package com.example.gallery2;

import java.util.ArrayList;

public class DBContext {
    private static final DBContext instance = new DBContext();
    private ArrayList<ImageItem> listImages;

    private DBContext(){
        listImages = new ArrayList<>();
    }

    public static DBContext getInstance() {
        return instance;
    }

    public ArrayList<ImageItem> getListImages() {
        return listImages;
    }

    public void setListImages(ArrayList<ImageItem> listImages) {
        this.listImages = listImages;
    }
}
