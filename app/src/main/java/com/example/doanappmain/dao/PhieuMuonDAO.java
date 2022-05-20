package com.example.doanappmain.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;


import com.example.doanappmain.database.DbHelper;
import com.example.doanappmain.modol.PhieuMuon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private SQLiteDatabase db;
    private Context context;

    public PhieuMuonDAO(Context context){
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(PhieuMuon obj){
        ContentValues values = new ContentValues();
//        values.put("maTT",obj.maTT);
        values.put("maTV",obj.maTV);
        values.put("maSach",obj.maSach);
        values.put("ngay", sdf.format(obj.ngay));
         values.put("tienThue",obj.tienThue);
        values.put("traSach",obj.traSach);

        return db.insert("PhieuMuon",null,values);
    }

    public int update(PhieuMuon obj){
        ContentValues values = new ContentValues();
//        values.put("maTT",obj.maTT);
        values.put("maTV",obj.maTV);
        values.put("maSach",obj.maSach);
        values.put("ngay", sdf.format(obj.ngay));
        values.put("tienThue",obj.tienThue);
        values.put("traSach",obj.traSach);
        return db.update("PhieuMuon",values,"maPM=?",new String[]{String.valueOf(obj.maPM)});
    }
    public int delete(String id){
        Toast.makeText(context.getApplicationContext(), "Xóa Thành công",Toast.LENGTH_SHORT).show();
        return db.delete("PhieuMuon","maPM=?",new String[]{id});
    }

    //get data nhieu tham so
    @SuppressLint("Range")
    private List<PhieuMuon> getData(String sql, String...selectionArgs){

        List<PhieuMuon>list = new ArrayList<PhieuMuon>();

        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            PhieuMuon obj = new PhieuMuon();
            SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");

            obj.maPM = Integer.parseInt(c.getString(c.getColumnIndex("maPM")));
//            obj.maTT =c.getString(c.getColumnIndex("maTT"));
            obj.maTV = Integer.parseInt(c.getString(c.getColumnIndex("maTV")));
            obj.maSach=Integer.parseInt(c.getString(c.getColumnIndex("maSach")));
            obj.tienThue=Integer.parseInt(c.getString(c.getColumnIndex("tienThue")));

            try {
                obj.ngay=sdf.parse(c.getString(c.getColumnIndex("ngay")));
            }catch (ParseException e){
                e.printStackTrace();
            }
            obj.traSach=Integer.parseInt(c.getString(c.getColumnIndex("traSach")));
            Log.i("//======",obj.toString());
            list.add(obj);
        }
        return list;
    }
    //get all
    public List<PhieuMuon>getAll(){
        String sql = "SELECT * FROM PhieuMuon";
        return getData(sql);
    }
    //getdata theo id
    public PhieuMuon getID(String id){
        String sql = "SELECT * FROM PhieuMuon WHERE maPM=?";
        List<PhieuMuon>list=getData(sql,id);
        return list.get(0);
    }
}
