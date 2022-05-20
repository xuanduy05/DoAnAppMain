package com.example.doanappmain;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanappmain.dao.ThuThuDAO;
import com.google.android.material.textfield.TextInputLayout;


public class LoginActivity extends AppCompatActivity {
    EditText edUserName,edPassword;
    Button btnLogin,btnCanCel;
    CheckBox chkRememberPass;
    String strUser,strPass;
    ThuThuDAO dao;
    ImageView img1,img2,img3,img4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("ĐĂNG NHẬP");
        edUserName = findViewById(R.id.edUserName);
        edPassword = findViewById(R.id.edPassWord);

        btnLogin = findViewById(R.id.btnLogin);
        btnCanCel = findViewById(R.id.btnCancel);

        chkRememberPass = findViewById(R.id.chkRememberPass);

        dao = new ThuThuDAO(this);
        //doc User,pass,trong SharedPreferences

        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        edUserName.setText(pref.getString("USERNAME",""));
        edPassword.setText(pref.getString("PASSWORD",""));
        chkRememberPass.setChecked(pref.getBoolean("REMEMBER",false));

        edUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUserName.setText("");
            }
        });
        edPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edPassword.setText("");
            }
        });
        btnCanCel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUserName.setText("");
                edPassword.setText("");
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

    }



    public void checkLogin(){
        strUser  = edUserName.getText().toString();
        strPass = edPassword.getText() .toString();

        if (strUser.isEmpty()||strPass.isEmpty()){
            Toast.makeText(getApplicationContext(),"Tên đăng nhập hoặc mật khẩu không được bỏ trống",Toast.LENGTH_SHORT).show();
        }else {
            if(dao.CheckLogin(strUser,strPass)>0 || (strUser.equals("admin") && strPass.equals("123"))){
                Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                rememberUser(strUser,strPass,chkRememberPass.isChecked());

                Intent i  = new Intent(getApplicationContext(),MainActivity.class);
                i.putExtra("user",strUser);
                startActivity(i);
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"Tên đăng nhập hoặc mật khẩu không đúng",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void rememberUser(String u,String p,boolean status){
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        if (!status){
            //Xoa tinh trang truoc do
            editor.clear();
        }else {
            // luu du lieu
            editor.putString("USERNAME",u);
            editor.putString("PASSWORD",p);
            editor.putBoolean("REMEMBER",status);
        }
        // luu lai toan bo
        editor.commit();
    }
}