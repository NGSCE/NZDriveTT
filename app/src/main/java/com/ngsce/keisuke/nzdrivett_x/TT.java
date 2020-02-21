package com.ngsce.keisuke.nzdrivett_x;

import android.app.Application;
import android.content.Context;

public class TT extends Application {
    //Context

    private static Context context;

    public void onCreate(){
        super.onCreate();
        TT.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return TT.context;
    }
}