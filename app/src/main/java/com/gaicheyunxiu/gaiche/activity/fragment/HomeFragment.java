package com.gaicheyunxiu.gaiche.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.FHomeGrallryAdapter;
import com.gaicheyunxiu.gaiche.utils.DensityUtil;
import com.gaicheyunxiu.gaiche.view.MyGallery;


/**
 * Created by Administrator on 2015/12/19.
 */
public class HomeFragment extends Fragment {

    private ImageView code;

    private ImageView message;

    private TextView title;

    private MyGallery gallery1;

    private FHomeGrallryAdapter adapter;

    private  int width;

    private LinearLayout lin = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        code= (ImageView) view.findViewById(R.id.title_code);
        message= (ImageView) view.findViewById(R.id.title_message);
        title= (TextView) view.findViewById(R.id.title_text);
        gallery1= (MyGallery) view.findViewById(R.id.home_grally);
        lin = (LinearLayout) view.findViewById(R.id.home_lin);
        code.setVisibility(View.VISIBLE);
        message.setVisibility(View.VISIBLE);
        view.findViewById(R.id.title_back).setVisibility(View.GONE);
        title.setText("盖车云修");
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        width= DensityUtil.getScreenWidth(getActivity());
        adapter=new FHomeGrallryAdapter(getActivity(),width);
        gallery1.setAdapter(adapter);

        int m = DensityUtil.dip2px(getActivity(), 3);
        for (int i = 0; i <3; i++) {
            ImageView image = (ImageView) LayoutInflater.from(getActivity())
                    .inflate(R.layout.banner_point, null);
            image.setPadding(m, 0, m, 0);
            lin.addView(image);
        }
        gallery1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ImageView iv;
                int count = lin.getChildCount();
                for (int i = 0; i < count; i++) {
                    iv = (ImageView) lin.getChildAt(i);
                    if (i == position % 3) {
                        iv.setImageResource(R.drawable.circle_select);
                    } else {
                        iv.setImageResource(R.drawable.circle_normal);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
