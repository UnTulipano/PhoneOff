package com.iteam.phoneoff;

import android.os.Bundle;
import android.widget.TextView;
import com.iteam.phoneoff.utils.Utils;
import com.iteam.phoneoff.view.CircleBar;
import net.tsz.afinal.annotation.view.ViewInject;

public class FirstActivity extends BaseActivity {

    //无需调用findViewById和setOnclickListener等
    @ViewInject(id=R.id.txt_times)
    TextView txt_times;
    @ViewInject(id=R.id.txt_minutes)
    TextView txt_minutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }


    void initView() {
        Utils.getYesterdayDistribute(this,-1);
        int times = Utils.getTodaySumTime(this);
        int minutes = Utils.timeInMillisToMinutes(Utils.getTodaySumMinutes(this));
        txt_times.setText("今天使用手机" + times + "次");
        txt_minutes.setText("今天共使用手机" + minutes + "分钟");
        CircleBar circleBar = (CircleBar)findViewById(R.id.circleBar);
        circleBar.setMaxstepnumber(PApplication.TIME);
        circleBar.update(minutes, 1000);
    }
}
