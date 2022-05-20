package com.example.doanappmain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.doanappmain.R;
import com.example.doanappmain.fragment.TopFragment;
import com.example.doanappmain.modol.Top;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TopAdapter extends ArrayAdapter<Top> {

    private Context context;
    TopFragment fragment;
    private ArrayList<Top>lists;
    TextView tvSach,tvSL;
    ImageView imgDel;

    public TopAdapter(@NonNull @NotNull Context context, TopFragment fragment, ArrayList<Top> lists) {
        super(context, 0,lists);
        this.context = context;
        this.fragment = fragment;
        this.lists = lists;
        this.tvSach = tvSach;
        this.tvSL = tvSL;
        this.imgDel = imgDel;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v =convertView;
        if (v==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.top_item,null);

        }
        final Top item = lists.get(position);
        if (item != null){
            tvSach = v.findViewById(R.id.tvSach);
            tvSach.setText(" Sách : "+item.tenSach);
            tvSL = v.findViewById(R.id.tvSL);
            tvSL.setText("Số Lượng :"+item.soLuong);
        }
        return v;
    }

}
