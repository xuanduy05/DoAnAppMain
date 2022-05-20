package com.example.doanappmain.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.doanappmain.R;
import com.example.doanappmain.dao.SachDAO;
import com.example.doanappmain.dao.ThanhVienDAO;
import com.example.doanappmain.fragment.PhieuMuonFragment;
import com.example.doanappmain.modol.PhieuMuon;
import com.example.doanappmain.modol.Sach;
import com.example.doanappmain.modol.ThanhVien;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PhieuMuonAdapter extends ArrayAdapter<PhieuMuon> {
    private Context context;
    PhieuMuonFragment fragment;
    private ArrayList<PhieuMuon>lists;
    TextView tvMaPM,tvTenTV,tvTenSach,tvTienThue,tvNgay,tvTraSach;
    ImageView imgDel;
    SachDAO sachDAO;
    ThanhVienDAO thanhVienDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public PhieuMuonAdapter(@NonNull @NotNull Context context, PhieuMuonFragment fragment, ArrayList<PhieuMuon> lists) {
        super(context, 0,lists);
        this.context = context;
        this.fragment = fragment;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v =convertView;
        if (v==null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.phieu_muon_item,null);

        }
        final PhieuMuon item = lists.get(position);
        if (item != null){
            tvMaPM = v.findViewById(R.id.tvMaPM);
            tvMaPM.setText("Mã Phiếu: "+item.maPM);
            sachDAO = new SachDAO(context);
            Sach sach = sachDAO.getID(String.valueOf(item.maSach));
            tvTenSach = v.findViewById(R.id.tvTenSach);
            tvTenSach.setText("Tên Sách: "+sach.tenSach);

            thanhVienDAO = new ThanhVienDAO(context);
            ThanhVien thanhVien = thanhVienDAO.getID(String.valueOf(item.maTV));
            tvTenTV=v.findViewById(R.id.tvtenTV1);
            tvTenTV.setText("Thành Viên: "+thanhVien.hoTen);

            tvTienThue  = v.findViewById(R.id.tvtienthue1);
            tvTienThue.setText("Tiền Thuê: "+item.tienThue);


            tvNgay = v.findViewById(R.id.tvngayPM);
            tvNgay.setText("Ngày Thuê: "+sdf.format(item.ngay));

            tvTraSach = v.findViewById(R.id.tvTraSach);
            if (item.traSach == 1){
                tvTraSach.setTextColor(Color.BLUE);
                tvTraSach.setText("Đã Trả Sách");
            }else {
                tvTraSach.setTextColor(Color.RED);
                tvTraSach.setText("Chưa Trả Sách");
            }
            imgDel = v.findViewById(R.id.imgxoa);
        }

        imgDel.setOnClickListener(v1 -> fragment.xoa(String.valueOf(item.maPM)));
        return v;
    }


}
