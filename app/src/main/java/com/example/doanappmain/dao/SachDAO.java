package com.example.doanappmain.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import com.example.doanappmain.database.DbHelper;
import com.example.doanappmain.modol.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private SQLiteDatabase db;
    private Context context;
    public SachDAO(Context context){
        this.context=context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    // Them moi du lieu|| kieu du lieu: long
    public long insert(Sach obj){
        ContentValues values = new ContentValues();
        values.put("tenSach",obj.tenSach);
        values.put("giaThue",obj.giaThue);
        values.put("maLoai",obj.maLoai);
        return db.insert("Sach",null,values);
    }
    // cap nhat du lieu|| kieu du lieu Integer
    public int update(Sach obj){
        ContentValues values = new ContentValues();
        values.put("tenSach",obj.tenSach);
        values.put("giaThue",obj.giaThue);
        values.put("maLoai",obj.maLoai);
        return db.update("Sach",values,"maSach=?",new String[]{String.valueOf(obj.maSach)});
    }
    // Xoa du lieu|| kieu du lieu Integer
    public int delete(String id){
        //  show thong bao
        Toast.makeText(context.getApplicationContext(), "Xóa Thành công",Toast.LENGTH_SHORT).show();
        return db.delete("Sach","maSach=?",new String[]{id});
    }

    //get tat ca data
    public List<Sach> getAll(){
        String sql = "SELECT * FROM Sach";
        return getData(sql);
    }
    //get tat ca theo id
    public Sach getID(String id){
        String sql = "SELECT * FROM Sach WHERE maSach=?";
        List<Sach>list = getData(sql,id);
        return list.get(0);
    }
    //get data tham so
    @SuppressLint("Range")
    private List<Sach>getData(String sql,String...selectionArgs){

        List<Sach>list = new ArrayList<Sach>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            Sach obj = new Sach();
            obj.maSach = Integer.parseInt(c.getString(c.getColumnIndex("maSach")));
            obj.tenSach = c.getString(c.getColumnIndex("tenSach"));
            obj.giaThue = Integer.parseInt(c.getString(c.getColumnIndex("giaThue")));
            obj.maLoai = Integer.parseInt(c.getString(c.getColumnIndex("maLoai")));
            list.add(obj);
        }

        return list;
    }
}
