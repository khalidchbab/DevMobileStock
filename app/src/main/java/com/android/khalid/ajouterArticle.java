package com.android.khalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.khalid.Models.Article;

public class ajouterArticle extends AppCompatActivity implements View.OnClickListener {
    EditText prodName,prodQte,prodPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_produit);
        getSupportActionBar().hide();
         Button btnAdd = findViewById(R.id.addArticle);
         prodName = findViewById(R.id.addProdName);
         prodQte = findViewById(R.id.addprodQte);
         prodPrice = findViewById(R.id.addProdPrice);
         btnAdd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String name = prodName.getText().toString().trim();
        String price         = prodPrice.getText().toString().trim();
        String qte           = prodQte.getText().toString().trim();

        if(name.equals("")){

            prodName.setError("Please Fill Product Name");
            return;
        }
        if(price.equals("")){

            prodPrice.setError("Please Fill Price");
            return;
        }
        if(qte.equals("")){

            prodQte.setError("Please Fill Quantity");
            return;
        }

        Article article = new Article(name,Double.valueOf(price), Integer.valueOf(qte));
        Intent intent = new Intent();
        intent.putExtra("newProduct", article);
        setResult(RESULT_OK,intent);
        finish();
    }
}