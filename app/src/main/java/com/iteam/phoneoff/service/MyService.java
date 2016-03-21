package com.iteam.phoneoff.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import com.iteam.phoneoff.Main2Activity;
import com.iteam.phoneoff.MainActivity;
import com.iteam.phoneoff.PApplication;
import com.iteam.phoneoff.R;
import com.iteam.phoneoff.base.User;
import com.iteam.phoneoff.utils.Utils;

import net.tsz.afinal.FinalDb;

import java.util.Date;

/**
 * 主要用于监听屏幕点亮及屏幕关闭事件，并进行存储
 */
public class MyService extends Service {

    User user = new User();

    private final BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            SharedPreferences sp=getSharedPreferences("actm", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();

            FinalDb db = FinalDb.create(getApplication());
            sendNotification();
            //屏幕点亮
            if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {

                //保存屏幕启动时的毫秒数
                editor.putLong("lasttime", new Date().getTime());
                editor.commit();

                user.setStartTime(Utils.getNowData());
                user.setStartTimeStamp((new Date().getTime()));

                //设置的时间
                int time1 = PApplication.TIME;
                //已经使用的时间
                int time2 = Utils.timeInMillisToMinutes(Utils.getTodaySumMinutes(getApplicationContext()));

                if(time1<=time2){
                    //读取提醒方式
                    boolean style1 = sp.getBoolean("style1",true);  //是否发送通知
                    boolean style2 = sp.getBoolean("style2",true);  //是否震动
                    if(style1==true)
                    {
                        Utils.sendNotification(MyService.this, time2 - time1);
                    }
                    if(style2==true)
                    {
                       Utils.vibrator(getApplicationContext(), new long[]{1000,1000,1000}, -1);
                    }


                }





            }
            else if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                //保存屏幕总工作时间
                long lasttime=sp.getLong("lasttime", new Date().getTime());
                long sum=new Date().getTime()-lasttime;
                user.setFinalTimeStamp(new Date().getTime());
                user.setSum(sum);
                user.setFinalTime(Utils.getNowData());
                db.save(user);


            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        sendNotification();
        //添加过滤器并注册
        final IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(receiver, filter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //把后台服务转化成前台服务
    private void sendNotification() {
        int times = Utils.getTodaySumTime(this);
        int minutes = Utils.timeInMillisToMinutes(Utils.getTodaySumMinutes(this));
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("今天使用手机");
        stringBuffer.append(times + "次\n");
        stringBuffer.append("共使用手机");
        stringBuffer.append(minutes + "分钟\n");
        Resources res = getResources();
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
        PendingIntent contentIndent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder .setContentIntent(contentIndent)
                .setSmallIcon(R.drawable.logo)//设置状态栏里面的图标（小图标） 　　　　　　　　　　　　　　　　　　　　
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.logo))//下拉下拉列表里面的图标（大图标） 　　　　　　　
                .setTicker("正在运行中") //设置状态栏的显示的信息
                .setWhen(System.currentTimeMillis())//设置时间发生时间
                .setAutoCancel(true)//设置可以清除
                .setContentTitle("PhoneOff")//设置下拉列表里的标题
                .setContentText(stringBuffer.toString());//设置上下文内容
        Notification notification = builder.build();//获取一个Notification
        startForeground(1, notification);

    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }



}
