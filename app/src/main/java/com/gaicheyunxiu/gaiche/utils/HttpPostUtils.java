package com.gaicheyunxiu.gaiche.utils;

import android.content.Context;
import android.os.Handler;

import com.gaicheyunxiu.gaiche.model.AdState;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.MyCarState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.mining.app.zxing.decoding.Intents;

import java.util.List;

/**
 * Created by Administrator on 2016/5/11.
 */
public class HttpPostUtils {

    public static final int FIND_MYCAR=16587;

    public static void getMyCar(Context context, final Handler handler){
        RequestParams rp = new RequestParams();
        if (ToosUtils.isStringEmpty(ShareDataTool.getToken(context))){
            MyCarEntityUtils carEntityUtils=new MyCarEntityUtils(context);
            MyCarEntity myCarEntity= carEntityUtils.getDefault();
            MyApplication.getInstance().setCarEntity(myCarEntity);
            handler.sendEmptyMessage(FIND_MYCAR);
            return;
        }
        if (MyApplication.getInstance().getCarEntity()!=null){
            return;
        }
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("sign", ShareDataTool.getToken(context));
        LogManager.LogShow("---------------",Constant.ROOT_PATH
                + "myCar/findAll?sign="+ShareDataTool.getToken(context),LogManager.ERROR);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "myCar/findAll", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {

            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        MyCarState myCarState=gson.fromJson(arg0.result,MyCarState.class);
                        List<MyCarEntity> entities=myCarState.result;
                        if (entities!=null && entities.size()>0){
                            MyCarEntity myCarEntity=null;
                            for (int i=0;i<entities.size();i++){
                                if (entities.get(i).isDefault){
                                    myCarEntity=entities.get(i);
                                }

                            }

                            if (myCarEntity==null){
                                myCarEntity=entities.get(0);
                            }
                            MyApplication.getInstance().setCarEntity(myCarEntity);
                            handler.sendEmptyMessage(FIND_MYCAR);
                        }
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                    }
                } catch (Exception e) {
                }

            }
        });
    }
}
