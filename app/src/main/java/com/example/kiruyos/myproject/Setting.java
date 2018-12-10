package com.example.kiruyos.myproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Setting extends MainActivity {

    ToggleButton dark_and_dark;
    private TabLayout tabLayout2;
    private Button btPink;
    private Button btGray;

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.kiruyos.myproject";


    int mColor;
    String COLOR_KEY = "color";

    TextView tulisanSettings;

//    final LinearLayout MainActivity = (LinearLayout) findViewById(R.id.mainactivity);




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        // instansiasi
//        dark_and_dark = (ToggleButton) findViewById(R.id.toggleDark);
        tulisanSettings = (TextView) findViewById(R.id.tulisanSettings);
        tabLayout2 = (TabLayout) findViewById(R.id.tablayout);
        btPink = (Button) findViewById(R.id.tombolUbahWarnaPink);
        btGray = (Button) findViewById(R.id.tombolUbahWarnaGray);

        btPink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("msg", 2);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("msg", 1);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        // event listener
//        dark_and_dark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    Log.e("toggle", "enabled");
////                    new MainActivity().colorToGray();
//                }
//                else {
//                    Log.e("toggle","disabled");
////                    colorToPink();
//                }
//            }
//        });

        Intent intent = getIntent();
        int msg = intent.getIntExtra("msg",1);

    }
}
