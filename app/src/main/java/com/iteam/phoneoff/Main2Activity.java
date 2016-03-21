package com.iteam.phoneoff;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.iteam.phoneoff.base.Setting;
import com.iteam.phoneoff.base.User;
import com.iteam.phoneoff.service.MyService;
import com.iteam.phoneoff.utils.Utils;
import com.iteam.phoneoff.view.CircleBar;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.List;

public class Main2Activity extends TabActivity {

    public RadioGroup radioGroup;
    public TabHost mth;
    public static final String TAB_KITCHEN = "厨房";
    public static final String TAB_BALCONY = "阳台";
    public static final String TAB_WASHROOM = "洗手间";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);
        startService(new Intent(this, MyService.class));
        init();

        clickevent();


    }


    private void clickevent()
    {
        this.radioGroup = (RadioGroup) findViewById(R.id.main_radio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_button0:
                        mth.setCurrentTabByTag(TAB_KITCHEN);
                        break;
                    case R.id.radio_button1:
                        mth.setCurrentTabByTag(TAB_WASHROOM);
                        break;
                    case R.id.radio_button2:
                        mth.setCurrentTabByTag(TAB_BALCONY);
                        break;


                }
            }
        });
    }


    private void init()
    {

        mth = this.getTabHost();
        TabHost.TabSpec ts1 = mth.newTabSpec(TAB_KITCHEN).setIndicator(TAB_KITCHEN);
        ts1.setContent(new Intent(this, FirstActivity.class));
        mth.addTab(ts1);

        TabHost.TabSpec ts2 = mth.newTabSpec(TAB_BALCONY).setIndicator(TAB_BALCONY);
        ts2.setContent(new Intent(this, SettingActivity.class));
        mth.addTab(ts2);

        TabHost.TabSpec ts3 = mth.newTabSpec(TAB_WASHROOM).setIndicator(TAB_WASHROOM);
        ts3.setContent(new Intent(this, SecondActivity.class));
        mth.addTab(ts3);




    }
}
