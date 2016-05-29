package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.CityGridAdapter;
import com.gaicheyunxiu.gaiche.adapter.CityListAdapter;
import com.gaicheyunxiu.gaiche.utils.CityComparator;
import com.gaicheyunxiu.gaiche.utils.CityDBUtils;
import com.gaicheyunxiu.gaiche.utils.CityEntity;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.gaicheyunxiu.gaiche.view.sortlistview.SideBar;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class CitySelActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private TextView localText;

    private ListView listView;

    private GridView gridView;

    private TextView dialog;

    private SideBar sideBar;

    private CityGridAdapter gridAdapter;

    private CityListAdapter listAdapter;

    private List<CityEntity> gridEntities;

    private List<CityEntity> listEntities;

    private Handler handler;

    private String cityname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citysel);
        initView();
    }

    private void initView() {
        cityname= MyApplication.getInstance().getCityEntity().name;
        title = (TextView) findViewById(R.id.title_text);
        back = (ImageView) findViewById(R.id.title_back);
        listView = (ListView) findViewById(R.id.citysel_list);
        localText = (TextView) findViewById(R.id.citysel_localname);
        gridView = (GridView) findViewById(R.id.citysel_mygrid);
        dialog = (TextView) findViewById(R.id.citysel_dialog);
        sideBar = (SideBar) findViewById(R.id.citysel_sidrbar);

        back.setOnClickListener(this);
        title.setText("选择城市");

        if (!ToosUtils.isStringEmpty(cityname)){
            localText.setText(cityname);
        }

        listEntities= CityDBUtils.getCity(this);
        LogManager.LogShow("---------",listEntities.toString(),LogManager.ERROR);
        sideBar.setTextView(dialog);
        Collections.sort(listEntities, new CityComparator());
        listAdapter=new CityListAdapter(this,listEntities);
        listView.setAdapter(listAdapter);
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = listAdapter
                        .getPositionForSection(s
                                .charAt(0));
                if (position != -1) {
                    listView.setSelection(position);
                }

            }
        });

        gridEntities=CityDBUtils.getHotCity(this);
        gridAdapter=new CityGridAdapter(this,gridEntities);
        gridView.setAdapter(gridAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                MyApplication.getInstance().setCityEntity(gridEntities.get(position));
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                MyApplication.getInstance().setCityEntity(listEntities.get(position));
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;

        }
    }
}
