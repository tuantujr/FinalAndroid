package com.example.ute.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Response from /api/v1/cart GET endpoint
 * Matches backend CartService.buildCartResponse() format
 */
public class CartDataResponse {
    @SerializedName("items")
    private List<CartItemData> items;
    
    @SerializedName("totalItems")
    private Integer totalItems;
    
    @SerializedName("totalPrice")
    private Double totalPrice;

    public List<CartItemData> getItems() {
        return items;
    }

    public void setItems(List<CartItemData> items) {
        this.items = items;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Inner class for cart item data
    public static class CartItemData {
        @SerializedName("cartItemId")
        private Long cartItemId;
        
        @SerializedName("productId")
        private Long productId;
        
        @SerializedName("productName")
        private String productName;
        
        @SerializedName("thumbnailUrl")
        private String thumbnailUrl;
        
        @SerializedName("price")
        private Double price;
        
        @SerializedName("quantity")
        private Integer quantity;
        
        @SerializedName("lineTotal")
        private Double lineTotal;

        public Long getCartItemId() {
            return cartItemId;
        }

        public void setCartItemId(Long cartItemId) {
            this.cartItemId = cartItemId;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Double getLineTotal() {
            return lineTotal;
        }

        public void setLineTotal(Double lineTotal) {
            this.lineTotal = lineTotal;
        }
    }
}
