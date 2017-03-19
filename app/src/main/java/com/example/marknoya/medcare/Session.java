package com.example.marknoya.medcare;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by shadow on 3/16/2017.
 */

public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;

    public Session(Context context){
        this.context = context;
        prefs = context.getSharedPreferences("MedCare", Context.MODE_PRIVATE);
        editor = prefs.edit();

    }

    public void setLoggin(boolean loggedin) {
        editor.putBoolean("loggedInmode",loggedin);
        editor.commit();
    }

    public boolean loggedin() {
        return prefs.getBoolean("loggedInmode", false);
    }


}
