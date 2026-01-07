package com.example.ute.models;

import com.google.gson.annotations.SerializedName;

public class Review {
    private Long id;
    private Long productId;
    private Integer rating;  // 1-5
    private String comment;
    @SerializedName("likeCount")
    private Integer likes;
    private String createdAt;
    private String updatedAt;
    private User user;
    private Boolean isLiked;  // Current user liked this review or not

    // Inner User class
    public static class User {
        @SerializedName("id")
        private Long id;
        @SerializedName("fullName")
        private String fullName;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }

    // Constructor
    public Review() {
    }

    public Review(Long id, Long productId, Integer rating, String comment) {
        this.id = id;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserName() {
        return user != null ? user.getFullName() : "Unknown";
    }

    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }
}
