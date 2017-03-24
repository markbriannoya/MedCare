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

    public void setLoggin(boolean loggedin,String id) {
        editor.putBoolean("loggedInmode",loggedin);
        editor.putString("Account",id);
        editor.commit();

    }


    public boolean loggedin() {
        return prefs.getBoolean("loggedInmode", false);
    }

    public String getUserid() {
        return prefs.getString("Account",null);
    }



}
