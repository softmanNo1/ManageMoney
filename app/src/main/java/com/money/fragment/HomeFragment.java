package com.money.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.money.R;
import com.money.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * Created by gsf on 15/7/8.
 */
public class HomeFragment extends Fragment {


    private ViewPager mNewsViewPager = null;

    private ImageView mNewsOne,mNewsTwo,mNewsThree;

    private List<View> mNewsList = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home_fragment,container,false);
        mNewsViewPager = (ViewPager) view.findViewById(R.id.home_news_view_pager);
        mNewsOne = (ImageView) view.findViewById(R.id.home_news_iv_one);
        mNewsTwo = (ImageView) view.findViewById(R.id.home_news_iv_two);
        mNewsThree = (ImageView) view.findViewById(R.id.home_news_iv_three);
        init();
        return view;
    }


    private void init(){


        mNewsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    mNewsOne.setBackgroundResource(R.drawable.guide_on);
                    mNewsTwo.setBackgroundResource(R.drawable.guide_off);
                    mNewsThree.setBackgroundResource(R.drawable.guide_off);

                } else if (position == 1) {
                    mNewsOne.setBackgroundResource(R.drawable.guide_off);
                    mNewsTwo.setBackgroundResource(R.drawable.guide_on);
                    mNewsThree.setBackgroundResource(R.drawable.guide_off);
                } else {
                    mNewsOne.setBackgroundResource(R.drawable.guide_off);
                    mNewsTwo.setBackgroundResource(R.drawable.guide_off);
                    mNewsThree.setBackgroundResource(R.drawable.guide_on);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ViewPagerAdapter adapter = new ViewPagerAdapter(mNewsList);
        mNewsViewPager.setAdapter(adapter);
    }


    private void initNews(){
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        mNewsList = new ArrayList<>();
        ImageView view1 = new ImageView(getActivity());
        view1.setImageResource(R.drawable.news_1);
        view1.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
        ImageView view2 = new ImageView(getActivity());
        view2.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
        view2.setImageResource(R.drawable.news_2);
        ImageView view3 = new ImageView(getActivity());
        view3.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
        view3.setImageResource(R.drawable.news_3);
        mNewsList.add(view1);
        mNewsList.add(view2);
        mNewsList.add(view3);
    }

}
