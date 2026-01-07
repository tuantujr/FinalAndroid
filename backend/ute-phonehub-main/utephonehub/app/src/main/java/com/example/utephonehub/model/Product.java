package com.example.utephonehub.model;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private Double price;

    @SerializedName("thumbnailUrl")
    private String thumbnailUrl;

    @SerializedName("stockQuantity")
    private Integer stockQuantity;

    @SerializedName("category")
    private Category category;

    @SerializedName("brand")
    private Brand brand;

    @SerializedName("description")
    private String description;

    // Constructors
    public Product() {}

    public Product(String name, Double price, String thumbnailUrl, Integer stockQuantity) {
        this.name = name;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
