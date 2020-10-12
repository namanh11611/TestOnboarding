package com.coccoc.helloworld2;

import android.app.Application;

public class App extends Application {

    private static App sInstance;

    public static void init(Application app) {
        if (sInstance == null) {
            sInstance = (App) app;
        }
    }

    public static App self() {
        return sInstance;
    }
}
