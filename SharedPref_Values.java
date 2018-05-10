package com.example.treesamary.testapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.treesamary.testapplication.models.User;
import com.google.gson.Gson;

/**
 * Created by Treesa Mary on 30-Apr-18.
 */

public class SharedPref_Values {
    public static String PREFERNCE_NAME="user_login";
    public static String IS_USER_LOGGEDIN = "is_user_login";
    public static String USER_ID = "user_id";
    public static String USER_COMPLETE_DATA = "user_complete_data";

    public void setUserPreference(Context context, User model) {

        SharedPreferences preferences= context.getSharedPreferences(PREFERNCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(IS_USER_LOGGEDIN,true);
        editor.putString(USER_ID,model.id);
        editor.putString(USER_COMPLETE_DATA,new Gson().toJson(model));
        editor.commit();



    }




}
