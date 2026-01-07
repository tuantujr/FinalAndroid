package com.example.ute.models.response;

import com.example.ute.models.Product;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Product List Response matching backend format:
 * { "success": true, "message": "...", "data": [ products ], "metadata": { "pagination": {...} } }
 */
public class ProductListResponse {
    @SerializedName("success")
    private Boolean success;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("data")
    private List<Product> data;
    
    @SerializedName("metadata")
    private Metadata metadata;

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

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }
    
    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
    
    public boolean isSuccess() {
        return success != null && success;
    }
    
    // Convenience method
    public List<Product> getProducts() {
        return data;
    }

    public void setProducts(List<Product> products) {
        this.data = products;
    }
    
    // Get pagination info
    public Pagination getPagination() {
        return metadata != null ? metadata.getPagination() : null;
    }
    
    public int getTotalPages() {
        Pagination p = getPagination();
        return p != null && p.getTotalPages() != null ? p.getTotalPages() : 1;
    }
    
    public int getCurrentPage() {
        Pagination p = getPagination();
        return p != null && p.getPage() != null ? p.getPage() : 1;
    }
    
    public long getTotalItems() {
        Pagination p = getPagination();
        return p != null && p.getTotalItems() != null ? p.getTotalItems() : 0;
    }
    
    /**
     * Metadata wrapper containing pagination
     */
    public static class Metadata {
        @SerializedName("pagination")
        private Pagination pagination;
        
        // Legacy fields for backwards compatibility
        @SerializedName("totalItems")
        private Long totalItems;
        
        @SerializedName("totalPages")
        private Integer totalPages;
        
        @SerializedName("currentPage")
        private Integer currentPage;
        
        @SerializedName("itemsPerPage")
        private Integer itemsPerPage;

        public Pagination getPagination() {
            return pagination;
        }

        public void setPagination(Pagination pagination) {
            this.pagination = pagination;
        }

        public Long getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(Long totalItems) {
            this.totalItems = totalItems;
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

        public Integer getItemsPerPage() {
            return itemsPerPage;
        }

        public void setItemsPerPage(Integer itemsPerPage) {
            this.itemsPerPage = itemsPerPage;
        }
    }
    
    /**
     * Pagination info from backend
     */
    public static class Pagination {
        @SerializedName("page")
        private Integer page;
        
        @SerializedName("limit")
        private Integer limit;
        
        @SerializedName("totalItems")
        private Long totalItems;
        
        @SerializedName("totalPages")
        private Integer totalPages;

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public Long getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(Long totalItems) {
            this.totalItems = totalItems;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }
    }
}
