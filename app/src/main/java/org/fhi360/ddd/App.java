package org.fhi360.ddd;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static void setLocaleFa(Context context) {
        List<String> language = new ArrayList<>();
        for (String id : language) {
            Locale locale = new Locale(id);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            context.getApplicationContext().getResources().updateConfiguration(config, null);
        }

    }

    public static void setLocaleEn(Context context) {
        Locale locale = new Locale("pt");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(config, null);
    }

}