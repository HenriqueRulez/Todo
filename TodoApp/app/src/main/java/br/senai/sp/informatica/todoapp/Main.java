package br.senai.sp.informatica.todoapp;

import android.app.Application;
import android.content.Context;


public class Main extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }
}
