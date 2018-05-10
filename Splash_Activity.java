package com.example.treesamary.testapplication.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.treesamary.testapplication.R;
import com.example.treesamary.testapplication.utils.SharedPref_Values;

public class Splash_Activity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final  boolean islogin = getSharedPreferences(SharedPref_Values.PREFERNCE_NAME,MODE_PRIVATE).
                getBoolean(SharedPref_Values.IS_USER_LOGGEDIN,false);
        new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                if (islogin) {
                    startActivity(new Intent(Splash_Activity.this, ListenLearn.class));
                } else {
                    startActivity(new Intent(Splash_Activity.this, Login.class));
                }
                finish();
            }
        }, 5000);








    }
}
