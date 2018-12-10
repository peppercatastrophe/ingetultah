package com.example.kiruyos.myproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class MainActivity extends AppCompatActivity implements FragmentProfile.Listener, RecyclerViewAdapter.Listener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private FragmentProfile fragmentProfile = new FragmentProfile();
    private FragmentListData fragmentListData = new FragmentListData();
    private Button btn_setting1;
    private FloatingActionButton fab1;
    private LinearLayout bgProfil;
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.kiruyos.myproject";
    private int mColor;
    private int btColor;



    private int bgProfilState;

    private final String COLOR_KEY = "color";
    private final String BTCOLOR_KEY = "btcolor";
    private final String BGCOLOR_KEY = "bgcolor";


    @Override
    protected void onPause() {
        super.onPause();
        mColor = ((ColorDrawable) tabLayout.getBackground()).getColor();
        btColor = ((ColorDrawable) btn_setting1.getBackground()).getColor();
        SharedPreferences.Editor preferenceEditor = mPreferences.edit();
        preferenceEditor.putInt(COLOR_KEY, mColor);
        preferenceEditor.putInt(BTCOLOR_KEY, btColor);
        preferenceEditor.putInt(BGCOLOR_KEY, bgProfilState);
        preferenceEditor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        btn_setting1 = (Button) findViewById(R.id.btn_setting);
        bgProfil = (LinearLayout) findViewById(R.id.bgProfil);
        fab1 = (FloatingActionButton) findViewById(R.id.fab);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // set color
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        mColor = ((ColorDrawable) tabLayout.getBackground()).getColor();
        btColor = ((ColorDrawable) btn_setting1.getBackground()).getColor();
        mColor = mPreferences.getInt(COLOR_KEY, mColor);
        btColor = mPreferences.getInt(BTCOLOR_KEY, btColor);
        tabLayout.setBackgroundColor(mColor);
        btn_setting1.setBackgroundColor(btColor);


        adapter.AddFragment(fragmentListData,"");
        adapter.AddFragment(fragmentProfile,"");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_list);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_person_add_black_24dp);

        // tombol di pojok kanan atas
        btn_setting1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(view.getContext(), Setting.class);
                //myIntent.putExtra("setting", bgProfilState);
                startActivityForResult(myIntent, 1);
            }
        });

        // floating action button di kanan bawah
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Setting.class);
                startActivityForResult(myIntent, 1);
            }
        });
    }

    public void cngThm(int counter){
        if(counter == 1){
            tabLayout.setBackgroundColor(getResources().getColor(R.color.colorGray));
            btn_setting1.setBackgroundColor(getResources().getColor(R.color.colorPink));
            fragmentProfile.gantiBgProfil(1);
            bgProfilState = 1;
        } else if (counter == 2){
            tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPink));
            btn_setting1.setBackgroundColor(getResources().getColor(R.color.colorGray));
            fragmentProfile.gantiBgProfil(2);
            bgProfilState = 2;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    cngThm(data.getIntExtra("msg", 1));
                }
        }
    }

    @Override
    public void addItem(String name, Date date, String photopath, String phone){
        Toast.makeText(this, name, Toast.LENGTH_LONG).show();
        fragmentListData.addData(name, date, photopath, phone);
        ///adapter.notifyDataSetChanged();
    }

    public void rmvItem(int position){
        fragmentListData.rmvItem(position);
        Toast.makeText(this, "sini", Toast.LENGTH_LONG).show();
    }


    @Override
    public void test() {
        Log.e("asa","asa");
    }

}
