package com.example.mystoreapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductItemDao {

    @Query("SELECT * FROM Products")
    LiveData<List<ProductItem>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ProductItem item);

    @Delete
    void delete(ProductItem item);

    @Update
    void update(ProductItem item);

}
