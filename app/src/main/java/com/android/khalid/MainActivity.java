package com.android.khalid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.khalid.Adapters.storeAdapter;
import com.android.khalid.DatabaseAccess.MyDBHelper;
import com.android.khalid.Models.Article;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements storeAdapter.ShowDialog {
    ListView listView;
    BottomNavigationView bottomNavigationView;
    ArrayList<Article> articles = new ArrayList();
    MyDBHelper database;
    storeAdapter storeadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listStore);
        bottomNavigationView = findViewById(R.id.navigationBottom);
        database = new MyDBHelper(this);
        articles = database.getAllProducts();
        storeadapter = new storeAdapter(this,R.layout.custom_list_store, articles);
        listView.setAdapter(storeadapter);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {

                    case R.id.addProd:
                        Intent intentAdd = new Intent(getApplicationContext(), ajouterArticle.class);
                        startActivityForResult(intentAdd,1);
                        break;
                    case R.id.showTranscations:
                        Intent intentShow = new Intent(getApplicationContext(), Operation_activity.class);
                        startActivity(intentShow);
                        break;
                }
                return false;
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                    Article article = (Article) data.getSerializableExtra("newProduct");
                    try{
                        long prodId = database.addProduct(article);
                        article.setProdId((int) prodId);
                        articles.add(article);
                        storeadapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(),"Product Add Successfully",Toast.LENGTH_SHORT).show();
                    }catch(SQLException e){
                        Toast.makeText(getApplicationContext(),"Product UnSuccessfully Added",Toast.LENGTH_SHORT).show();
                    }
            }else{

                Toast.makeText(getApplicationContext(),"Something Wrong Happening Try Again",Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void display(Article prd) {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);
        EditText qte = dialog.findViewById(R.id.qteSelected);
        Button btn  = dialog.findViewById(R.id.addQte);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qteValue = qte.getText().toString().trim();

                if(qteValue.equals("")){
                    qte.setError("Please Add Quantity");
                    return;
                }
                dialog.dismiss();

                if( prd.getProdQte() >= Integer.parseInt(qteValue)){
                    try{
                        long idTrans = database.addTranscation(prd,Integer.parseInt(qteValue));
                        Toast.makeText(getApplicationContext(),"successfully Operation",Toast.LENGTH_SHORT).show();
                        int prodId = articles.indexOf(prd);
                        int newQte  = prd.getProdQte() - Integer.parseInt(qteValue);
                        articles.get(prodId).setProdQte(newQte);
                        storeadapter.notifyDataSetChanged();

                    }catch(SQLException e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Unsuccessfully Operation",Toast.LENGTH_SHORT).show();
                    }


                }else{

                    Toast.makeText(getApplicationContext(),"Quantity Insufficient",Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();

    }

}