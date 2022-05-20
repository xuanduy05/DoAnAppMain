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
import com.example.doanappmain.adapter.ThuThuAdapter;
import com.example.doanappmain.dao.ThuThuDAO;
import com.example.doanappmain.modol.ThuThu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class AddUserFragment extends Fragment {
    ListView lv;
    ArrayList<ThuThu> listTT;
    FloatingActionButton fabTT;
    Dialog dialog;
    EditText edUser,edHoTen,edPass,edRePass;
    Button btnSave,btnCancel;
    int maTT;

    ThuThuAdapter adapter;
    ThuThu item;
    ThuThuDAO dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v= inflater.inflate(R.layout.fragment_add_user, container, false);
        lv = v.findViewById(R.id.lvthemthuthu);
        fabTT = v.findViewById(R.id.fabTT);

        dao = new ThuThuDAO(getActivity());
        capNhatLv();
        fabTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = listTT.get(position);
                openDialog(getActivity(), 1);
                return false;
            }
        });

        animation();

        return v;
    }

    private void openDialog(Context context, int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialogthemthuthu);
        edUser = dialog.findViewById(R.id.edUser);
        edHoTen=dialog.findViewById(R.id.edHoTen);
        edPass= dialog.findViewById(R.id.edPass);
        edRePass = dialog.findViewById(R.id.edRePass);

        btnSave = dialog.findViewById(R.id.btnSaveUser);
        btnCancel = dialog.findViewById(R.id.btnCancelUser);

        edUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUser.setText("");
            }
        });
        edHoTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edHoTen.setText("");
            }
        });
        edPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edPass.setText("");
            }
        });
        edRePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edRePass.setText("");
            }
        });
        if (type != 0) {
            edUser.setText(item.maTT);
            edHoTen.setText(item.hoTen);
            edPass.setText(item.matKhau);
            edRePass.setText(item.matKhau);
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

                ThuThu thuThu = new ThuThu();
                thuThu.maTT = edUser.getText().toString();
                thuThu.hoTen=edHoTen.getText().toString();
                thuThu.matKhau = edPass.getText().toString();
//                dao.insert(thuThu);
                if (validate()>0){
                    if (dao.insert(thuThu)>0){
                        Toast.makeText(getActivity(),"Lưu Thành Công",Toast.LENGTH_LONG).show();
                        edUser.setText("");
                        edHoTen.setText("");
                        edPass.setText("");
                        edRePass.setText("");
                    }else {
                        Toast.makeText(getActivity(),"Lưu Thất Bại",Toast.LENGTH_LONG).show();
                    }
                }
                capNhatLv();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

        public int validate(){
        int check  = 1;
        if (edUser.getText().length()==0 || edHoTen.getText().length()==0 ||edPass.getText().length()==0||edRePass.getText().length()==0){
            Toast.makeText(getContext(), "Bạn Phải Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            String pass = edPass.getText().toString();
            String rePass = edRePass.getText().toString();
            if (!pass.equals(rePass)){
                Toast.makeText(getContext(),"Mật Khẩu Không Trùng Khớp",Toast.LENGTH_LONG).show();
                check = -1;
            }
        }
        return check;
    }
    private void animation() {
        Animation animationHour1 = AnimationUtils.loadAnimation(getContext(), R.anim.translate);
        lv.startAnimation(animationHour1);
        Animation animationHour2 = AnimationUtils.loadAnimation(getContext(), R.anim.iconxoaxoay);
        fabTT.startAnimation(animationHour2);
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
                Toast.makeText(getContext(),"Xóa Thành Công",Toast.LENGTH_LONG).show();
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
        listTT = (ArrayList<ThuThu>) dao.getAll();
        adapter = new ThuThuAdapter(getActivity(),this,listTT);
        lv.setAdapter(adapter);
    }
}