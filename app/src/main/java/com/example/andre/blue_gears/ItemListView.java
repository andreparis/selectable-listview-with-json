package com.example.andre.blue_gears;

/**
 * Created by Andre on 6/1/2016.
 */
public class ItemListView {

    private int id;
    private  String name;
    private String description;
    private String datetime;
    private String datetime_selected;
    private byte[] img;
    private String imgWeb;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public String getDatetime() {
        return datetime;
    }

    public byte[] getImg() {
        return img;
    }

    public String getDatetime_selected() {
        return datetime_selected;
    }

    public void setDatetime_selected(String datetime_selected) {
        this.datetime_selected = datetime_selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgWeb() {
        return imgWeb;
    }

    public void setImgWeb(String imgWeb) {
        this.imgWeb = imgWeb;
    }
}
