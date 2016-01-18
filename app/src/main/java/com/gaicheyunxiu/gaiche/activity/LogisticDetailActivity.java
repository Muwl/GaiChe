package com.gaicheyunxiu.gaiche.activity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.LogisticDetailAdapter;
import com.gaicheyunxiu.gaiche.model.RouteInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mu on 2016/1/18.
 * 物流详情页面
 */
public class LogisticDetailActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private ListView listView;

    private LogisticDetailAdapter adapter;

    private List<RouteInfo> infos = new ArrayList<RouteInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logisticdetail);
        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        listView= (ListView) findViewById(R.id.logisticdetail_list);

        title.setText("查看物流");
        back.setOnClickListener(this);

        infos.add(new  RouteInfo("感谢您在京东购物，欢迎您再次光临 jd.com ", "2014-06-19 10:39:17"));
        infos.add(new  RouteInfo("配送员【李四】已出发，联系电话【18523456789】站点电话【13923459876】", "2014-06-19 10:14:17"));
        infos.add(new  RouteInfo("您的订单在【运城分拨中心】验货完成，正在分配派送员派件", "2014-06-19 09:34:38"));
        infos.add(new  RouteInfo("您的订单在【太原中转中心】验货完成，正在派往【运城分拨中心】", "2014-06-19 07:34:38"));
        infos.add(new  RouteInfo("你的订单在【太原分拨中心】发货完成，准备送往【太原中转中心】", "2014-06-18 20:14:07"));
        infos.add(new  RouteInfo("您的订单在【太原分拨中心】分拣完成", "2014-06-18 18:45:51"));
        infos.add(new  RouteInfo("您的订单已打包完毕", "2014-06-18 18:43:47"));
        infos.add(new RouteInfo("您的订单已通过扫描", "2014-06-18 18:27:10"));
        infos.add(new RouteInfo("您提交了订单，请等待系统确认", "2014-06-18 18:14:17"));
        adapter=new LogisticDetailAdapter(this,infos);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }
}
