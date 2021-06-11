package com.example.mystoreapp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class ProductRepository {

    /* Setting the Dao object */
    private final ProductItemDao productItemDao;

    /* Getting the Data */
    private final LiveData<List<ProductItem>> getAllItems;


    ProductRepository(Application application){
        ProductItemDatabase database = ProductItemDatabase.getDatabase(application);
        productItemDao = database.productItemDao();
        getAllItems = productItemDao.getAll();
    }


    /*  Return all list of the product items */
    LiveData<List<ProductItem>> getGetAll(){
        return getAllItems;
    }

    /* In order to call the insert operation in the background thread we wil call the Executor pool */
    void insert(ProductItem item){
        ProductItemDatabase.databaseWriteExecutor.execute(()-> productItemDao.insert(item));
    }

    void delete(ProductItem item){
        ProductItemDatabase.databaseWriteExecutor.execute(()-> productItemDao.delete(item));
    }

    void update(ProductItem item){
        ProductItemDatabase.databaseWriteExecutor.execute(()-> productItemDao.update(item));
    }


}
