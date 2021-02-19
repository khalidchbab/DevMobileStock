package com.android.khalid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.khalid.Models.Article;
import com.android.khalid.R;

import java.util.ArrayList;

public class storeAdapter extends ArrayAdapter implements View.OnClickListener {
    ArrayList<Article> articles;
    Context context;
    ShowDialog  showDialog;

    public storeAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Article> objects) {
        super(context, resource, objects);
        articles = objects;
        showDialog = (ShowDialog) context;
    }
    public interface  ShowDialog{
        public void display(Article prd);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_store,parent,false);
        }

        TextView prodPrice,prodQte,prodName;
        Button purchaseBtn;
        prodName = convertView.findViewById(R.id.prodName);
        prodPrice = convertView.findViewById(R.id.prodPrice);
        prodQte = convertView.findViewById(R.id.prodQte);
        purchaseBtn = convertView.findViewById(R.id.purchase);
        prodName.setText(getItem(position).getProdName());
        prodPrice.setText(String.format("%s DH",String.valueOf(getItem(position).getProdPrice())));
        prodQte.setText(String.valueOf(getItem(position).getProdQte()));
        purchaseBtn.setOnClickListener(this);
        purchaseBtn.setTag(String.valueOf(position));
        return convertView;
    }


    @Nullable
    @Override
    public Article getItem(int position) {
        return articles.get(position);
    }

    @Override
    public void onClick(View v) {

        Article prod = getItem(Integer.parseInt((String) v.getTag()));
        showDialog.display(prod);
    }
}
