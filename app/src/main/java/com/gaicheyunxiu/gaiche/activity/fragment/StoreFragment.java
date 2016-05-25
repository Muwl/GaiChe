package com.gaicheyunxiu.gaiche.activity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.CarbrandActivity;
import com.gaicheyunxiu.gaiche.activity.CarmanagerActivity;
import com.gaicheyunxiu.gaiche.activity.ShopListActivity;
import com.gaicheyunxiu.gaiche.activity.SerchActivity;
import com.gaicheyunxiu.gaiche.adapter.FOuletAdapter;
import com.gaicheyunxiu.gaiche.adapter.FStoreAdapter;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.model.ShopState;
import com.gaicheyunxiu.gaiche.model.ShopTypeChildState;
import com.gaicheyunxiu.gaiche.model.ShopTypeEntity;
import com.gaicheyunxiu.gaiche.model.ShopTypeState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.DensityUtil;
import com.gaicheyunxiu.gaiche.utils.HttpPostUtils;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

/**
 * Created by Administrator on 2015/12/19.
 */
public class StoreFragment extends Fragment implements View.OnClickListener{

    private TextView title;

    private View brandlin;

    private ImageView carImage;

    private ImageView carAddImage;

    private TextView carName;

    private ImageView brandImage;

    private TextView brandText;

    private ImageView serch;

    private TextView serchText;

    private View pro;

    private ExpandableListView listView;

    private FStoreAdapter adapter;

    private List<ShopTypeEntity> entities;

    private BitmapUtils bitmapUtils;


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HttpPostUtils.FIND_MYCAR:
                    MyCarEntity carEntity=MyApplication.getInstance().getCarEntity();
                    if (carEntity!=null){
                        bitmapUtils.display(carImage,carEntity.carBrandLogo);
                        carAddImage.setVisibility(View.GONE);
                        carName.setText(carEntity.carBrandName+carEntity.displacement+carEntity.productionDate);
                    }
                    break;
            }
        };
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_store,container,false);
        title= (TextView) view.findViewById(R.id.title_text);
        view.findViewById(R.id.title_back).setVisibility(View.GONE);
        brandlin=view.findViewById(R.id.fstore_carlin);
        carImage= (ImageView) view.findViewById(R.id.fstore_carimage);
        carAddImage= (ImageView) view.findViewById(R.id.fstore_caraddimage);
        carName= (TextView) view.findViewById(R.id.fstore_carbrand);
        brandText= (TextView) view.findViewById(R.id.fstore_carbrand);
        serch= (ImageView) view.findViewById(R.id.fstore_serch);
        serchText= (TextView) view.findViewById(R.id.fstore_serchtext);
        listView= (ExpandableListView) view.findViewById(R.id.fstore_listview);
        pro= view.findViewById(R.id.fstore_pro);
        title.setText("商城");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bitmapUtils=new BitmapUtils(getActivity());
        serchText.setOnClickListener(this);
        brandlin.setOnClickListener(this);
        listView.setGroupIndicator(null);
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (entities.get(groupPosition).childEntities==null){
                    getShopType(entities.get(groupPosition).id,groupPosition);
                    return true;
                }
                return false;
            }
        });

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity(), ShopListActivity.class);
                intent.putExtra("category",entities.get(groupPosition).name);
                intent.putExtra("cattype",entities.get(groupPosition).childEntities.get(childPosition).name);
                intent.putExtra("comeFlag",3);
                startActivity(intent);
                return true;
            }
        });
        getShopType("-1", 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        MyCarEntity carEntity=MyApplication.getInstance().getCarEntity();
        if (carEntity!=null){
            bitmapUtils.display(carImage,carEntity.carBrandLogo);
            carAddImage.setVisibility(View.GONE);
            carName.setText(carEntity.carBrandName+carEntity.displacement+carEntity.productionDate);
        }else{
            HttpPostUtils.getMyCar(getActivity(), handler);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fstore_serchtext:
                Intent intent=new Intent(getActivity(), SerchActivity.class);
                startActivity(intent);
                break;

            case R.id.fstore_carlin:
                MyCarEntity carEntity= MyApplication.getInstance().getCarEntity();
                if (carEntity==null){
                    ToosUtils.goBrand(getActivity(),1);
                }else{
                    Intent intent1=new Intent(getActivity(), CarmanagerActivity.class);
                    startActivityForResult(intent1, 8856);
                }

                break;
        }
    }

    public void onRefush(){
        MyCarEntity carEntity= MyApplication.getInstance().getCarEntity();
        if (carEntity!=null){
            bitmapUtils.display(carImage,carEntity.carBrandLogo);
            carAddImage.setVisibility(View.GONE);
            carName.setText(carEntity.carBrandName+carEntity.type+carEntity.displacement+carEntity.productionDate);
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==1224 && resultCode== Activity.RESULT_OK){
//            MyCarEntity carEntity= MyApplication.getInstance().getCarEntity();
//            if (carEntity!=null){
//                bitmapUtils.display(carImage,carEntity.carBrandLogo);
//                carAddImage.setVisibility(View.GONE);
//                carName.setText(carEntity.carBrandName+carEntity.type+carEntity.displacement+carEntity.productionDate);
//            }
//            LogManager.LogShow("----------**&&",carEntity.toString(),LogManager.ERROR);
//        }
//
//    }
    /**
     * 查询所有商品分类
     */
    private void getShopType(final String parentId, final int position) {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("parentId", parentId);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "commodityType/find", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(getActivity());
                pro.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);

                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----++++", arg0.result,
                                LogManager.ERROR);
                        if ("-1".equals(parentId)){
                            ShopTypeState typeState=gson.fromJson(arg0.result,ShopTypeState.class);
                            entities=typeState.result;
                            adapter=new FStoreAdapter(getActivity(),entities);
                            listView.setAdapter(adapter);
                        }else{
                            ShopTypeChildState typeState=gson.fromJson(arg0.result,ShopTypeChildState.class);
                            entities.get(position).childEntities=typeState.result;
                            listView.expandGroup(position);
                            adapter.notifyDataSetChanged();
                        }

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(getActivity(),
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(getActivity());
                    } else {
                        ToastUtils.displayShortToast(getActivity(),
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(getActivity());
                }

            }
        });}


}
