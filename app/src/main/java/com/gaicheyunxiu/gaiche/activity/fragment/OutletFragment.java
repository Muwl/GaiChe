package com.gaicheyunxiu.gaiche.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.SerchActivity;
import com.gaicheyunxiu.gaiche.adapter.FOuletAdapter;
import com.gaicheyunxiu.gaiche.utils.DensityUtil;

import java.util.List;


/**
 * Created by Administrator on 2015/12/19.
 */
public class OutletFragment extends Fragment implements View.OnClickListener{

    private TextView title;

    private View map;



    private ListView listView;

    private FOuletAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_outlet,container,false);
        title= (TextView) view.findViewById(R.id.title_text);
        view.findViewById(R.id.title_back).setVisibility(View.GONE);
        map=view.findViewById(R.id.title_map);

        listView= (ListView) view.findViewById(R.id.foutlet_listview);
        title.setText("门店");
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        map.setVisibility(View.VISIBLE);
        adapter=new FOuletAdapter(getActivity(), DensityUtil.getScreenWidth(getActivity()));
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){



        }
    }
}
