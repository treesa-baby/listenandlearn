package com.example.treesamary.testapplication.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.treesamary.testapplication.Connections.HttpRequestHelper;
import com.example.treesamary.testapplication.R;
import com.example.treesamary.testapplication.models.User;
import com.example.treesamary.testapplication.utils.SharedPref_Values;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {
    private EditText username,password;
    private Button login,signup;
    private TextView forpassword;
    private ImageView image;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.et_user);
        password = findViewById(R.id.et_password);
        login = findViewById(R.id.btn_login);
        signup = findViewById(R.id.btn_signup);
        forpassword = findViewById(R.id.tv_frgpassword);
        image = findViewById(R.id.image);


        Picasso.with(Login.this).load(R.drawable.ic_voice)
                    .resize(150, 150)
                .into(image);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
//                String name = "treesa";
//                String pwd = "treesa";
                String u_username = username.getText().toString().trim();
                String u_Password = password.getText().toString().trim();

                user.username = u_username;
                user.password_hash = u_Password;
                login(user);

                if(username.getText().toString().trim().length()==0) {
                    username.setError("Username is not entered");
                    username.requestFocus();
                }
                if(password.getText().toString().trim().length()==0){
                    password.setError("Password is not entered");
                    password.requestFocus();
                }
                //((name).equals(u_username) && (pwd).equals(u_Password))

            }

        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Toast.makeText(Login.this, "Not registered user.Go to signup", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(Login.this, Student_Registration.class));


            }
        });
    }

    private void login(User model) {
        try {

            JSONObject requestJson = new JSONObject();

            requestJson.put("username", model.username);
            requestJson.put("password_hash", model.password_hash);
            requestJson.put("device_token","cccc");
            requestJson.put("device_type", "android");


            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, HttpRequestHelper.App_BaseUrl +
                    HttpRequestHelper.app_Login_URL, requestJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.d(">>>>>>>>>>>>>>>",""+response);
                    try {
                        if (response.getBoolean("status")) {
                            if (response.has("data") && response.get("data") != null) {
                                //for profile
                                User model = new Gson().fromJson(String.valueOf(response.get("data")), User.class);

                                if (model != null && model.id != null) {


                                    new SharedPref_Values().setUserPreference(Login.this, model);
                                    startActivity(new Intent(Login.this, ListenLearn.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                    Login.this.finish();

                                }
//                                else
//                                {
//                                    Toast.makeText(Login.this,"", Toast.LENGTH_LONG).show();
//                                }


                            }


                        }else
                            if (response.has("message")&&response.getString("message")!=null)
                            Toast.makeText(Login.this,response.getString("message"),Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Login.this,"Error..!!",Toast.LENGTH_SHORT).show();

                }


            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", String.format("Basic %s", Base64.encodeToString(String.format("%s:%s",
                            HttpRequestHelper.app_Authentication_Username, HttpRequestHelper.app_Authentication_Password).getBytes(), Base64.DEFAULT)));
                    return params;
                }
            };

            com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(Login.this);

            jor.setShouldCache(false);
            requestQueue.add(jor);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
