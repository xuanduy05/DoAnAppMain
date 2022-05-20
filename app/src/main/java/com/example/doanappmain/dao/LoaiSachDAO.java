package com.example.doanappmain.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import com.example.doanappmain.database.DbHelper;
import com.example.doanappmain.modol.LoaiSach;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {
    private SQLiteDatabase db;
    private Context context;
    public LoaiSachDAO(Context context){
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(LoaiSach obj){
        ContentValues values = new ContentValues();
        values.put("tenLoai",obj.tenLoai);

        return db.insert("LoaiSach",null,values);
    }
    public int update(LoaiSach obj){
        ContentValues values = new ContentValues();
        values.put("tenLoai",obj.tenLoai);
        return db.update("LoaiSach",values,"maLoai=?",new String[]{String.valueOf(obj.maLoai)});
    }

    public int delete(String id){
        Toast.makeText(context.getApplicationContext(), "Xóa Thành công",Toast.LENGTH_SHORT).show();

        return db.delete("LoaiSach","maLoai=?",new String[]{id});
    }

    //get tat ca data
    public List<LoaiSach>getAll(){
        String sql = "SELECT * FROM LoaiSach";

        return getData(sql);
    }
    //get tat ca theo id
    public LoaiSach getID(String id){
        String sql = "SELECT * FROM LoaiSach WHERE maLoai=?";
        List<LoaiSach>list = getData(sql,id);
        return list.get(0);
    }
    //get data tham so
    @SuppressLint("Range")
    private List<LoaiSach>getData(String sql,String...selectionArgs){

        List<LoaiSach>list = new ArrayList<LoaiSach>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            LoaiSach obj = new LoaiSach();
            obj.maLoai = Integer.parseInt(c.getString(c.getColumnIndex("maLoai")));
            obj.tenLoai = c.getString(c.getColumnIndex("tenLoai"));

            list.add(obj);
        }
        return list;
    }
}
