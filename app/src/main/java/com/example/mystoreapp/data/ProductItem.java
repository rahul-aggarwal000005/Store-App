package com.example.mystoreapp.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Products")
public class ProductItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    private String productName;

    @ColumnInfo(name = "brand")
    private String brandName;

    @ColumnInfo(name = "inStock")
    private boolean inStock;

    @ColumnInfo(name = "weight")
    private int weight;

    @ColumnInfo(name = "price")
    private int price;

    public ProductItem() {}

    public ProductItem(String productName,String brandName,boolean inStock,int weight,int price){
        this.productName = productName;
        this.brandName = brandName;
        this.inStock = inStock;
        this.weight = weight;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public boolean isInStock() {
        return inStock;
    }

    public int getWeight() {
        return weight;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
