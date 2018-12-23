package com.lace.aj.lacer.traffic.activities;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lace.aj.lacer.R;
import com.lace.aj.lacer.traffic.adapters.MainActivityPagerAdapter;
import com.lace.aj.lacer.traffic.fragments.ProfilFragment;

public class MainActivity extends AppCompatActivity
        implements ProfilFragment.Listener{

    private MainActivityPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(
                new MainActivityPagerAdapter
                        (MainActivity.this,getSupportFragmentManager())
        );

    }

    @Override
    public void getUserContext(String text) {
        Toast.makeText(MainActivity.this,"Yloooooo",Toast.LENGTH_LONG).show();
    }
}
