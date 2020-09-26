package com.sourav.rxnet.adapter;

public class MenuList {
    String title;
    int image;
    String url;
    String type;

    public MenuList() {
    }

    public MenuList(String title, int image, String url, String type) {
        this.title = title;
        this.image = image;
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
