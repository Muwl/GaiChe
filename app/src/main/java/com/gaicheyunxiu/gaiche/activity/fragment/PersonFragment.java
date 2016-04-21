

package com.gaicheyunxiu.gaiche.activity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.CartActivity;
import com.gaicheyunxiu.gaiche.activity.EarningActivity;
import com.gaicheyunxiu.gaiche.activity.LoginActivity;
import com.gaicheyunxiu.gaiche.activity.LogisticActivity;
import com.gaicheyunxiu.gaiche.activity.MaintainActivity;
import com.gaicheyunxiu.gaiche.activity.MywalletActivity;
import com.gaicheyunxiu.gaiche.activity.PersonDataActivity;
import com.gaicheyunxiu.gaiche.activity.RaiseOrderActivity;
import com.gaicheyunxiu.gaiche.activity.ServiceOrderActivity;
import com.gaicheyunxiu.gaiche.activity.SettingActivity;
import com.gaicheyunxiu.gaiche.activity.ShipaddressActivity;
import com.gaicheyunxiu.gaiche.activity.ShopOrderActivity;
import com.gaicheyunxiu.gaiche.model.PersonDynamicEntity;
import com.gaicheyunxiu.gaiche.model.RegiterEntity;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.ChangeCharset;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.gaicheyunxiu.gaiche.view.RoundAngleImageView;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.UnsupportedEncodingException;


/**
 * Created by Administrator on 2015/12/19.
 */
public class PersonFragment extends Fragment implements View.OnClickListener{

    private static final int LOGIN_RETURN=100;

    private RoundAngleImageView icon;

    private TextView no;

    private TextView name;

    private TextView balance;

    private TextView title;

    private View dataView;

    private TextView rigService;

    private  View cartView;

    private View orderView;

    private View serviceOrderView;

    private View earningsView;

    private View moneyView;

    private View messageView;

    private View addressView;

    private View askView;

    private View maintainView;

    private View logisticsView;

    private View crowdorderView;

    private View settingView;

    private BitmapUtils bitmapUtils;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_person,container,false);
        title= (TextView) view.findViewById(R.id.title_text);
        rigService = (TextView) view.findViewById(R.id.title_service);
        icon= (RoundAngleImageView) view.findViewById(R.id.person_icon);
        no= (TextView) view.findViewById(R.id.person_no);
        name= (TextView) view.findViewById(R.id.person_name);
        balance= (TextView) view.findViewById(R.id.person_balance);
        dataView=view.findViewById(R.id.person_data);
        cartView=view.findViewById(R.id.person_cart);
        orderView=view.findViewById(R.id.person_order);
        serviceOrderView=view.findViewById(R.id.person_serviceorder);
        earningsView=view.findViewById(R.id.person_earnings);
        moneyView=view.findViewById(R.id.person_money);
        messageView=view.findViewById(R.id.person_message);
        addressView=view.findViewById(R.id.person_address);
        askView=view.findViewById(R.id.person_ask);
        maintainView=view.findViewById(R.id.person_maintain);
        logisticsView=view.findViewById(R.id.person_logistics);
        crowdorderView=view.findViewById(R.id.person_crowdorder);
        settingView=view.findViewById(R.id.person_setting);
        view.findViewById(R.id.title_back).setVisibility(View.GONE);
        title.setText("我的");
        return view;


    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rigService.setVisibility(View.VISIBLE);
        rigService.setOnClickListener(this);
        cartView.setOnClickListener(this);
        orderView.setOnClickListener(this);
        dataView.setOnClickListener(this);
        serviceOrderView.setOnClickListener(this);
        earningsView.setOnClickListener(this);
        moneyView.setOnClickListener(this);
        messageView.setOnClickListener(this);
        addressView.setOnClickListener(this);
        askView.setOnClickListener(this);
        maintainView.setOnClickListener(this);
        logisticsView.setOnClickListener(this);
        crowdorderView.setOnClickListener(this);
        settingView.setOnClickListener(this);
        bitmapUtils=new BitmapUtils(getActivity());
        reFushPersonInfo();
        getDynamic();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.title_service:

                break;

            case R.id.person_data:
                if(ToosUtils.isStringEmpty(ShareDataTool.getToken(getActivity()))){
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, LOGIN_RETURN);
                }else{
                    Intent intent=new Intent(getActivity(), PersonDataActivity.class);
                    startActivity(intent);
                }


                break;

            case R.id.person_cart:
                Intent intent2=new Intent(getActivity(), CartActivity.class);
                startActivity(intent2);

                break;

            case R.id.person_order:
                Intent intent3=new Intent(getActivity(), ShopOrderActivity.class);
                startActivity(intent3);
                break;


            case R.id.person_serviceorder:
                Intent intent4=new Intent(getActivity(), ServiceOrderActivity.class);
                startActivity(intent4);

                break;

            case R.id.person_earnings:
                Intent intent5=new Intent(getActivity(), EarningActivity.class);
                startActivity(intent5);
                break;

            case R.id.person_money:
                Intent intent6=new Intent(getActivity(),  MywalletActivity.class);
                startActivity(intent6);
                break;

            case R.id.person_message:

                break;

            case R.id.person_address:
                Intent intent8=new Intent(getActivity(), ShipaddressActivity.class);
                startActivity(intent8);

                break;

            case R.id.person_ask:

                break;

            case R.id.person_maintain:
                Intent intent9=new Intent(getActivity(), MaintainActivity.class);
                startActivity(intent9);

                break;

            case R.id.person_logistics:
                Intent intent10=new Intent(getActivity(), LogisticActivity.class);
                startActivity(intent10);

                break;

            case R.id.person_crowdorder:
                Intent intent11=new Intent(getActivity(), RaiseOrderActivity.class);
                startActivity(intent11);
                break;

            case R.id.person_setting:
                Intent intent12=new Intent(getActivity(), SettingActivity.class);
                startActivity(intent12);

                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!= Activity.RESULT_OK){
            return;
        }
        if (requestCode==LOGIN_RETURN){
            reFushPersonInfo();
        }
    }

    public void reFushPersonInfo(){
        RegiterEntity regiterEntity=ShareDataTool.getRegiterEntity(getActivity());
        if (regiterEntity!=null){
            bitmapUtils.display(icon, regiterEntity.icon);
            RegiterEntity entity=ShareDataTool.getRegiterEntity(getActivity());
            no.setText(entity.gcCode);
            name.setText(entity.nickname);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDynamic();
    }

    /**
     * 获取我的动态数
     */
    private void getDynamic() {
        if (ToosUtils.isStringEmpty(ShareDataTool.getToken(getActivity()))){
            return;
        }
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(getActivity()));
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "user/findUserDynamic", rp, new RequestCallBack<String>() {
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
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        PersonDynamicEntity dynamicEntity = gson.fromJson(arg0.result,
                                PersonDynamicEntity.class);
                        setvalue(dynamicEntity.result);

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
        });
    }

    private void setvalue(PersonDynamicEntity.ResultBean resultBean){
        balance.setText("余额：￥"+resultBean.balance);
    }
}
