package com.example.doanappmain.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import androidx.fragment.app.Fragment;


import com.example.doanappmain.R;
import com.example.doanappmain.adapter.TopAdapter;
import com.example.doanappmain.dao.ThongKeDAO;
import com.example.doanappmain.modol.Top;

import java.util.ArrayList;

public class TopFragment extends Fragment {
    ListView lv;
    ArrayList<Top>list;
    TopAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_top, container, false);
        lv = v.findViewById(R.id.lvTop);
        animation();
        ThongKeDAO thongKeDAO = new ThongKeDAO(getActivity());
        list = (ArrayList<Top>)thongKeDAO.getTop();
        adapter = new TopAdapter(getActivity(),this,list);
        lv.setAdapter(adapter);

        return v;
    }
    public void animation(){
        Animation animationHour2 = AnimationUtils.loadAnimation(getActivity(),R.anim.translate1);
        lv.startAnimation(animationHour2);
    }

}