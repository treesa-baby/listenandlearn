package com.example.treesamary.testapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.treesamary.testapplication.Fragments.Home;
import com.example.treesamary.testapplication.Fragments.Logout;
import com.example.treesamary.testapplication.Fragments.Notification;
import com.example.treesamary.testapplication.Fragments.Profile;
import com.example.treesamary.testapplication.R;
import com.example.treesamary.testapplication.Fragments.VoiceNote;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class ListenLearn extends AppCompatActivity {


   // private TextView mTextMessage;
    MaterialSearchView materialSearchView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Home()).commit();
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_voice:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new VoiceNote()).commit();
                   // mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Notification()).commit();
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Profile()).commit();
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_learn);

        initView();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ic_listen_learn);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);



        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initView() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ic_listen_learn);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Home()).commit();

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main_action,menu);



//        MenuItem item = menu.findItem(R.id.search);
//        searchView.setMenuItem(item);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.settings)
        {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Listen & Learn");
        builder.setMessage("Are you sure you want to Logout?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {


                SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(ListenLearn.this, Login.class));

                Toast.makeText(getApplicationContext(), "Ok was Clicked", Toast.LENGTH_SHORT).show();
                finish();
            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                Toast.makeText(getApplicationContext(), "Cancel was Clicked", Toast.LENGTH_SHORT).show();

            }
        });
        builder.create().show();


    }




//        switch (item.getItemId()){
//            case R.id.search:
//                //toast or other
////                Toast.makeText(getApplicationContext(),"settings", Toast.LENGTH_LONG).show();
//                return true;
//
//            case R.id.settings:
//                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Logout()).commit();
//
//        }
        return super.onOptionsItemSelected(item);
    }
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.navi__drawer, menu);
//        return true;
//    }


}
