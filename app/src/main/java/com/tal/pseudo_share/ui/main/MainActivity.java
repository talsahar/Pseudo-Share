package com.tal.pseudo_share.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.model.authentication.AuthenticationRepository;
import com.tal.pseudo_share.ui.creation.CreatePseudoActivity;
import com.tal.pseudo_share.viewmodel.AuthenticationViewModel;
import com.tal.pseudo_share.viewmodel.MyPseudoViewModel;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private MyPseudoViewModel myPseudoViewModel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPseudoViewModel = ViewModelProviders.of(this).get(MyPseudoViewModel.class);
        progressBar = findViewById(R.id.progressBar);
        myPseudoViewModel.getProgressBarStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean)
                    progressBar.setVisibility(View.VISIBLE);
                else
                    progressBar.setVisibility(View.INVISIBLE);
            }
        });
        myPseudoViewModel.setProgressBarStatus(true);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        Fragment[] fragments;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new Fragment[3];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MyPseudoFragment();
                case 1:
                    return new OnlinePseudoFragment();
                case 2:
                    return new AboutFragment();
                default:
                    return null; // Problem occurs at this condition!
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
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
            case R.id.action_logout:
                ViewModelProviders.of(this).get(AuthenticationViewModel.class).signout();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


