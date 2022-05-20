package com.example.doanappmain.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.example.doanappmain.R;
import com.example.doanappmain.dao.ThuThuDAO;
import com.example.doanappmain.modol.ThuThu;
import com.google.android.material.textfield.TextInputEditText;


public class ChangePassFragment extends Fragment {
    TextInputEditText edPassOld,edPass,edRePass;
    Button btnsave,btnCancel;
    LinearLayout linearLayout;
    ThuThuDAO dao;

//    public ChangePassFragment() {
//        // Required empty public constructor
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_pass, container, false);
        edPassOld = v.findViewById(R.id.edPassOld);
        edPass = v.findViewById(R.id.edPassChange);
        edRePass=v.findViewById(R.id.edRePassChange);
        btnsave = v.findViewById(R.id.btnSaveUserChange);
        btnCancel= v.findViewById(R.id.btnCancelUserChange);
        linearLayout=v.findViewById(R.id.LLdoimatkhau);
        animation();
        dao = new ThuThuDAO(getActivity());
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edPassOld.setText("");
                edPass.setText("");
                edRePass.setText("");

            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //doc user,pass trong Sharedpreferences
                SharedPreferences pref=getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = pref.getString("USERNAME","");
                if (validate()>0){
                    ThuThu thuThu = dao.getID(user);
                    thuThu.matKhau = edPass.getText().toString();
                    dao.update(thuThu);
                    if (dao.update(thuThu)>0){
                        Toast.makeText(getActivity(),"Thay Đổi Mật Khẩu Thành Công",Toast.LENGTH_SHORT).show();
                        edPassOld.setText("");
                        edPass.setText("");
                        edRePass.setText("");
                    }
                    else {
                        Toast.makeText(getActivity(),"Thay Đổi Mật Khẩu Thất Bại",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return v;
    }
    public int validate(){
        int check  = 1;
        if (edPassOld.getText().length()==0 || edPass.getText().length()==0 ||edRePass.getText().length()==0){
            Toast.makeText(getContext(),"Bạn Phải Nhập Đầy Đủ Thông Tin",Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            //doc

            SharedPreferences pref=getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passOld = pref.getString("PASSWORD","");
            String pass = edPass.getText().toString();
            String rePass = edRePass.getText().toString();
            if (!passOld.equals(edPassOld.getText().toString())){
                Toast.makeText(getContext(),"Mật Khẩu Củ Sai",Toast.LENGTH_LONG).show();
                check = -1;
            }
            if (!pass.equals(rePass)){
                Toast.makeText(getContext(),"Mật Khẩu Không Trùng Khớp",Toast.LENGTH_LONG).show();
                check = -1;
            }
        }
        return check;
    }
    private void animation() {
        Animation animationHour1 = AnimationUtils.loadAnimation(getContext(), R.anim.translate1);
        linearLayout.startAnimation(animationHour1);
    }
}