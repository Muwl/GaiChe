package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.SerchAdapter;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.SerchState;
import com.gaicheyunxiu.gaiche.model.ShopDetailState;
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
 * Created by Administrator on 2016/4/4.
 * 搜索页面
 */
public class SerchActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private EditText textView;

    private TextView serch;

    private ImageView cancel;

    private ListView listView;

    private SerchAdapter adapter;

    private View pro;

    List<String> strings;

    private String stag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serch);
        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title_text);
        back = (ImageView) findViewById(R.id.title_back);
        textView = (EditText) findViewById(R.id.serch_text);
        serch = (TextView) findViewById(R.id.serch_serch);
        cancel = (ImageView) findViewById(R.id.serch_cancel);
        listView = (ListView) findViewById(R.id.serch_listview);
        pro = findViewById(R.id.serch_pro);
        strings = new ArrayList<>();
        adapter = new SerchAdapter(this, strings);
        listView.setAdapter(adapter);
        title.setText("搜索");
        back.setOnClickListener(this);
        serch.setOnClickListener(this);
        cancel.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SerchActivity.this, ShopListActivity.class);
                intent.putExtra("comeFlag", 4);
                intent.putExtra("keywords", strings.get(position));
                startActivity(intent);
            }
        });

        textView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // LogManager.LogShow("--------", "aaaaaaaaaaaaaa",
                // LogManager.ERROR);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // LogManager
                // .LogShow("--------", "bbbbbbbbbbbb", LogManager.ERROR);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ToosUtils.isStringEmpty(s.toString())){
                    return;
                }
                if (!ToosUtils.isStringEmpty(s.toString())) {
                    stag = String.valueOf(System.currentTimeMillis());
                    getShopKeyWords(stag);

                } else {
                    strings.clear();
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.serch_serch:
                if (ToosUtils.isTextEmpty(textView)){
                    ToastUtils.displayShortToast(SerchActivity.this,"请输入要搜索的商品！");
                    return;
                }
                Intent intent = new Intent(SerchActivity.this, ShopListActivity.class);
                intent.putExtra("comeFlag", 4);
                intent.putExtra("keywords",ToosUtils.getTextContent(textView));
                startActivity(intent);
                break;
            case R.id.serch_cancel:
                textView.setText("");
                break;
        }
    }


    /**
     * 根据商品id查询商品详情
     */
    private void getShopKeyWords(final String tag) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        rp.addBodyParameter("keyword", ToosUtils.getTextContent(textView));
        utils.configTimeout(20000);
        RequestCallBack<String> requestCallBack =new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
//                pro.setVisibility(View.GONE);
//                ToastUtils.displayFailureToast(SerchActivity.this);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                LogManager.LogShow("-----", arg0.result + "=====",
                        LogManager.ERROR);
                try {
                    if (stag.equals(userTag)) {
                        Gson gson = new Gson();
                        ReturnState state = gson.fromJson(arg0.result,
                                ReturnState.class);
                        if (Constant.RETURN_OK.equals(state.msg)) {
                            strings.clear();
                            adapter.notifyDataSetChanged();
                            SerchState serchState = gson.fromJson(arg0.result, SerchState.class);
                            for (int i = 0; i < serchState.result.size(); i++) {
                                strings.add(serchState.result.get(i));
                            }
                            adapter.notifyDataSetChanged();
                        } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                            ToastUtils.displayShortToast(SerchActivity.this,
                                    "验证错误，请重新登录");
                            ToosUtils.goReLogin(SerchActivity.this);
                        } else {
                            ToastUtils.displayShortToast(SerchActivity.this,
                                    (String) state.result);
                        }
                    }
                } catch (Exception e) {
                }

            }
        };
        requestCallBack.setUserTag(tag);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                        + "commodity/findKeywords", rp,requestCallBack);
    }
}
