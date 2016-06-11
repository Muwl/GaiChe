package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.YxListEditAdapter;
import com.gaicheyunxiu.gaiche.model.CommodityEntity;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
import com.lidroid.xutils.BitmapUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/8.
 */
public class YxListEditActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;

    private TextView title;

    private BitmapUtils bitmapUtils;

    private ImageView carImage;

    private TextView carName;

    private  TextView serviceName;

    private TextView save;

    private ListView listView;

    private List<CommodityEntity> commodityEntities;

    private YxListEditAdapter adapter;

    private int position;

    private String serName;

    private String ids;



    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 4546:
                    int poi= (int) msg.obj;
                    Intent intent=new Intent(YxListEditActivity.this,YxListChangeActivity.class);
                    intent.putExtra("position",poi);
                    intent.putExtra("id",ids);
                    startActivityForResult(intent, 4715);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yxlistedit);
        initView();
    }

    private void initView() {
        bitmapUtils=new BitmapUtils(this);

        position=getIntent().getIntExtra("position", 0);
        serName=getIntent().getStringExtra("name");
        commodityEntities= (List<CommodityEntity>) getIntent().getSerializableExtra("entity");

        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        carImage= (ImageView) findViewById(R.id.yxlist_carimage);
        carName= (TextView) findViewById(R.id.yxlist_carbrand);
        serviceName= (TextView) findViewById(R.id.yxlist_name);
        save= (TextView) findViewById(R.id.yxlist_save);
        listView= (ListView) findViewById(R.id.yxlist_list);

        title.setText("养修");
        back.setOnClickListener(this);
        save.setOnClickListener(this);
        MyCarEntity carEntity= MyApplication.getInstance().getCarEntity();

        bitmapUtils.display(carImage, carEntity.carBrandLogo);
        carName.setText(carEntity.carBrandName+carEntity.type +carEntity.displacement+carEntity.productionDate+"年产");
        serviceName.setText(serName);

        adapter=new YxListEditAdapter(this,commodityEntities,handler);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;

            case R.id.yxlist_save:
                Intent intent=new Intent();
                intent.putExtra("position",position);
                intent.putExtra("entity", (Serializable) commodityEntities);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==4715 && resultCode==RESULT_OK){
            int poi=data.getIntExtra("position",0);
            if (poi==-1){
                Intent intent=new Intent();
                intent.putExtra("position",position);
                intent.putExtra("entity", (Serializable) commodityEntities);
                setResult(RESULT_OK,intent);
                finish();
            }else{
                CommodityEntity commodityEntity= (CommodityEntity) getIntent().getSerializableExtra("entity");
                commodityEntities.set(poi,commodityEntity);
                adapter.notifyDataSetChanged();
            }

        }
    }
}
