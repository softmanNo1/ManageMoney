package com.money.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.money.R;
import com.money.fragment.HomeFragment;


public class MainActivity extends FragmentActivity {


    Fragment[] mFragments = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        HomeFragment mHomeFragment = new HomeFragment();
        mFragments = new Fragment[]{mHomeFragment};
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container,mHomeFragment)
                .commit();
    }


}
