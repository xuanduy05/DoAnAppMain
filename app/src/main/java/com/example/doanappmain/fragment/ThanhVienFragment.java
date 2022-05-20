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
import com.example.doanappmain.adapter.ThanhVienAdapter;
import com.example.doanappmain.dao.ThanhVienDAO;
import com.example.doanappmain.modol.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ThanhVienFragment extends Fragment {

    ListView lv;
    ArrayList<ThanhVien> lists;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaTV, edTenTV, edNamSinh;
    Button btnSave, btnCancel;
    static ThanhVienDAO dao;
    ThanhVienAdapter adapter;
    ThanhVien item;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_thanh_vien, container, false);

        lv = v.findViewById(R.id.lvthanhvien);
        fab = v.findViewById(R.id.faba);
        dao = new ThanhVienDAO(getActivity());
        capNhatLv();
        animation();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = lists.get(position);
                openDialog(getActivity(), 1);
                return false;
            }
        });
        return v;
    }

    private void openDialog(final Context context, final int type) {

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.thanh_vien_dialog);
        edMaTV = dialog.findViewById(R.id.edMaTV);
        edTenTV = dialog.findViewById(R.id.edTenTV);
        edNamSinh = dialog.findViewById(R.id.edNamsinh);
        btnCancel = dialog.findViewById(R.id.btnCancelTV);
        btnSave = dialog.findViewById(R.id.btnSaveTV);
        edMaTV.setEnabled(false);

        if (type != 0) {
            edMaTV.setText(String.valueOf(item.maTV));
            edTenTV.setText(item.hoTen);
            edNamSinh.setText(item.namSinh);
        }
        edTenTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               edTenTV.setText("");
            }
        });
        edNamSinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edNamSinh.setText("");
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new ThanhVien();
                item.hoTen = edTenTV.getText().toString();
                item.namSinh = edNamSinh.getText().toString();
                if (validate() > 0) {
                    if (type == 0) {
                        if (dao.insert(item) > 0) {
                            Toast.makeText(context, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm Thất Bai", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        item.maTV = Integer.parseInt(edMaTV.getText().toString());
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
        builder.setMessage("Bạn có muốn Xóa Không ?");
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
        lists = (ArrayList<ThanhVien>) dao.getAll();
       adapter = new ThanhVienAdapter(getActivity(),this,lists);
        lv.setAdapter(adapter);

    }

    public int validate() {
        int check = 1;
        if (edTenTV.getText().length() == 0 || edNamSinh.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn Phải Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    private void animation() {
        Animation animationHour1 = AnimationUtils.loadAnimation(getActivity(),R.anim.login);
        fab.startAnimation(animationHour1);
        Animation animationHour2 = AnimationUtils.loadAnimation(getActivity(),R.anim.translate);

        lv.startAnimation(animationHour2);
    }
}