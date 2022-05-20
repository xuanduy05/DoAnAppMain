package com.example.doanappmain.dao;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import com.example.doanappmain.database.DbHelper;
import com.example.doanappmain.modol.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    private SQLiteDatabase db;
    private Context context;
    public ThuThuDAO(Context context){
        this.context=context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(ThuThu obj){
        ContentValues values = new ContentValues();
        values.put("maTT",obj.maTT);
        values.put("hoTen",obj.hoTen);
        values.put("matKhau",obj.matKhau);
        return db.insert("ThuThu",null,values);
    }
    public int update(ThuThu obj){
        ContentValues values = new ContentValues();

        values.put("hoTen",obj.hoTen);
        values.put("matKhau",obj.matKhau);
        return db.update("ThuThu",values,"maTT=?",new String[]{String.valueOf(obj.maTT)});
    }

    public int delete(String id){
        Toast.makeText(context.getApplicationContext(), "Xóa Thành công",Toast.LENGTH_SHORT).show();
        return db.delete("ThuThu","maTT=?",new String[]{id});
    }

    //get tat ca data
    public List<ThuThu> getAll(){
        String sql = "SELECT * FROM ThuThu";
        return getData(sql);
    }
    //get tat ca theo id
    public ThuThu getID(String id){
        String sql = "SELECT * FROM ThuThu WHERE maTT=?";
        List<ThuThu>list = getData(sql,id);
        return list.get(0);
    }
    //get data tham so
    @SuppressLint("Range")
    private List<ThuThu>getData(String sql,String...selectionArgs){

        List<ThuThu>list = new ArrayList<ThuThu>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            ThuThu obj = new ThuThu();
            obj.maTT = c.getString(c.getColumnIndex("maTT"));
            obj.hoTen = c.getString(c.getColumnIndex("hoTen"));
            obj.matKhau = c.getString(c.getColumnIndex("matKhau"));
            list.add(obj);
        }
        return list;
    }

    //check login
    public int CheckLogin(String id,String password){
        String sql = "SELECT * FROM ThuThu WHERE maTT=? AND matkhau=?";
        List<ThuThu>list = getData(sql,id,password);
        if (list.size()==0)
            return -1;
        return 1;

    }
}
