package com.example.doanappmain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.doanappmain.R;
import com.example.doanappmain.fragment.ThanhVienFragment;
import com.example.doanappmain.modol.ThanhVien;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ThanhVienAdapter extends ArrayAdapter<ThanhVien> {

    private Context context;
    ThanhVienFragment fragment;
    private ArrayList<ThanhVien>lists;
    TextView tvMaTV,tvTenTV,tvNamSinh;
    ImageView imgDel;


    public ThanhVienAdapter(@NonNull @NotNull Context context, ThanhVienFragment fragment, ArrayList<ThanhVien> lists) {
        super(context, 0,lists);
        this.context = context;
        this.fragment = fragment;
        this.lists = lists;
        this.tvMaTV = tvMaTV;
        this.tvTenTV = tvTenTV;
        this.tvNamSinh = tvNamSinh;
        this.imgDel = imgDel;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.thanh_vien_item,null);
        }
        final ThanhVien item = lists.get(position);
        if (item != null){
            tvMaTV = v.findViewById(R.id.tvMaTV);
            tvMaTV.setText("Mã Thành Viên: "+item.maTV);

            tvTenTV = v.findViewById(R.id.tvTenTv);
            tvTenTV.setText("Họ và Tên: "+item.hoTen);

            tvNamSinh = v.findViewById(R.id.tvNamSinh);
            tvNamSinh.setText("Năm Sinh: "+item.namSinh);

            imgDel=v.findViewById(R.id.imgdeleteLs);
        }

        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.xoa(String.valueOf(item.maTV));
            }

        });

        return v;
    }


}
