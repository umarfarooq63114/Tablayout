package com.codingdemos.tablayout.Model;

import com.google.gson.annotations.SerializedName;
public class Technician {
    @SerializedName("t_name")
    String t_name;
    @SerializedName("id")
    int id;
    @SerializedName("cnic")
    String cnic;
    @SerializedName("email")
    String email;
    @SerializedName("address")
    String address;
    @SerializedName("status")
    int status;
    @SerializedName("phone")
    String phone;
    @SerializedName("experience")
    String experience;
    @SerializedName("rating")
    double rating;

    @SerializedName("specaility_id")
    int specaility_id;
    @SerializedName("password")
    String password;
    @SerializedName("image")
    String image;
    @SerializedName("name")
    String name;

    public Technician(String t_name, String cnic, String address,
                      String email, String password, int status, String phone,
                      String experience, double rating, int specaility_id, String image) {
        this.t_name = t_name;
        this.cnic = cnic;
        this.address = address;
        this.email = email;
        this.password=password;
        this.status = status;
        this.phone = phone;
        this.experience = experience;
        this.rating = rating;
        this.image=image;

        this.specaility_id = specaility_id;
    }

    public String getName() {
        return t_name;
    }
    public String getSpecName() {
        return name;
    }
    public String getCnic() {
        return cnic;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public int getStatus() {
        return status;
    }

    public String getPhone() {
        return phone;
    }

    public String getExperience() {
        return experience;
    }

    public double getRating() {
        return rating;
    }

    public String getPassword() { return password; }

    public int getSpeciality() {
        return specaility_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
