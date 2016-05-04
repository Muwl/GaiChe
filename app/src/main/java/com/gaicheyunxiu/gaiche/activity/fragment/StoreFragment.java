package com.gaicheyunxiu.gaiche.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.ShopListActivity;
import com.gaicheyunxiu.gaiche.activity.SerchActivity;
import com.gaicheyunxiu.gaiche.adapter.FStoreAdapter;

/**
 * Created by Administrator on 2015/12/19.
 */
public class StoreFragment extends Fragment implements View.OnClickListener{

    private TextView title;

    private View brandlin;

    private ImageView brandImage;

    private TextView brandText;

    private ImageView serch;

    private TextView serchText;

//    private PullToRefreshExpandableListView listView;
    private ExpandableListView listView;

    private FStoreAdapter adapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_store,container,false);
        title= (TextView) view.findViewById(R.id.title_text);
        view.findViewById(R.id.title_back).setVisibility(View.GONE);
        brandlin=view.findViewById(R.id.fstore_carlin);
        brandText= (TextView) view.findViewById(R.id.fstore_carbrand);
        serch= (ImageView) view.findViewById(R.id.fstore_serch);
        serchText= (TextView) view.findViewById(R.id.fstore_serchtext);
        listView= (ExpandableListView) view.findViewById(R.id.fstore_listview);
        title.setText("商城");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter=new FStoreAdapter(getActivity());
        listView.setAdapter(adapter);
        serchText.setOnClickListener(this);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                LogManager.LogShow("----","----"+position,LogManager.ERROR);
//            }
//        });

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity(), ShopListActivity.class);
                startActivity(intent);
//                LogManager.LogShow("----","----"+groupPosition+"==="+childPosition+"==",LogManager.ERROR);
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fstore_serchtext:
                Intent intent=new Intent(getActivity(), SerchActivity.class);
                startActivity(intent);
                break;
        }
    }


}
