package com.example.doanappmain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.doanappmain.R;
import com.example.doanappmain.modol.ThanhVien;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ThanhVienSpinnerAdapter extends ArrayAdapter<ThanhVien> {
    private Context context;
    private ArrayList<ThanhVien>lists;
    TextView tvMaTV,tvTenTV;

    public ThanhVienSpinnerAdapter(@NonNull @NotNull Context context, ArrayList<ThanhVien> lists) {
        super(context, 0,lists);
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      View v =convertView;
      if (v==null){
          LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          v= inflater.inflate(R.layout.thanh_vien_item_spinner,null);

      }
      final ThanhVien item  = lists.get(position);
      if (item != null){
          tvMaTV = v.findViewById(R.id.tvMaTVSp);
          tvMaTV.setText(item.maTV+".");
          tvTenTV = v.findViewById(R.id.tvTenTVSp);
          tvTenTV.setText(item.hoTen);
      }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        View v =convertView;
        if (v==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(R.layout.thanh_vien_item_spinner,null);

        }
        final ThanhVien item  = lists.get(position);
        if (item != null){
            tvMaTV = v.findViewById(R.id.tvMaTVSp);
            tvMaTV.setText(item.maTV+".");
            tvTenTV = v.findViewById(R.id.tvTenTVSp);
            tvTenTV.setText(item.hoTen);
        }
        return v;
    }
}
