package com.example.utephonehub.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ProductListResponse {
    @SerializedName("data")
    private ProductPageData data;

    @SerializedName("success")
    private Boolean success;

    @SerializedName("message")
    private String message;

    // Constructors
    public ProductListResponse() {}

    public ProductListResponse(ProductPageData data) {
        this.data = data;
    }

    // Getters and Setters
    public ProductPageData getData() {
        return data;
    }

    public void setData(ProductPageData data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Nested class for paginated data
    public static class ProductPageData {
        @SerializedName("content")
        private List<Product> content;

        @SerializedName("totalElements")
        private Long totalElements;

        @SerializedName("totalPages")
        private Integer totalPages;

        @SerializedName("currentPage")
        private Integer currentPage;

        // Constructors
        public ProductPageData() {}

        public ProductPageData(List<Product> content, Long totalElements, Integer totalPages, Integer currentPage) {
            this.content = content;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
            this.currentPage = currentPage;
        }

        // Getters and Setters
        public List<Product> getContent() {
            return content;
        }

        public void setContent(List<Product> content) {
            this.content = content;
        }

        public Long getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(Long totalElements) {
            this.totalElements = totalElements;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }
    }
}
