package com.example.mystoreapp.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private final ProductRepository productRepository;

    private final LiveData<List<ProductItem>> allItems;


    public ProductViewModel(@NonNull @org.jetbrains.annotations.NotNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
        allItems = productRepository.getGetAll();
    }

    public LiveData<List<ProductItem>> getAllItems(){
        return allItems;
    }


    public void insert(ProductItem item){
        productRepository.insert(item);
    }

    public void delete(ProductItem item){
        productRepository.delete(item);
    }

    public void update(ProductItem item) { productRepository.update(item);}
}
