package com.example.instagram;

import android.app.Application;
import com.parse.Parse;

public class ParseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        final Parse.Configuration config = new Parse.Configuration.Builder(this)
                .applicationId("fbu-instagram")
                .clientKey("key-instagram")
                .server("http://miguel0-fbu-instagram.herokuapp.com/parse")
                .build();
        Parse.initialize(config);
    }
}
