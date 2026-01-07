package com.utephonehub.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.utephonehub.model.Product;
import com.utephonehub.repository.ProductRepository;
import java.util.List;

public class ProductViewModel extends ViewModel {
    private ProductRepository repository;
    private LiveData<List<Product>> productsList;
    private LiveData<Product> productDetail;

    public ProductViewModel() {
        repository = new ProductRepository();
    }

    public LiveData<List<Product>> getProducts(int page, int size) {
        if (productsList == null) {
            productsList = repository.getProducts(page, size);
        }
        return productsList;
    }

    public void loadProducts(int page, int size) {
        productsList = repository.getProducts(page, size);
    }

    public LiveData<Product> getProductDetail(Long id) {
        if (productDetail == null) {
            productDetail = repository.getProductById(id);
        }
        return productDetail;
    }

    public void loadProductDetail(Long id) {
        productDetail = repository.getProductById(id);
    }

    public LiveData<List<Product>> searchProducts(String keyword, int page, int size) {
        return repository.searchProducts(keyword, page, size);
    }
}
