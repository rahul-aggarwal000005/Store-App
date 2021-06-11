package com.example.mystoreapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.mystoreapp.data.ProductItem;
import com.example.mystoreapp.data.ProductViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity implements ProductClickListener {

    private static final int NEW_PRODUCT_ACTIVITY_CODE = 1;
    private static final int EDIT_PRODUCT_ACTIVITY_CODE = 2;

    public static final String PRODUCT_NAME = "PRODUCT_NAME";
    public static final String BRAND_NAME = "BRAND_NAME";
    public static final String IN_STOCK_NAME = "IN_STOCK_NAME";
    public static final String WEIGHT_NAME = "WEIGHT_NAME";
    public static final String PRICE_NAME = "PRICE_NAME";
    public static final String ITEM_ID = "ITEM_ID";

    ProductViewModel productViewModel;

    /* Product adapter used in the recycler view */
    ProductAdapter productAdapter = new ProductAdapter(this,this);

    ImageView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        empty = findViewById(R.id.empty);

        /* button to add new Items in the list */
        FloatingActionButton fab = findViewById(R.id.fab);

        /* Recycler view in order to populate the data */
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(productAdapter);

        /* Observer of the View Model */
        productViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ProductViewModel.class);

        /* Observe the change in the items */
        productViewModel.getAllItems().observe(this, productItems -> productAdapter.updateItems(productItems));

        /* Deleting the item from the table using swipe */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                int index = viewHolder.getAdapterPosition();
                productViewModel.delete(productAdapter.getItemAt(index));
                Toast.makeText(MainActivity.this, "Item is Successfully Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        /* Click handling on the add button */
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,AddNewItem.class);
            startActivityForResult(intent,NEW_PRODUCT_ACTIVITY_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /* Inserting the new Activity */
        if(requestCode == NEW_PRODUCT_ACTIVITY_CODE && resultCode == RESULT_OK){
            assert data != null;
            String productName = data.getStringExtra(AddNewItem.PRODUCT_NAME);
            String brandName = data.getStringExtra(AddNewItem.BRAND_NAME);
            boolean inStock = data.getBooleanExtra(AddNewItem.IN_STOCK_NAME,true);
            int weight = data.getIntExtra(AddNewItem.WEIGHT_NAME,0);
            int price = data.getIntExtra(AddNewItem.PRICE_NAME,0);

            ProductItem item = new ProductItem(productName,brandName,inStock,weight,price);
            productViewModel.insert(item);
        }
        else if(requestCode == EDIT_PRODUCT_ACTIVITY_CODE && resultCode == RESULT_OK){

            assert data != null;
            String productName = data.getStringExtra(PRODUCT_NAME);
            String brandName = data.getStringExtra(BRAND_NAME);
            boolean inStock = data.getBooleanExtra(IN_STOCK_NAME,true);
            int weight = data.getIntExtra(WEIGHT_NAME,0);
            int price = data.getIntExtra(PRICE_NAME,0);
            int id = data.getIntExtra(ITEM_ID,-1);

            Log.d("updated",id + "");
            if(id != -1){
                ProductItem item = new ProductItem(productName,brandName,inStock,weight,price);
                item.setId(id);
                productViewModel.update(item);
                Toast.makeText(this, "Successfully updated", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Nothing is updated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onProductClick(ProductItem item) {
        Intent intent = new Intent(MainActivity.this,EditingItem.class);
        intent.putExtra(PRODUCT_NAME,item.getProductName());
        intent.putExtra(BRAND_NAME,item.getBrandName());
        intent.putExtra(IN_STOCK_NAME,item.isInStock());
        intent.putExtra(WEIGHT_NAME,item.getWeight());
        intent.putExtra(PRICE_NAME,item.getPrice());
        intent.putExtra(ITEM_ID,item.getId());
        startActivityForResult(intent,EDIT_PRODUCT_ACTIVITY_CODE);
    }
}