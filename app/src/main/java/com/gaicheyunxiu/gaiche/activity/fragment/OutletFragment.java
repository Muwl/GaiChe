package com.gaicheyunxiu.gaiche.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;


/**
 * Created by Administrator on 2015/12/19.
 */
public class OutletFragment extends Fragment {

    private TextView title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_store,container,false);
        title= (TextView) view.findViewById(R.id.title_text);
        title.setText("门店");
        return view;

    }
}
