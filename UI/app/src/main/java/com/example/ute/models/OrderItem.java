package com.example.ute.models;

import com.google.gson.annotations.SerializedName;

public class OrderItem {
    @SerializedName("id")
    private Long id;
    
    @SerializedName("productId")
    private Long productId;
    
    @SerializedName("product")
    private Product product;
    
    @SerializedName("productName")
    private String productName;
    
    @SerializedName("productImage")
    private String productImage;
    
    @SerializedName("quantity")
    private Integer quantity;
    
    @SerializedName("price")
    private Double price;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getProductName() {
        if (productName != null) return productName;
        if (product != null) return product.getName();
        return "";
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        if (productImage != null) return productImage;
        if (product != null) return product.getThumbnailUrl();
        return null;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Double getSubtotal() {
        if (price != null && quantity != null) {
            return price * quantity;
        }
        return 0.0;
    }
}
