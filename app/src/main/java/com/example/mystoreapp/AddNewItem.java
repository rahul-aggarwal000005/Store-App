package com.example.mystoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mystoreapp.data.ProductItem;


public class AddNewItem extends AppCompatActivity {

    EditText productName, brandName, inStock, weight,price;

    public static final String PRODUCT_NAME = "PRODUCT_NAME";
    public static final String BRAND_NAME = "BRAND_NAME";
    public static final String IN_STOCK_NAME = "IN_STOCK_NAME";
    public static final String WEIGHT_NAME = "WEIGHT_NAME";
    public static final String PRICE_NAME = "PRICE_NAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        productName = findViewById(R.id.product_name);
        brandName = findViewById(R.id.brand_name);
        inStock = findViewById(R.id.in_stock);
        weight = findViewById(R.id.weight_value);
        price = findViewById(R.id.price_value);

        Button addButton = findViewById(R.id.addButton);
        
        addButton.setOnClickListener(v -> insert());
    }
    
    public void insert(){

        Intent replyIntent = new Intent();

        final String ProductName = productName.getText().toString().trim();
        final String BrandName = brandName.getText().toString().trim();
        final String InStock = inStock.getText().toString().trim();
        final String Weight = weight.getText().toString().trim();
        final String Price = price.getText().toString().trim();

        if(ProductName.isEmpty()){
            productName.setError("Name Required");
            productName.requestFocus();
            setResult(RESULT_CANCELED,replyIntent);
            return;
        }

        if(BrandName.isEmpty()){
            brandName.setError("Brand Required");
            brandName.requestFocus();
            setResult(RESULT_CANCELED,replyIntent);
            return;
        }

        if(InStock.isEmpty() || (!InStock.toUpperCase().equals("YES") && !InStock.toUpperCase().equals("NO"))){
            inStock.setError("In Stock Required");
            inStock.requestFocus();
            setResult(RESULT_CANCELED,replyIntent);
            return;
        }

        if(Weight.isEmpty()){
            weight.setError("Weight Required");
            weight.requestFocus();
            setResult(RESULT_CANCELED,replyIntent);
            return;
        }

        if(Price.isEmpty()){
            price.setError("Price Required");
            price.requestFocus();
            setResult(RESULT_CANCELED,replyIntent);
            return;
        }

        ProductItem item = new ProductItem();
        item.setProductName(ProductName);
        item.setBrandName(BrandName);
        item.setInStock((InStock).toUpperCase().equals("YES"));
        item.setWeight(Integer.parseInt(Weight));
        item.setPrice(Integer.parseInt(Price));

        replyIntent.putExtra(PRODUCT_NAME,item.getProductName());
        replyIntent.putExtra(BRAND_NAME,item.getBrandName());
        replyIntent.putExtra(IN_STOCK_NAME,item.isInStock());
        replyIntent.putExtra(WEIGHT_NAME,item.getWeight());
        replyIntent.putExtra(PRICE_NAME,item.getPrice());

        setResult(RESULT_OK,replyIntent);

        Toast.makeText(this, "Item is Added Successfully to the database", Toast.LENGTH_SHORT).show();
        finish();
    }
}
