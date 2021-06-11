package com.example.mystoreapp;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditingItem extends AppCompatActivity {

    EditText brandNameEdit, inStockEdit, weightEdit, priceEdit;

    TextView productNameTV;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_item);

        Intent intent = getIntent();
        String ProductNameGet = intent.getStringExtra(MainActivity.PRODUCT_NAME);
        String BrandNameGet = intent.getStringExtra(MainActivity.BRAND_NAME);
        boolean InStockGet = intent.getBooleanExtra(MainActivity.IN_STOCK_NAME,false);
        int WeightGet = intent.getIntExtra(MainActivity.WEIGHT_NAME,0);
        int PriceGet = intent.getIntExtra(MainActivity.PRICE_NAME,0);
        int idGet = intent.getIntExtra(MainActivity.ITEM_ID,-1);

        productNameTV = findViewById(R.id.productNameEdit);
        brandNameEdit = findViewById(R.id.brandNameEdit);
        inStockEdit = findViewById(R.id.inStockEdit);
        weightEdit = findViewById(R.id.weightEdit);
        priceEdit = findViewById(R.id.priceEdit);

        productNameTV.setText(ProductNameGet);
        brandNameEdit.setText(BrandNameGet);
        inStockEdit.setText(InStockGet ? "YES" : "NO");
        weightEdit.setText(Integer.toString(WeightGet));
        priceEdit.setText(Integer.toString(PriceGet));

        Button updateButton = findViewById(R.id.updateButton);

        updateButton.setOnClickListener(v -> {
            Intent replyIntent = new Intent();

            final String ProductName = productNameTV.getText().toString().trim();
            final String BrandName = brandNameEdit.getText().toString().trim();
            final String InStock = inStockEdit.getText().toString().trim();
            final String Weight = weightEdit.getText().toString().trim();
            final String Price = priceEdit.getText().toString().trim();


            if(BrandName.isEmpty()){
                brandNameEdit.setError("Brand Required");
                brandNameEdit.requestFocus();
                setResult(RESULT_CANCELED,replyIntent);
                return;
            }

            if(InStock.isEmpty() || (!InStock.toUpperCase().equals("YES") && !InStock.toUpperCase().equals("NO"))){
                inStockEdit.setError("In Stock Required");
                inStockEdit.requestFocus();
                setResult(RESULT_CANCELED,replyIntent);
                return;
            }

            if(Weight.isEmpty()){
                weightEdit.setError("Weight Required");
                weightEdit.requestFocus();
                setResult(RESULT_CANCELED,replyIntent);
                return;
            }

            if(Price.isEmpty()){
                priceEdit.setError("Price Required");
                priceEdit.requestFocus();
                setResult(RESULT_CANCELED,replyIntent);
                return;
            }

            int new_id;
            if(ProductName.equals(ProductNameGet) && BrandName.equals(BrandNameGet) && InStock.toUpperCase().equals(InStockGet ? "YES" : "NO") && Weight.equals(Integer.toString(WeightGet)) && Price.equals(Integer.toString(PriceGet))){
                new_id = -1;
            }
            else{
                new_id = idGet;
            }

            replyIntent.putExtra(MainActivity.PRODUCT_NAME,ProductName);
            replyIntent.putExtra(MainActivity.BRAND_NAME,BrandName);
            replyIntent.putExtra(MainActivity.IN_STOCK_NAME,InStock.toUpperCase().equals("YES"));
            replyIntent.putExtra(MainActivity.WEIGHT_NAME,Integer.parseInt(Weight));
            replyIntent.putExtra(MainActivity.PRICE_NAME,Integer.parseInt(Price));
            replyIntent.putExtra(MainActivity.ITEM_ID,new_id);

            setResult(RESULT_OK,replyIntent);
            finish();
        });

    }
}