package com.iteam.phoneoff.utils;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.widget.Toast;

import com.iteam.phoneoff.Main2Activity;
import com.iteam.phoneoff.PApplication;
import com.iteam.phoneoff.R;
import com.iteam.phoneoff.base.User;

import net.tsz.afinal.FinalDb;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 宇轩 on 2016/3/15.
 */
public class Utils {

    /**
     * 返回今天使用总毫秒
     * @param context
     * @return
     */
    public static long getTodaySumMinutes(Context context) {
        StringBuffer sb = new StringBuffer();
        long sum = 0 ;
        FinalDb db = FinalDb.create(context);
        String SQL = "finalTimeStamp >= ";
        SQL = SQL + Utils.getDayBeginStamp() + ";";
        List<User> list = db.findAllByWhere(User.class, SQL);
        for(User user : list){
            sb.append(user.getFinalTimeStamp()+"\n");
            sum = sum + user.getSum();
        }
//        Toast.makeText(context, "今天一共使用时间（毫秒）" + sum, Toast.LENGTH_SHORT).show();
        return sum;

    }

    /**
     *返回某天凌晨到某天凌晨的使用时间（毫秒）
     * @param context
     * @param num1 开始日期， 例如0 今天， -1昨天 num1<num2
     * @param num2 结束日期， 例如0 今天， -1昨天 num1<num2
     * @return
     */
    public static long getSomedaySumMinutes(Context context, int num1, int num2) {
        long startTimeStamp = Utils.getSomeDayBeginStamp(num1, 0);
        long finalTimeStamp = Utils.getSomeDayBeginStamp(num2, 0);
        StringBuffer sb = new StringBuffer();
        long sum = 0 ;
        FinalDb db = FinalDb.create(context);
        String SQL = "finalTimeStamp between ";
        SQL = SQL + startTimeStamp +" and ";
        SQL = SQL + finalTimeStamp + ";";
        List<User> list = db.findAllByWhere(User.class, SQL);
        for(User user : list){
            sb.append(user.getFinalTimeStamp()+"\n");
            sum = sum + user.getSum();
        }
        return sum;

    }

    /**
     * 返回今天使用总次数
     * @param context
     * @return
     */
    public static int getTodaySumTime(Context context) {
        StringBuffer sb = new StringBuffer();
        int sum = 0 ;
        FinalDb db = FinalDb.create(context);
        String SQL = "finalTimeStamp >= ";
        SQL = SQL + Utils.getDayBeginStamp() + ";";
        List<User> list = db.findAllByWhere(User.class, SQL);
        for(User user : list){
            sb.append(user.getFinalTimeStamp()+"\n");
            sum++;
        }
//        Toast.makeText(context, "今天一共使用次数" + sum, Toast.LENGTH_SHORT).show();
        return sum;

    }

    /**
     * 返回今天凌晨时间戳
     * @return
     */
    public static long getDayBeginStamp() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 001);
        return cal.getTimeInMillis();
    }

    /**
     * 返回某天凌晨时间戳, 0 为今天，-1为昨天
     * @param num
     * @param hour 默认为零
     * @return
     */
    public static long getSomeDayBeginStamp(int num, int hour) {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE) + num;
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 001);

        return cal.getTimeInMillis();
    }

    /**
     * 返回某天的日期， 0 为今天，-1为昨天
     * @param day
     * @return
     */
    public static String getADay(int day) {

        Calendar cal = Calendar.getInstance();
        int _day = cal.get(Calendar.DATE) + day;
        cal.set(Calendar.DATE, _day);

        return cal.get(Calendar.DATE)+"";
    }

    /**
     * 把毫秒转化成分钟
     * @param time
     * @return
     */
    public static int timeInMillisToMinutes(long time) {
        return Integer.parseInt( (time/60000) + "");

    }

    /**
     * 获取当前时间
     * @return
     */
    public static String getNowData() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }

    /**
     * 返回任意两个时间戳之间使用的时间（单位：分钟）
     * @param context
     * @param timeStamp1 timeStamp1 > timeStamp2
     * @param timeStamp2 timeStamp1 > timeStamp2
     * @return  分钟
     */
    public static int getSomeTimeStampSumMinutes(Context context, long timeStamp1, long timeStamp2) {
        StringBuffer sb = new StringBuffer();
        long sum = 0 ;
        FinalDb db = FinalDb.create(context);
        String SQL = "finalTimeStamp between ";
        SQL = SQL + timeStamp1 +" and ";
        SQL = SQL + timeStamp2 + ";";
        List<User> list = db.findAllByWhere(User.class, SQL);
        for(User user : list){
            sb.append(user.getFinalTimeStamp()+"\n");
            sum = sum + user.getSum();
        }
        return Utils.timeInMillisToMinutes(sum);

    }

    //day -1 昨天

    /**
     * 返回某一天的时间分布
     * @param context 上下文
     * @param day  -1代表昨天 -2前天
     * @return  4个时间段
     */
    public static List getYesterdayDistribute(Context context, int day) {
        List list=new ArrayList();
        long a = Utils.getSomeDayBeginStamp(day, 0);
        long b = Utils.getSomeDayBeginStamp(day, 6);
        long c = Utils.getSomeDayBeginStamp(day, 12);
        long d = Utils.getSomeDayBeginStamp(day, 18);
        long e = Utils.getSomeDayBeginStamp(day, 24);
        int _1 = Utils.getSomeTimeStampSumMinutes(context, a, b);
        int _2 = Utils.getSomeTimeStampSumMinutes(context, b, c);
        int _3 = Utils.getSomeTimeStampSumMinutes(context, c, d);
        int _4 = Utils.getSomeTimeStampSumMinutes(context, d, e);
        list.add(_1);
        list.add(_2);
        list.add(_3);
        list.add(_4);
        StringBuffer sb = new StringBuffer();
        sb.append(a+"\n");
        sb.append(b+"\n");
        sb.append(c+"\n");
        sb.append(d+"\n");
        sb.append(e+"\n\n");
        sb.append(_1+"\n");
        sb.append(_2+"\n");
        sb.append(_3+"\n");
        sb.append(_4 + "\n");

       // Toast.makeText(context, new Calendar(), Toast.LENGTH_LONG).show();
        return list;
    }

    /**
     * 频率
     * @param minutes
     * @param times
     * @return
     */
    public static int rate( int minutes, int times) {
        if (times == 0){
            times = 1;
        }
        return minutes/times;
    }

    /**
     * 震动
     * @param context  上下文
     * @param pattern  震动时间
     * @param repeat   重复 -1代表不重复
     */
    public static void vibrator(Context context, long[] pattern, int repeat) {
        Vibrator vibrator=(Vibrator)context.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, repeat);
    }



    /**
     * 使用频率评价
     * @param context
     * @return  返回评价
     */
    public static String rateEvaluation(Context context) {

        StringBuffer sb = new StringBuffer();
        sb.append("您平均每隔");
        int rate = Utils.rate(Utils.timeInMillisToMinutes(new Date().getTime() - Utils.getDayBeginStamp()),Utils.getTodaySumTime(context));
        sb.append(rate);
        sb.append("分钟查看一次智能设备。");
        if(rate>20)
        {
            sb.append("您能很好的控制使用智能设备，继续加油！");
        }
        else
        {
            sb.append("您对它有严重的依赖症，快点去求助身边帮助你的人吧！");
        }
        return sb.toString();
    }

    /**
     * 发送超时提醒通知
     * @param context
     * @param minutes 超时时间
     */
    public static void sendNotification(Context context, int minutes) {
        Resources res = context.getResources();
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        Intent intent = new Intent(context, Main2Activity.class);
        PendingIntent contentIndent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder .setContentIntent(contentIndent)
                .setSmallIcon(R.drawable.logo)//设置状态栏里面的图标（小图标） 　　　　　　　　　　　　　　　　　　　　
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.logo))//下拉下拉列表里面的图标（大图标） 　　　　　　　
                .setTicker("超时啦！！！") //设置状态栏的显示的信息
                .setWhen(System.currentTimeMillis())//设置时间发生时间
                .setAutoCancel(true)//设置可以清除
                .setContentTitle("超时啦！！！")//设置下拉列表里的标题
                .setContentText("你已经超时" + minutes + "分钟");//设置上下文内容
        Notification notification = builder.build();//获取一个Notification
        notificationManager.notify(0, notification);
    }

    /**
     * 打分情况 利用频率，分数，目标综合打分，满分100分
     * @param context
     * @return
     */
    public static int getScore(Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        int score =  0;
        int times = Utils.getTodaySumTime(context);
        if (times < 50) {
            times = 50;
        }
        if (times > 50 && times <100) {
            times = 100 - times;
        }
        if (times >= 100){
            times = 0;
        }
        score = score + times;
        stringBuffer.append("\ntimes" +score);
        //设置的时间
        double time1 = (double)PApplication.TIME;
        stringBuffer.append("\ntime1" +time1);
        //已经使用的时间
        double time2 = (double)Utils.timeInMillisToMinutes(Utils.getTodaySumMinutes(context));
        stringBuffer.append("\ntime2" +time2);
        if(time1 > time2){
            double a= ((time1-time2)/time1)*50;
            int b = (int)a;
            stringBuffer.append("\ntime1/time2" + b);
            score = score + b;
        }
        stringBuffer.append("\nscore" + score);
//        Toast.makeText(context, stringBuffer.toString(), Toast.LENGTH_LONG).show();
        return score;
    }

    /**
     * 分数评价
     * @param context
     * @return 评价字符串
     */
    public static String scoreEvaluation(Context context) {

        String str = "";
        int score = Utils.getScore(context);
        if(score > 80){
            str = "还不错，继续保持哦";
        }else if(score >60){
            str = "不要气馁，加油哦！你可以做得更好！！";
        }else {
            str = "(╰_╯)# 怎么会这样，快快反思一下吧";
        }
        return  str;
    }

}
