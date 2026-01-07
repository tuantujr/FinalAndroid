package com.example.ute.models;

import com.google.gson.annotations.SerializedName;

public class CartItem {
    @SerializedName("id")
    private Long id;
    
    @SerializedName("productId")
    private Long productId;
    
    @SerializedName("product")
    private Product product;
    
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
        if (product != null && quantity != null) {
            return product.getPrice() * quantity;
        }
        return 0.0;
    }
}
