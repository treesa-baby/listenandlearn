package com.example.treesamary.testapplication.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Student_Registration extends AppCompatActivity {

     EditText fname,lname,email,mobile,dob,username,password,repassword;
     RadioGroup gender;
     RadioButton male,female;
     ImageView SelectImage;
     Button image,signup;
    private Bitmap bitmap;

    final Calendar myCalendar = Calendar.getInstance();
    int Image_Request_Code = 7;
    User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__registration);
        fname = findViewById(R.id.et_fname);
        lname = findViewById(R.id.et_lname);
        email = findViewById(R.id.et_email);
        mobile = findViewById(R.id.et_mobile);
        dob = findViewById(R.id.et_dob);
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        repassword = findViewById(R.id.et_repassword);
        gender = findViewById(R.id.rg_gender);
        male = findViewById(R.id.rb_male);
        female = findViewById(R.id.rb_female);
        SelectImage = findViewById(R.id.ShowImageView);
        image = findViewById(R.id.ButtonChooseImage);
        signup = findViewById(R.id.btn_signup);
        user = new User();

        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b )
                    user.user_gender = "male";


            }
        });
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    user.user_gender = "female";
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getvalue();


                registration(user);


            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);


            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Student_Registration.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));

    }

    private void getvalue() {



        String u_fname = fname.getText().toString().trim();
        user.user_fname = u_fname;

        String u_lname = lname.getText().toString().trim();
        user.user_lname = u_lname;

        String u_email = email.getText().toString().trim();
        user.email = u_email;

        String u_mobile = mobile.getText().toString().trim();
        user.user_mob = u_mobile;

        String u_dob = dob.getText().toString().trim();
        user.user_dob = u_dob;

        String u_username = username.getText().toString().trim();
        user.username = u_username;

        String u_password = password.getText().toString().trim();
        user.password_hash = u_password;




        String u_repassword = repassword.getText().toString().trim();

    }

    private void registration(User model) {



        try {

            JSONObject requestJson = new JSONObject();

            requestJson.put("user_fname", model.user_fname);
            requestJson.put("user_lname", model.user_lname);
            requestJson.put("user_mob", model.user_mob);
            requestJson.put("user_gender", model.user_gender);
            requestJson.put("user_dob", model.user_dob);
            requestJson.put("email", model.email);
           // requestJson.put("profile_image", "image");
            requestJson.put("profile_image", model.profile_image);
            requestJson.put("username", model.username);
            requestJson.put("password_hash", model.password_hash);
            requestJson.put("device_token","11");
            requestJson.put("device_type", "android");


            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, HttpRequestHelper.App_BaseUrl +
                    HttpRequestHelper.app_User_Reg_URL, requestJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.d(">>>>>>>>>>>>>>>",""+response);
                    try {
                        if (response.getBoolean("status")) {
                            if (response.has("data") && response.get("data") != null) {
                                //for profile
                                User model = new Gson().fromJson(String.valueOf(response.get("data")), User.class);

//                                if (model != null && model.id != null) {
//
//
//                                    new SharedPref_Values().setUserPreference(Student_Registration.this, model);
//                                    startActivity(new Intent(Student_Registration.this, ListenLearn.class)
//                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
//                                    Student_Registration.this.finish();
//
//                                }
//                                else
//                                {
                                    Toast.makeText(Student_Registration.this,"successss", Toast.LENGTH_LONG).show();
//                                }


                            }


                        }else
                        if (response.has("message")&&response.getString("message")!=null)
                            Toast.makeText(Student_Registration.this,response.getString("message"),Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Student_Registration.this,"Error..!!",Toast.LENGTH_SHORT).show();

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

            com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(Student_Registration.this);

            jor.setShouldCache(false);
            requestQueue.add(jor);

        } catch (JSONException e) {
            e.printStackTrace();
        }




    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                Picasso.with(Student_Registration.this).load(filePath.toString()).
                        error(R.drawable.ic_voice)
                        .resize(300, 300)
                        .into(SelectImage);
                user.profile_image = getStringImage(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
