package com.iteam.phoneoff.base;

/**
 * Created by 宇轩 on 2016/3/17.
 */

/**
 * 个人设置
 */
public class Setting {
    private int id;
    private int goal;
    private String date;

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
