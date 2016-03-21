package com.iteam.phoneoff;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.iteam.phoneoff.base.Setting;

import net.tsz.afinal.FinalDb;

import java.util.List;

/**
 * Created by 宇轩 on 2016/3/17.
 */
public class PApplication extends Application {
    public static Context context;
    public static int TIME = 500;
    @Override
    public void onCreate() {
        super.onCreate();
        FinalDb db = FinalDb.create(this);
        //查找数据库中的个人设置
        List<Setting> list = db.findAll(Setting.class, "id");
//        StringBuilder _sb = new StringBuilder();
//        for(int i=0;i<list.size();i++) {
//            _sb.append("   id:" + list.get(i).getId());
//        }
        if(list.size() != 0) {
            TIME = list.get(list.size()-1).getGoal();
        }

    }


}
