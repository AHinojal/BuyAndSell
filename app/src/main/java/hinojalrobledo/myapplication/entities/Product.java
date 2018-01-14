package hinojalrobledo.myapplication.entities;

import android.media.Image;

import java.io.Serializable;

public class Product implements Serializable {

    private Integer id;
    private String photo;
    private String name;
    private String description;
    private String price;
    private String emailSeller;

    public Product(){

    }

    public Product(Integer id, String photo, String name, String description, String price, String emailSeller){
        this.id = id;
        this.photo = photo;
        this.name = name;
        this.description = description;
        this.price = price;
        this.emailSeller = emailSeller;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEmailSeller() {
        return emailSeller;
    }

    public void setEmailSeller(String emailSeller) {
        this.emailSeller = emailSeller;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
