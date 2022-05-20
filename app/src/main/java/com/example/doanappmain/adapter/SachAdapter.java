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
import com.example.doanappmain.fragment.SachFragment;
import com.example.doanappmain.modol.LoaiSach;
import com.example.doanappmain.modol.Sach;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SachAdapter extends ArrayAdapter<Sach> {
    private Context context;
    SachFragment fragment;
    private ArrayList<Sach>lists;
    TextView tvMaSach,tvTenSach,tvGiaThue,tvLoai;
    ImageView imgDel;

    public SachAdapter(@NonNull @NotNull Context context, SachFragment fragment, ArrayList<Sach> lists) {
        super(context, 0,lists);
        this.context = context;
        this.fragment = fragment;
        this.lists = lists;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v =convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v =inflater.inflate(R.layout.sach_item,null);

        }
        final Sach item = lists.get(position);
        if (item != null){
            LoaiSachDAO loaiSachDAO =new LoaiSachDAO(context);
            LoaiSach loaiSach = loaiSachDAO.getID(String.valueOf(item.maLoai));
            tvMaSach = v.findViewById(R.id.tvMaSach1);
            tvMaSach.setText("Mã Sách: "+item.maSach);
            tvTenSach= v.findViewById(R.id.tvTenSach1);
            tvTenSach.setText("Tên Sách: "+item.tenSach);
            tvGiaThue = v.findViewById(R.id.tvgiathue1);
            tvGiaThue.setText("Giá Thuê: "+item.giaThue);
            tvLoai = v.findViewById(R.id.tvLoai1);
            tvLoai.setText("Loại Sách: "+loaiSach.tenLoai);
            imgDel = v.findViewById(R.id.imgdeleteSach1);

        }

        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.xoa(String.valueOf(item.maSach));
            }
        });

        return v;
    }
}
