package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.fragment.OutletFragment;
import com.gaicheyunxiu.gaiche.adapter.FSupportAdapter;
import com.gaicheyunxiu.gaiche.adapter.FacialAdapter;
import com.gaicheyunxiu.gaiche.model.ButyServiceEntity;
import com.gaicheyunxiu.gaiche.model.ButyServiceState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.SupportEntity;
import com.gaicheyunxiu.gaiche.model.SupportState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/27.
 * 美容页面
 */
public class FcialActivity extends BaseActivity  implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private ListView listView;

    private TextView ok;

    private FacialAdapter adapter;

    private View pro;

    private List<ButyServiceEntity> entities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facial);
        initView();

        if (ToosUtils.goBrand(this,0)){
            return;
        }
    }

    private void initView() {
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        listView= (ListView) findViewById(R.id.facical_listview);
        ok= (TextView) findViewById(R.id.facical_ok);
        pro= findViewById(R.id.facical_pro);

        entities=new ArrayList<>();
        back.setOnClickListener(this);
        ok.setOnClickListener(this);
        title.setText("美容");
        adapter=new FacialAdapter(this,entities);
        listView.setAdapter(adapter);
        getSupport();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.facical_ok:
                Intent intent=new Intent(FcialActivity.this, OultSelActivity.class);
                intent.putExtra("flag",1);
                String ids="";
                for (int i=0;i<entities.size();i++){
                    if (entities.get(i).isSelect){
                        ids=ids+entities.get(i).id+",";
                    }
                }
                if (ids.length()>0){
                    ids=ids.substring(0,(ids.length()-1));
                }
                intent.putExtra("ids",ids);
                intent.putExtra("flag",1);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!=RESULT_OK){
            finish();
        }
    }


    /**
     * 查询所有品牌
     */
    private void getSupport() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "service/beautyService", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(FcialActivity.this);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        ButyServiceState supportState=gson.fromJson(arg0.result,ButyServiceState.class);
                        for (int i=0;i<supportState.result.size();i++){
                            entities.add(supportState.result.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(FcialActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(FcialActivity.this);
                    } else {
                        ToastUtils.displayShortToast(FcialActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(FcialActivity.this);
                }

            }
        });
    }
}
