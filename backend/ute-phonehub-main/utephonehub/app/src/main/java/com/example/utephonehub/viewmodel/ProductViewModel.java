package com.example.utephonehub.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.utephonehub.model.Product;
import com.example.utephonehub.model.ProductListResponse;
import com.example.utephonehub.repository.ProductRepository;

public class ProductViewModel extends ViewModel {
    private ProductRepository productRepository;
    private LiveData<ProductListResponse.ProductPageData> productsLiveData;
    private LiveData<Product> productDetailLiveData;

    public ProductViewModel() {
        productRepository = new ProductRepository();
    }

    public void loadProducts(int page, int size) {
        productsLiveData = productRepository.getProducts(page, size);
    }

    public LiveData<ProductListResponse.ProductPageData> getProducts() {
        if (productsLiveData == null) {
            loadProducts(0, 10);
        }
        return productsLiveData;
    }

    public void loadProductDetail(Long productId) {
        productDetailLiveData = productRepository.getProductById(productId);
    }

    public LiveData<Product> getProductDetail() {
        return productDetailLiveData;
    }

    public void searchProducts(String keyword, int page, int size) {
        productsLiveData = productRepository.searchProducts(keyword, page, size);
    }
}
