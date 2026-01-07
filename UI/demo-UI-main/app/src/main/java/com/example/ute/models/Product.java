package com.example.ute.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {
    @SerializedName("id")
    private Long id;
    
    @SerializedName("name")
    private String name;
    
    @SerializedName("description")
    private String description;
    
    @SerializedName("price")
    private Double price;
    
    @SerializedName("originalPrice")
    private Double originalPrice;
    
    @SerializedName("stockQuantity")
    private Integer stockQuantity;
    
    @SerializedName("specifications")
    private String specifications;
    
    @SerializedName("category")
    private Category category;
    
    @SerializedName("brand")
    private Brand brand;
    
    @SerializedName("images")
    private List<ProductImage> images;
    
    @SerializedName("thumbnailUrl")
    private String thumbnailUrl;
    
    @SerializedName("averageRating")
    private Double averageRating;
    
    @SerializedName("reviewCount")
    private Integer reviewCount;
    
    @SerializedName("status")
    private String status;
    
    @SerializedName("createdAt")
    private String createdAt;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
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

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public String getThumbnailUrl() {
        if (thumbnailUrl != null) {
            return thumbnailUrl;
        }
        if (images != null && !images.isEmpty()) {
            return images.get(0).getImageUrl();
        }
        return null;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Double getAverageRating() {
        return averageRating != null ? averageRating : 0.0;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getReviewCount() {
        return reviewCount != null ? reviewCount : 0;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    public boolean isInStock() {
        return stockQuantity != null && stockQuantity > 0;
    }
    
    public boolean hasDiscount() {
        return originalPrice != null && originalPrice > price;
    }
    
    public int getDiscountPercent() {
        if (hasDiscount()) {
            return (int) ((1 - price / originalPrice) * 100);
        }
        return 0;
    }
}
