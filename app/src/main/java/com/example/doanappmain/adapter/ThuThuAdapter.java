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
import com.example.doanappmain.dao.ThuThuDAO;
import com.example.doanappmain.fragment.AddUserFragment;
import com.example.doanappmain.modol.ThuThu;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ThuThuAdapter extends ArrayAdapter<ThuThu> {
    private Context context;
    AddUserFragment fragment;

    private ArrayList<ThuThu> lists;
    TextView tvTenDN,tvTenThuthu,tvMatKhau;
    ImageView imgDel;

    public ThuThuAdapter(@NonNull @NotNull Context context, AddUserFragment fragment, ArrayList<ThuThu> lists) {
        super(context, 0,lists);
        this.context = context;
        this.fragment = fragment;
        this.lists = lists;
        this.tvTenDN = tvTenDN;
        this.tvTenThuthu = tvTenThuthu;
        this.tvMatKhau = tvMatKhau;
        this.imgDel = imgDel;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.themthuthu_item, null);
        }
        final ThuThu item = lists.get(position);
        if (item != null){
            ThuThuDAO thuThuDAO =new ThuThuDAO(context);
            ThuThu thuThu = thuThuDAO.getID(String.valueOf(item.maTT));
            tvTenDN = v.findViewById(R.id.tvTenDN);
            tvTenDN.setText("Tên Đăng Nhập: "+item.maTT);
            tvTenThuthu= v.findViewById(R.id.tvTenThuthu);
            tvTenThuthu.setText("Họ Và Tên: "+item.hoTen);
            tvMatKhau = v.findViewById(R.id.tvMatKhau);
            tvMatKhau.setText("Mật Khẩu: "+item.matKhau);

            imgDel = v.findViewById(R.id.imgdeleteThuThu);

        }

        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.xoa(String.valueOf(item.maTT));
            }
        });
        return v;
        }
    }

