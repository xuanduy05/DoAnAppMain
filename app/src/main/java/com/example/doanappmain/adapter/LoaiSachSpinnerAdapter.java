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
import com.example.doanappmain.modol.LoaiSach;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LoaiSachSpinnerAdapter extends ArrayAdapter<LoaiSach> {
    private Context context;
    private ArrayList<LoaiSach>lists;
    TextView tvMaLoaiSach,tvTenLoaiSach;

    public LoaiSachSpinnerAdapter(@NonNull @NotNull Context context, ArrayList<LoaiSach> list) {
        super(context, 0,list);
        this.context = context;
        this.lists = list;
        this.tvMaLoaiSach = tvMaLoaiSach;
        this.tvTenLoaiSach = tvTenLoaiSach;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.loaisach_item_spinner,null);
        }
        final LoaiSach item = lists.get(position);
        if (item != null){
            tvMaLoaiSach = v.findViewById(R.id.spnMaLS);
            tvMaLoaiSach.setText(item.maLoai+".");
            tvTenLoaiSach = v.findViewById(R.id.spnTenLS);
            tvTenLoaiSach.setText(item.tenLoai);
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable @org.jetbrains.annotations.Nullable View convertView, @NonNull @NotNull ViewGroup parent) {

        View v  =convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.sach_item_spinner,null);
        }
        final LoaiSach item = lists.get(position);
        if (item != null){
            tvMaLoaiSach = v.findViewById(R.id.spnMaLS);
            tvMaLoaiSach.setText(item.maLoai+".");
            tvTenLoaiSach = v.findViewById(R.id.spnTenLS);
            tvTenLoaiSach.setText(item.tenLoai);
        }
        return v;
    }
}
