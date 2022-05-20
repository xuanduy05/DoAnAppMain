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
import com.example.doanappmain.dao.LoaiSachDAO;
import com.example.doanappmain.fragment.LoaiSachFragment;
import com.example.doanappmain.modol.LoaiSach;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LoaiSachAdapter  extends ArrayAdapter<LoaiSach> {
    private Context context;
    LoaiSachFragment fragment;
    private ArrayList<LoaiSach> lists;
    TextView tvMaLS,tvTenLs;
    LoaiSachDAO loaiSachDAO;
    ImageView imgDel;

    public LoaiSachAdapter(@NonNull @NotNull Context context, LoaiSachFragment fragment, ArrayList<LoaiSach> lists) {
        super(context, 0,lists);
        this.context = context;
        this.fragment = fragment;
        this.lists = lists;
        this.tvMaLS = tvMaLS;
        this.tvTenLs = tvTenLs;
        this.loaiSachDAO = loaiSachDAO;
        this.imgDel = imgDel;

    }

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> listloaisach) {
        super(context, 0,listloaisach);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.loai_sach_item,null);
        }
        final LoaiSach item = lists.get(position);
        if (item != null){
            tvMaLS = v.findViewById(R.id.tvMaLS);
            tvMaLS.setText("Mã Loại: "+item.maLoai);

            tvTenLs = v.findViewById(R.id.tvTenLs);
            tvTenLs.setText("Tên Loại: "+item.tenLoai);


            imgDel=v.findViewById(R.id.imgdeleteloaisach);

        }

        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.xoa(String.valueOf(item.maLoai));
            }

        });

        return v;
    }
}
