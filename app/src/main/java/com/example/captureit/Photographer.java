package com.example.captureit;

public class Photographer {
    private String id;
    private String name;
//    private double rating;
    private String[] photos;
//    private String[] reviews;
    private String price;
    private String[] services;
    private String locationName;
    private String latitude;
    private String longitude;
    private double experience;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public void setRating(double rating) {
//        this.rating = rating;
//    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setServices(String[] services) {
        this.services = services;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

//    public double getRating() {
//        return rating;
//    }

    public String[] getPhotos() {
        return photos;
    }

    public String getPrice() {
        return price;
    }

    public String[] getServices() {
        return services;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public double getExperience() {
        return experience;
    }


}
