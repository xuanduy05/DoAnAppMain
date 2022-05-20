package com.example.doanappmain.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.example.doanappmain.R;
import com.example.doanappmain.adapter.LoaiSachSpinnerAdapter;
import com.example.doanappmain.adapter.SachAdapter;
import com.example.doanappmain.dao.LoaiSachDAO;
import com.example.doanappmain.dao.SachDAO;
import com.example.doanappmain.modol.LoaiSach;
import com.example.doanappmain.modol.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class SachFragment extends Fragment {
    // khai bao
    ListView lv;
    ArrayList<Sach> lists;
    FloatingActionButton fabs;
    Dialog dialog;
    EditText edMasach, edTenSach,edGiaThue;
    Spinner spinner;
    Button btnSave, btnCancel;
    static SachDAO dao;
    SachAdapter adapter;
    Sach item;
    LoaiSachSpinnerAdapter spinnerAdapter;
    ArrayList<LoaiSach>listLoaiSach;
    LoaiSachDAO loaiSachDAO;
    LoaiSach loaiSach;
    int maLoaiSach,position;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sach, container, false);
        // anh xa framgment lv
        lv = v.findViewById(R.id.lvSach);
        fabs = v.findViewById(R.id.fabS1);
        dao = new SachDAO(getActivity());
        capnhatlv();
        animation();
        fabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(),0);

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = lists.get(position);
                openDialog(getActivity(),1);
                return false;
            }
        });
        return v;
    }

    protected void openDialog(Context context, int type) {
        // anh xa dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.sach_dialog);
        edMasach = dialog.findViewById(R.id.edMaSach);
        edTenSach=dialog.findViewById(R.id.edTenSach);
        edGiaThue=dialog.findViewById(R.id.edgiathueSach);
        spinner = dialog.findViewById(R.id.spnLoaiSach);
        btnCancel = dialog.findViewById(R.id.btnCancelSach);
        btnSave = dialog.findViewById(R.id.btnSaveSach);
        listLoaiSach = new ArrayList<LoaiSach>();
        loaiSachDAO = new LoaiSachDAO(context);
        listLoaiSach= (ArrayList<LoaiSach>)loaiSachDAO.getAll();
        spinnerAdapter = new LoaiSachSpinnerAdapter(context,listLoaiSach);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiSach = listLoaiSach.get(position).maLoai;
                // show thong bao
                Toast.makeText(context,"chon " +listLoaiSach.get(position).tenLoai,Toast.LENGTH_SHORT).show();
            }

            //
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Textinput layout khong duoc nhap
        edMasach.setEnabled(false);

        if (type != 0){
            edMasach.setText(String.valueOf(item.maSach));
            edTenSach.setText(item.tenSach);
            edGiaThue.setText(String.valueOf(item.giaThue));
            for (int i = 0; i < listLoaiSach.size();i++)
                if (item.maLoai == (listLoaiSach.get(i).maLoai)){
                    position = i;
                }
            Log.i("demo","posSach"+position);
            spinner.setSelection(position);
        }
//        edTenSach,edGiaThue rs form
        edTenSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edTenSach.setText("");
            }
        });
        edGiaThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edGiaThue.setText("");
            }
        });

        // huy them du lieu
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // luu
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new Sach();
                item.tenSach = edTenSach.getText().toString();
                item.maLoai =maLoaiSach;
                if (validate()>0){
                item.giaThue = Integer.parseInt(edGiaThue.getText().toString());
                    if (type == 0){
                        // them du lieu
                        if (dao.insert(item)>0){
                            Toast.makeText(context,"Thêm Thành Công",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"Thêm Thất Bại",Toast.LENGTH_SHORT).show();

                        }
                    }else {
                        item.maSach =Integer.parseInt(edMasach.getText().toString());
                        // sua du lieu
                        if (dao.update(item)>0){
                            Toast.makeText(context,"Sửa Thành Công ",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"Sửa Thất Bại ",Toast.LENGTH_SHORT).show();

                        }

                    }
                    capnhatlv();
                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }

    private int validate() {
        // check rong
        int check = 1;
        if (edTenSach.getText().length()==0||edGiaThue.getText().length()==0){
            Toast.makeText(getContext(), "Bạn Phải Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    void capnhatlv() {
        lists = (ArrayList<Sach>)dao.getAll();
        adapter = new SachAdapter(getActivity(),this,lists);
        lv.setAdapter(adapter);
    }
    public void xoa(final String Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa");
        builder.setMessage("Bạn Có Muốn Xóa Không?");
        builder.setCancelable(true);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(Id);
                capnhatlv();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();

    }
    // khai bao hieu ung animation
    private void animation() {
        Animation animationHour1 = AnimationUtils.loadAnimation(getActivity(),R.anim.login);
        fabs.startAnimation(animationHour1);
        Animation animationHour2 = AnimationUtils.loadAnimation(getActivity(),R.anim.translate);
        lv.startAnimation(animationHour2);
    }
}