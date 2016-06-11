package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.PartsAdapter;
import com.gaicheyunxiu.gaiche.model.CommodityEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/6/9.
 */
public class ShopClearInfoActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private ListView listView;

    private List<CommodityEntity> commodityEntities;

    private PartsAdapter partsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopclear);
        initView();
    }

    private void initView() {

        commodityEntities= (List<CommodityEntity>) getIntent().getSerializableExtra("entity");
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        listView= (ListView) findViewById(R.id.shopclear_list);

        partsAdapter=new PartsAdapter(this,commodityEntities);
        listView.setAdapter(partsAdapter);

        title.setText("商品清单");
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
        }
    }
}
