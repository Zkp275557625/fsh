package com.zkp.fsh;

import android.app.Application;

import com.coder.zzq.smartshow.core.SmartShow;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * @author: zkp
 * @project: fsh
 * @package: com.zkp.fsh
 * @time: 2019/3/28 16:29
 * @description:
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化Logger
        Logger.addLogAdapter(new AndroidLogAdapter());

        //初始化SmartShow
        SmartShow.init(this);
    }
}
