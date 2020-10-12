package com.coccoc.helloworld2;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Locale;

public class LanguageUtils {

    public static void changeLanguage(Context context, Language language) {
        Resources resources = context.getResources();
        Locale locale = new Locale(language.getCode());
//        Locale.setDefault(locale);
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
//        return App.self().getApplicationContext().createConfigurationContext(configuration);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public static <T> T parseJson(String value, Class<T> classOfT) {
        T data = null;
//        Gson gson = new Gson();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(classOfT, new AnnotatedDeserializer<T>())
                .create();
        try {
            data = gson.fromJson(value, classOfT);
        } catch (Exception e) {
            Log.d("namanh11611", "Exception " + e.getMessage());
        } finally {
            return data;
        }
    }
}
