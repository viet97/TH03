package com.example.gallery2;

public class ImageItem {
    private int id;
    private String uri;
    private String type;

    public ImageItem(int id, String uri, String type) {
        this.id = id;
        this.uri = uri;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
