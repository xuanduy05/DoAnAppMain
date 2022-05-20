package com.example.doanappmain.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.example.doanappmain.R;
import com.example.doanappmain.adapter.LoaiSachAdapter;
import com.example.doanappmain.dao.LoaiSachDAO;
import com.example.doanappmain.modol.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class LoaiSachFragment extends Fragment {

    ListView lv;
    ArrayList<LoaiSach> listLs;
    FloatingActionButton fabLs;
    Dialog dialog;
    EditText edMaLoaiSach, edTenLoaiSach;
    Button btnSaveLs, btnCancelLs;
    int MaLoai;
    static LoaiSachDAO dao;
    LoaiSachAdapter adapter;
    LoaiSach item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_loai_sach, container, false);

        lv = v.findViewById(R.id.lvLoaisach);
        fabLs = v.findViewById(R.id.fabLS);

        dao = new LoaiSachDAO(getActivity());
        capNhatLv();
        animation();
        fabLs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = listLs.get(position);
                openDialog(getActivity(), 1);
                return false;
            }
        });
        return v;
    }

    private void openDialog(Context context, int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.loai_sach_dialog);
        edMaLoaiSach = dialog.findViewById(R.id.edMaLoaiSach);
        edTenLoaiSach = dialog.findViewById(R.id.edTenLoaiSach);

        btnCancelLs = dialog.findViewById(R.id.btnCancelLoaiSach);
        btnSaveLs = dialog.findViewById(R.id.btnSaveLoaiSach);
        edMaLoaiSach.setEnabled(false);



        if (type != 0) {
            edMaLoaiSach.setText(String.valueOf(item.maLoai));
            edTenLoaiSach.setText(item.tenLoai);
        }

        // rs ten loai sách
        edTenLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edTenLoaiSach.setText("");
            }
        });

        btnCancelLs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSaveLs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new LoaiSach();
                item.tenLoai = edTenLoaiSach.getText().toString();
                if (validate() >0){

                if (type == 0) {
                    if (dao.insert(item) > 0) {
                        Toast.makeText(context, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    item.maLoai = Integer.parseInt(edMaLoaiSach.getText().toString());
                    if (dao.update(item) > 0) {
                        Toast.makeText(context, "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Sửa Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                }
                capNhatLv();
                dialog.dismiss();
             }
            }

        });
        dialog.show();

    }







    public void xoa(final String Id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa");
        builder.setMessage("Bạn Có Muốn Xóa Không ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dao.delete(Id);
                capNhatLv();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }
    void capNhatLv() {
        listLs = (ArrayList<LoaiSach>) dao.getAll();
        adapter = new LoaiSachAdapter(getActivity(),this,listLs);
        lv.setAdapter(adapter);
    }

    public int validate() {
        int check = 1;
        if ( edTenLoaiSach.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn Phải Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
    private void animation() {
        Animation animationHour1 = AnimationUtils.loadAnimation(getActivity(),R.anim.login);
        Animation animationHour2 = AnimationUtils.loadAnimation(getActivity(),R.anim.translate);

        lv.startAnimation(animationHour2);
        fabLs.startAnimation(animationHour1);
    }
}