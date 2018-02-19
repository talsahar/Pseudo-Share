package com.tal.pseudo_share.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tal.pseudo_share.ui.external.ExternalActivity;
import com.tal.pseudo_share.R;
import com.tal.pseudo_share.ui.BaseActivity;
import com.tal.pseudo_share.ui.creation.CreatePseudoActivity;
import com.tal.pseudo_share.ui.login.LoginActivity;
import com.tal.pseudo_share.ui.main.categories.CategoriesMainFragment;
import com.tal.pseudo_share.viewmodel.AllPseudoViewModel;
import com.tal.pseudo_share.viewmodel.AuthenticationViewModel;

import java.util.HashMap;

public class MainActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       AllPseudoViewModel viewModel=ViewModelProviders.of(this).get(AllPseudoViewModel.class);
       viewModel.initRepository();


        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreatePseudoActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                break;
            case R.id.action_about:
                Intent intent = new Intent(this, ExternalActivity.class);
                startActivity(intent);
                break;
            case R.id.action_logout:
                ViewModelProviders.of(this).get(AuthenticationViewModel.class).signout();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Toolbar getToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pseudo-Share");
        return toolbar;
    }

    @Override
    public int getParentId() {
        return R.id.main_content;
    }


    @Override
    public ProgressBar loadProgressBar() {
        return findViewById(R.id.progressBar);
    }


    @Override
    public HashMap<Integer, Fragment> getInitialFragments() {
        return null;
    }

}

class SectionsPagerAdapter extends FragmentPagerAdapter {

    HashMap<Integer, Fragment> fragmentHashMap;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentHashMap = new HashMap<>();
        fragmentHashMap.put(0, new MyPseudoFragment());
        fragmentHashMap.put(1, new OnlinePseudoFragment());
        fragmentHashMap.put(2, new CategoriesMainFragment());

    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = fragmentHashMap.get(position);
        if (fragment == null)
            throw new RuntimeException("Error loading fragment please check your SectionsPagerAdapter");
        return fragment;
    }

    @Override
    public int getCount() {
        return fragmentHashMap.size();
    }

}


