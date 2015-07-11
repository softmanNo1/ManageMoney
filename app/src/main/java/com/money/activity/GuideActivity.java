package com.money.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.money.R;
import com.money.adapter.ViewPagerAdapter;
import com.money.common.IntentNavigation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gsf on 15/7/9.
 */
public class GuideActivity extends Activity{

    private ViewPager mViewPager = null;
    private Button mButton = null;
    private ImageView mImageViewOne = null;
    private ImageView mImageViewTwo = null;
    private ImageView mImageViewThree = null;

    private View mViewOne,mViewTwo,mViewThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_guide);
        mViewPager = (ViewPager) findViewById(R.id.guide_view_pager);
        mButton = (Button) findViewById(R.id.guide_button);
        mImageViewOne = (ImageView) findViewById(R.id.guide_iv_one);
        mImageViewTwo = (ImageView) findViewById(R.id.guide_iv_two);
        mImageViewThree = (ImageView) findViewById(R.id.guide_iv_three);
        init();
    }

    private void init(){
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(GuideActivity.this,MainActivity.class);
                IntentNavigation.startActivity(GuideActivity.this,in);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    mButton.setVisibility(View.VISIBLE);
                } else {
                    mButton.setVisibility(View.INVISIBLE);
                }
                if (position == 0) {
                    mImageViewOne.setBackgroundResource(R.drawable.guide_on);
                    mImageViewTwo.setBackgroundResource(R.drawable.guide_off);
                    mImageViewThree.setBackgroundResource(R.drawable.guide_off);

                } else if (position == 1) {
                    mImageViewOne.setBackgroundResource(R.drawable.guide_off);
                    mImageViewTwo.setBackgroundResource(R.drawable.guide_on);
                    mImageViewThree.setBackgroundResource(R.drawable.guide_off);
                } else {
                    mImageViewOne.setBackgroundResource(R.drawable.guide_off);
                    mImageViewTwo.setBackgroundResource(R.drawable.guide_off);
                    mImageViewThree.setBackgroundResource(R.drawable.guide_on);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        List<View> guideList = new ArrayList<View>();

        LayoutInflater layoutInflater = getLayoutInflater();
        mViewOne = layoutInflater.inflate(R.layout.layout_guide_frist, null);
        mViewTwo = layoutInflater.inflate(R.layout.layout_guide_second, null);
        mViewThree = layoutInflater.inflate(R.layout.layout_guide_third, null);

        guideList.add(mViewOne);
        guideList.add(mViewTwo);
        guideList.add(mViewThree);

        ViewPagerAdapter adapter = new ViewPagerAdapter(guideList);
        mViewPager.setAdapter(adapter);


    }


}
