package com.example.doanappmain.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    static final String dbName = "PNLIB";
    static final int dbVersion = 3;
    public DbHelper(Context context){super(context,dbName,null,dbVersion);}

    @Override
    public void onCreate(SQLiteDatabase db) {



        //tao bang thu thu
        String createTableThuThu=
                "create table ThuThu("+
                        "maTT TEXT PRIMARY KEY,"+
                        "hoTen TEXT NOT NULL,"+
                        "matKhau TEXT NOT NULL)";
        db.execSQL(createTableThuThu);

        //tao bang thanh vien
        String createTableThanhVien=
                "create table ThanhVien("+
                        "maTV INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "hoTen TEXT NOT NULL,"+
                        "namSinh TEXT NOT NULL)";
        db.execSQL(createTableThanhVien);

        //tao bang loai sach

        String createTableLoaiSach=
                "create table LoaiSach("+
                        "maLoai INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "tenLoai TEXT NOT NULL)";
        db.execSQL(createTableLoaiSach);

        //tao bang sach

        String createTableSach=
                "create table Sach("+
                        "maSach INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "tenSach TEXT NOT NULL,"+
                        "giaThue INTEGER NOT NULL,"+
                        "maLoai INTEGER REFERENCES LoaiSach(maLoai))";
        db.execSQL(createTableSach);

        //tao bang phieu muon

        String createTablePhieuMuon=
                "create table PhieuMuon("+
                        "maPM INTEGER PRIMARY KEY AUTOINCREMENT,"+
//                        "maTT TEXT REFERENCES ThuThu(maTT),"+
                        "maTV INTEGER REFERENCES ThanhVien(maTV),"+
                        "maSach INTEGER REFERENCES Sach(maSach),"+
                        "ngay DATE NOT NULL ,"+
                        "tienThue INTEGER NOT NULL,"+
                        "traSach INTEGER NOT NULL)";

        db.execSQL(createTablePhieuMuon);

        db.execSQL(DbHelper1.INSERT_THU_THU);
        db.execSQL(DbHelper1.INSER_THANH_VIEN);
        db.execSQL(DbHelper1.INSERT_LOAI_SACH);
        db.execSQL(DbHelper1.INSERT_SACH);
        db.execSQL(DbHelper1.INSERT_PHIEU_MUON);

        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            String dropTableLoaiThuThu = "drop table if exists ThuThu";
            db.execSQL(dropTableLoaiThuThu);

            String dropTableThanhVien ="drop table if exists ThanhVien";
            db.execSQL(dropTableThanhVien);

            String dropTableLoaiSach = "drop table if exists LoaiSach";
            db.execSQL(dropTableLoaiSach);

            String dropTableSach = "drop table if exists Sach";
            db.execSQL(dropTableSach);

            String dropTablePhieuMuon = "drop table if exists PhieuMuon";
            db.execSQL(dropTablePhieuMuon);

            onCreate(db);
        }

}
