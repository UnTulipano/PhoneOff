package com.iteam.phoneoff.base;

import java.util.Date;

/**
 * Created by 宇轩 on 2016/3/15.
 */

/**
 * 用户使用手机情况
 */
public class User {
    private int id;
    private long sum;
    private long startTimeStamp;
    private long finalTimeStamp;
    private String startTime;
    private String finalTime;


    /////////////getter and setter 不能省略哦///////////////
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public long getFinalTimeStamp() {
        return finalTimeStamp;
    }

    public void setFinalTimeStamp(long finalTimeStamp) {
        this.finalTimeStamp = finalTimeStamp;
    }

    public long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(String finalTime) {
        this.finalTime = finalTime;
    }
}
