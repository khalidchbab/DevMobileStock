package com.android.khalid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.khalid.Models.Operation;
import com.android.khalid.R;

import java.util.ArrayList;

public class AdapterTransactionList extends ArrayAdapter {
    ArrayList<Operation> operations;
    public AdapterTransactionList(@NonNull Context context, int resource, @NonNull ArrayList<Operation> objects) {
        super(context, resource, objects);
        operations = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cutsom_transaction_list, parent, false);
        }
        TextView prodNum,prodName,prodQte,prodDate;
        prodName = convertView.findViewById(R.id.prodTranscName);
        prodQte = convertView.findViewById(R.id.prodTranscQte);
        prodNum  = convertView.findViewById(R.id.prodNum);
        prodDate = convertView.findViewById(R.id.dateOperation);
        prodNum.setText(String.valueOf(getItem(position).getTranscNum()));
        prodName.setText(String.valueOf(getItem(position).getProdName()));
        prodQte.setText(String.valueOf(getItem(position).getProdQte()));
        prodDate.setText(getItem(position).getDateOperation());
        return  convertView;
    }
    @Nullable
    @Override
    public Operation getItem(int position) {
        return operations.get(position);
    }

}
