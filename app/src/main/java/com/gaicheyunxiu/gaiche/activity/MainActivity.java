package com.gaicheyunxiu.gaiche.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.fragment.HomeFragment;
import com.gaicheyunxiu.gaiche.activity.fragment.OutletFragment;
import com.gaicheyunxiu.gaiche.activity.fragment.PersonFragment;
import com.gaicheyunxiu.gaiche.activity.fragment.StoreFragment;
import com.gaicheyunxiu.gaiche.utils.FileTool;

import java.io.IOException;


public class MainActivity extends BaseActivity {

    private static FragmentManager fMgr;

    private RadioButton home;

    private RadioButton store;

    private RadioButton outlet;

    private RadioButton person;

    private RadioGroup group;

    private int pageIndex = 1;// 1首页 2商城 3门店 4我的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            FileTool.copyAssetFileToDatabase(this, "city.db","city.db");
        } catch (IOException e) {
            e.printStackTrace();
        }
        fMgr = getSupportFragmentManager();
        initFragment();
        home= (RadioButton) findViewById(R.id.main_bottom_home);
        store= (RadioButton) findViewById(R.id.main_bottom_store);
        outlet= (RadioButton) findViewById(R.id.main_bottom_outlet);
        person= (RadioButton) findViewById(R.id.main_bottom_person);
        group= (RadioGroup) findViewById(R.id.main_rg);

        group.check(R.id.main_bottom_home);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_bottom_home:
                        pageIndex = 1;
                        try {
                            if ((fMgr.findFragmentByTag("HomeFragment") != null && fMgr
                                    .findFragmentByTag("HomeFragment")
                                    .isVisible())) {
                                return;
                            }
                            HomeFragment fragment = (HomeFragment) fMgr
                                    .findFragmentByTag("HomeFragment");
                            // 当前页面如果为聊天历史页面，刷新此页面
                            popAllFragmentsExceptTheBottomOne();
                        } catch (Exception e) {
                        }

                        break;
                    case R.id.main_bottom_store:
                        pageIndex = 2;
                        try {
                            popAllFragmentsExceptTheBottomOne();
                            FragmentTransaction ft1 = fMgr.beginTransaction();
                            ft1.hide(fMgr.findFragmentByTag("HomeFragment"));
                            StoreFragment storeFragment = new StoreFragment();
                            ft1.add(R.id.main_fragment, storeFragment,
                                    "StoreFragment");
                            ft1.addToBackStack("StoreFragment");
                            ft1.commitAllowingStateLoss();
                        } catch (Exception e) {
                        }

                        break;
                    case R.id.main_bottom_outlet:
                        pageIndex = 3;
                        try {
                            popAllFragmentsExceptTheBottomOne();
                            FragmentTransaction ft = fMgr.beginTransaction();
                            ft.hide(fMgr.findFragmentByTag("HomeFragment"));
                            OutletFragment outletFragment = new OutletFragment();
                            ft.add(R.id.main_fragment, outletFragment,
                                    "OutletFragment");
                            ft.addToBackStack("OutletFragment");
                            ft.commitAllowingStateLoss();
                        } catch (Exception e) {
                        }

                        break;
                    case R.id.main_bottom_person:
                        pageIndex = 4;
                        try {
                            popAllFragmentsExceptTheBottomOne();
                            FragmentTransaction ft2 = fMgr.beginTransaction();
                            ft2.hide(fMgr.findFragmentByTag("HomeFragment"));
                            PersonFragment personFragment = new PersonFragment();
                            ft2.add(R.id.main_fragment, personFragment,
                                    "PersonFragment");
                            ft2.addToBackStack("PersonFragment");
                            ft2.commitAllowingStateLoss();

                        } catch (Exception e) {
                        }

                        break;
                    default:
                        break;
                }
            }
        });


    }

    /**
     * 初始化首个Fragment
     */
    private void initFragment() {
        try {
            FragmentTransaction ft = fMgr.beginTransaction();
            HomeFragment homeFragment = new HomeFragment();
            ft.add(R.id.main_fragment, homeFragment, "HomeFragment");
            ft.addToBackStack("HomeFragment");
            ft.commitAllowingStateLoss();
        } catch (Exception e) {

        }

    }

    /**
     * 从back stack弹出所有的fragment，保留首页的那个
     */
    public static void popAllFragmentsExceptTheBottomOne() {
        for (int i = 0, count = fMgr.getBackStackEntryCount() - 1; i < count; i++) {
            fMgr.popBackStack();
        }

    }


    // 点击返回按钮
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
