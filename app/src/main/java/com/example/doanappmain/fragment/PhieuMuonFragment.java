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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.example.doanappmain.R;
import com.example.doanappmain.adapter.PhieuMuonAdapter;
import com.example.doanappmain.adapter.SachSpinnerAdapter;
import com.example.doanappmain.adapter.ThanhVienSpinnerAdapter;
import com.example.doanappmain.dao.PhieuMuonDAO;
import com.example.doanappmain.dao.SachDAO;
import com.example.doanappmain.dao.ThanhVienDAO;
import com.example.doanappmain.modol.PhieuMuon;
import com.example.doanappmain.modol.Sach;
import com.example.doanappmain.modol.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PhieuMuonFragment extends Fragment {
    ListView lv;
    ArrayList<PhieuMuon>list;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaPM;
    Spinner spTV, spSach;
    TextView tvNgay, tvTienThue;
    CheckBox chkTraSach;
    Button btnSave,btnCancel;
    static PhieuMuonDAO dao;
    PhieuMuonAdapter adapter;
    PhieuMuon item;

    ThanhVienSpinnerAdapter thanhVienSpinnerAdapter;
    ArrayList<ThanhVien>listThanhvien;
    ThanhVienDAO thanhVienDAO;
    int mathanhvien;
    SachSpinnerAdapter sachSpinnerAdapter;
    ArrayList<Sach>listSach;
    SachDAO sachDAO;
    Sach sach;
    int masach,tienthue;
    int positionTV,positionSach;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        lv= v.findViewById(R.id.lvphieumuon);
        fab=v.findViewById(R.id.fabpm);
        dao=new PhieuMuonDAO(getActivity());
        capnhatLv();
        animation();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(),0);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(),1);
                return false;
            }
        });
        return v;
    }

    protected void openDialog(final Context context,final int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.phieu_muon_dialog);

        edMaPM = dialog.findViewById(R.id.edMaPM);
        spTV=dialog.findViewById(R.id.spMaTV);
        spSach = dialog.findViewById(R.id.spMaSach);
        tvNgay=dialog.findViewById(R.id.tvNgayphieumuon);
        tvTienThue = dialog.findViewById(R.id.tvTienThuePm);
        chkTraSach = dialog.findViewById(R.id.chkTraSach);
        btnCancel = dialog.findViewById(R.id.btnCancelPM);
        btnSave = dialog.findViewById(R.id.btnSavePM);

        tvNgay.setText("Ngày Thuê : "+sdf.format(new Date()));

        thanhVienDAO = new ThanhVienDAO(context);
        listThanhvien = new ArrayList<ThanhVien>();
        listThanhvien = (ArrayList<ThanhVien>)thanhVienDAO.getAll();
        thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(context,listThanhvien);
        spTV.setAdapter(thanhVienSpinnerAdapter);

        spTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mathanhvien = listThanhvien.get(position).maTV;
                Toast.makeText(context,"Chọn: "+listThanhvien.get(position).hoTen,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sachDAO = new SachDAO(context);
        listSach=(ArrayList<Sach>)sachDAO.getAll();
        sachSpinnerAdapter = new SachSpinnerAdapter(context,listSach);
        spSach.setAdapter(sachSpinnerAdapter);
        spSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                masach = listSach.get(position).maSach;
                tienthue=listSach.get(position).giaThue;
                tvTienThue.setText("Tiền Thuê : "+tienthue);
                Toast.makeText(context,"Chọn: "+listSach.get(position).tenSach,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edMaPM.setEnabled(false);
        if (type !=0){
            edMaPM.setText(String.valueOf(item.maPM));

            for (int i = 0 ;i <listThanhvien.size();i++)
                if (item.maTV == (listThanhvien.get(i).maTV)){
                    positionTV = i;
                }
            spTV.setSelection(positionTV);
            for (int i = 0 ;i <listSach.size();i++)
                if (item.maSach==(listSach.get(i).maSach)){
                    positionSach = i;
                }
            spSach.setSelection(positionSach);


            Log.i("//======",item.ngay+"");
            tvNgay.setText("Ngày thuê: "+sdf.format(item.ngay));
            tvTienThue.setText("Tiền Thuê: "+item.tienThue);
            if (item.traSach ==1){
                chkTraSach.setChecked(true);
            }else {
                chkTraSach.setChecked(false);
            }
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new PhieuMuon();
                item.maSach = masach;
                item.maTV=mathanhvien;

                Date date = new Date();
                sdf.format(date);

                item.ngay= date;
                item.tienThue=tienthue;
                if (chkTraSach.isChecked()){
                    item.traSach =1;
                }else {
                    item.traSach = 0;
                }
                if (validate()>0){
                    if (type == 0){
                        if (dao.insert(item)>0){
                            Toast.makeText(context,"Thêm Thành Công",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"Thêm Thất Bại",Toast.LENGTH_SHORT).show();

                        }
                    }else {
                        item.maPM = Integer.parseInt(edMaPM.getText().toString());
                        if (dao.update(item)>0){
                            Toast.makeText(context,"Sửa Thành Công",Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(context,"Sửa Thất Bại",Toast.LENGTH_SHORT).show();

                        }
                    }

                }
                capnhatLv();
                dialog.dismiss();
            }
        });
    dialog.show();
    }


    void capnhatLv() {
        list = (ArrayList<PhieuMuon>)dao.getAll();
        adapter = new PhieuMuonAdapter(getActivity(),this,list);
        lv.setAdapter(adapter);

    }
    int validate(){
        int check =1;
        return check;
    }
    public void xoa(final String Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa");
        builder.setMessage("Bạn Có Muốn Xóa Không ? ");
        builder.setCancelable(true);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(Id);
                capnhatLv();
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
    private void animation() {
        Animation animationHour1 = AnimationUtils.loadAnimation(getActivity(),R.anim.login);
        Animation animation =AnimationUtils.loadAnimation(getActivity(),R.anim.translate);
        fab.startAnimation(animationHour1);
        lv.startAnimation(animation);
    }
}