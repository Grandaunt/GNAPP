package com.bcm.havoc.gnapp;


import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.bcm.havoc.gnapp.Adapter.BottomViewAdapter;
import com.bcm.havoc.gnapp.Fragment.HomeFragment;
import com.bcm.havoc.gnapp.Fragment.LookListFragment;
import com.bcm.havoc.gnapp.Fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener ,LookListFragment.OnFragmentInteractionListener ,MyFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;
    private String TAG = MainActivity.class.getSimpleName();
    private MyFragment mMyFragment;
    private LookListFragment mLookListFragment;
    private HomeFragment mHomeFragment;
    List<Fragment> list_fragment = new ArrayList<>();
    private int lastfragment;
    FragmentManager fm = this.getFragmentManager();
    private BottomNavigationView navigation;
    private ViewPager viewpager_launch;
    private MenuItem menuItem;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    viewpager_launch.setCurrentItem(0);
                    return true;
                case R.id.navigation_home:
                    viewpager_launch.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewpager_launch.setCurrentItem(2);
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewpager_launch = (ViewPager) findViewById(R.id.viewpager_launch);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setNavigation();
    }

    private void setNavigation() {
        viewpager_launch.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                menuItem = navigation.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        list_fragment.add(LookListFragment.newInstance("",""));
        list_fragment.add(HomeFragment.newInstance("",""));
        list_fragment.add(MyFragment.newInstance("",""));
        BottomViewAdapter adapter = new BottomViewAdapter(getSupportFragmentManager(), list_fragment);
        viewpager_launch.setAdapter(adapter);
        viewpager_launch.setOffscreenPageLimit(3);//设置缓存view 的个数
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
