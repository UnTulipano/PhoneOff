package com.iteam.phoneoff;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface.OnMultiChoiceClickListener;

import com.iteam.phoneoff.base.Setting;
import com.iteam.phoneoff.utils.Utils;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

public class SettingActivity extends BaseActivity {

    //无需调用findViewById和setOnclickListener等
    @ViewInject(id = R.id.textView, click = "btnClick")
    TextView textView;
    @ViewInject(id = R.id.textView2, click = "btnClick")
    TextView textView2;
    @ViewInject(id = R.id.textView3, click = "btnClick")
    TextView textView3;
    @ViewInject(id = R.id.textView4, click = "onClick_remind")
    TextView text_remind;
    @ViewInject(id = R.id.textView5, click = "onClick_clean")
    TextView text_clean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);


    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        //toast(this, "这里是SettingActicity onResume");
    }

    private void initView() {
        textView2.setText(PApplication.TIME + "");

    }


    public void btnClick(View v) {
        final EditText editText = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("请输入时间")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String time = editText.getText().toString();
                        FinalDb db = FinalDb.create(getApplication());
                        Setting setting = new Setting();
                        setting.setGoal(Integer.parseInt(time));
                        setting.setDate(Utils.getNowData());
                        db.save(setting);
                        textView2.setText(time);
                        PApplication.TIME = Integer.parseInt(time);
                        judge();  //判断是否超出规定时间
                        //Toast.makeText(getApplicationContext(), time + "存储成功", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", null).show();

    }

    public void onClick_remind(View v) {
        final String[] multiChoiceItems = {"发送通知", "震动提醒"};

        final SharedPreferences sharedPreferences = getSharedPreferences("actm", Context.MODE_PRIVATE);
        boolean style1 = sharedPreferences.getBoolean("style1", true);
        boolean style2 = sharedPreferences.getBoolean("style2", true);
        final boolean[] defaultSelectedStatus = {style1, style2};
        final StringBuffer sb = new StringBuffer();
        DialogInterface.OnMultiChoiceClickListener mutiListener =
                new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface,
                                        int which, boolean isChecked) {
                        defaultSelectedStatus[which] = isChecked;
                    }
                };
        new AlertDialog.Builder(this)
                .setTitle("选择提醒方式")
                .setMultiChoiceItems(multiChoiceItems, defaultSelectedStatus, mutiListener)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putBoolean("style1", defaultSelectedStatus[0]);
                        editor.putBoolean("style2", defaultSelectedStatus[1]);
                        editor.commit();//提交修改


                    }
                }).setNegativeButton("取消", null)
                .show();

    }

    public void onClick_clean(View v) {
        new AlertDialog.Builder(this)
                .setMessage("确定清除所有数据吗？")
                .setTitle("提示")
                .setPositiveButton("确认", null)
                .setNegativeButton("取消", null)
                .create().show();
    }

    public void judge() {
        SharedPreferences sp=getSharedPreferences("actm", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        //设置的时间
        int time1 = PApplication.TIME;
        //已经使用的时间
        int time2 = Utils.timeInMillisToMinutes(Utils.getTodaySumMinutes(getApplicationContext()));

        if (time1 <= time2) {
            boolean style1 = sp.getBoolean("style1", true);
            boolean style2 = sp.getBoolean("style2", true);
            if (style1 == true) {
                //send(time2 - time1);
                Utils.sendNotification(this, time2 - time1);
            }
            if (style2 == true) {
                Utils.vibrator(getApplicationContext(), new long[]{1000, 1000, 1000}, -1);
            }
        }

    }
}