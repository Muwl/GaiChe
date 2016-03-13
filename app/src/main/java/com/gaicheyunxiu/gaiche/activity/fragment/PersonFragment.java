

package com.gaicheyunxiu.gaiche.activity.fragment;

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
import com.gaicheyunxiu.gaiche.activity.LogisticActivity;
import com.gaicheyunxiu.gaiche.activity.MaintainActivity;
import com.gaicheyunxiu.gaiche.activity.MywalletActivity;
import com.gaicheyunxiu.gaiche.activity.PersonDataActivity;
import com.gaicheyunxiu.gaiche.activity.RaiseOrderActivity;
import com.gaicheyunxiu.gaiche.activity.ServiceOrderActivity;
import com.gaicheyunxiu.gaiche.activity.SettingActivity;
import com.gaicheyunxiu.gaiche.activity.ShipaddressActivity;
import com.gaicheyunxiu.gaiche.activity.ShopOrderActivity;
import com.gaicheyunxiu.gaiche.utils.ChangeCharset;
import com.gaicheyunxiu.gaiche.view.RoundAngleImageView;
import com.lidroid.xutils.BitmapUtils;

import java.io.UnsupportedEncodingException;


/**
 * Created by Administrator on 2015/12/19.
 */
public class PersonFragment extends Fragment implements View.OnClickListener{

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
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.title_service:

                break;

            case R.id.person_data:
                Intent intent=new Intent(getActivity(), PersonDataActivity.class);
                startActivity(intent);

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
}
