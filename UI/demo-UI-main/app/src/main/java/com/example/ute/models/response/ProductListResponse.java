package com.example.ute.models.response;

import com.example.ute.models.Product;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductListResponse {
    @SerializedName("products")
    private List<Product> products;
    
    @SerializedName("content")
    private List<Product> content;
    
    @SerializedName("totalElements")
    private Long totalElements;
    
    @SerializedName("totalPages")
    private Integer totalPages;
    
    @SerializedName("currentPage")
    private Integer currentPage;
    
    @SerializedName("pageSize")
    private Integer pageSize;

    public List<Product> getProducts() {
        return products != null ? products : content;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

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

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
