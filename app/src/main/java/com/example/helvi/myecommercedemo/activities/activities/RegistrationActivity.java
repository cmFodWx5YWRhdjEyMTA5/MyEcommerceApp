package com.example.helvi.myecommercedemo.activities.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.helvi.myecommercedemo.R;
import com.example.helvi.myecommercedemo.activities.net.AppController;

public class RegistrationActivity extends AppCompatActivity
{
    /**
     * This activity is Registration
     * User Register Mobile,Email,Password fields
     *
     */


    private EditText edit_name_regi,edit_email_regi,edit_mobile_regi,edit_password_regi;
    private Button btn_signup_regi,btn_login_regi;
    private CheckBox check_agree_regi;

    private String input_name,input_email,input_mobile,input_password;

    public static final String TAG = "";
    private String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edit_name_regi= (EditText) findViewById(R.id.editTextName);
        edit_email_regi= (EditText) findViewById(R.id.editTextEmail);
        edit_mobile_regi= (EditText) findViewById(R.id.editTextMobile);
        edit_password_regi= (EditText) findViewById(R.id.editTextPassword);

        btn_login_regi= (Button) findViewById(R.id.buttonLoginRegi);
        btn_signup_regi= (Button) findViewById(R.id.buttonSignUpRegi);


btn_login_regi.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view)
    {
        Intent intentHome = new Intent(RegistrationActivity.this, HomeActivity.class);
        startActivity(intentHome);

    }
});

        btn_signup_regi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                input_name=edit_name_regi.getText().toString().trim();
                input_email=edit_email_regi.getText().toString().trim();
                input_mobile=edit_mobile_regi.getText().toString().trim();
                input_password=edit_password_regi.getText().toString().trim();

                String url="http://rjtmobile.com/ansari/shopingcart/androidapp/shop_reg.php?"+"name="+input_name+"&email="+input_email+"&mobile="+input_mobile+"&password="+input_password;

               if(!(input_name.equals(""))&& !(input_email.equals(""))&&!(input_mobile.equals(""))&&!(input_password.equals("")))
               {
                if(input_mobile.length() == 10)
                {
                    if(input_password.length()>=6)
                    {
                        if (input_email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))
                        {
                            registerData(url);

                        }
                        else
                        {
                            Toast.makeText(RegistrationActivity.this, "Please Enter Valid Email", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(RegistrationActivity.this, "Password Should be 6 or more Characters", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(RegistrationActivity.this, "Mobile Should be 10 digit Number", Toast.LENGTH_LONG).show();
                }

            }

               else if(input_name.equals("")|| input_email.equals("")||input_mobile.equals("")||input_password.equals(""))
                {
                    Toast.makeText(RegistrationActivity.this,"Please Enter Empty Fields",Toast.LENGTH_LONG).show();

                }


            }
        });

      }

      void registerData(String url)
      {
          String  tag_string_req = "string_req";

          StringRequest strReq = new StringRequest(Request.Method.GET,
                  url, new Response.Listener<String>() {

              @Override
              public void onResponse(String response)
              {
                  res=response;
                  Log.d(TAG, "Helvi:"+response);
                  Toast.makeText(RegistrationActivity.this,""+res,Toast.LENGTH_LONG).show();
                  if(res.contains("successfully registered"))
                  {
                      Intent intentHome = new Intent(RegistrationActivity.this, HomeActivity.class);
                      startActivity(intentHome);
                  }
                  else
                  {
                      Toast.makeText(RegistrationActivity.this,""+res,Toast.LENGTH_LONG).show();
                  }


              }
          }, new Response.ErrorListener() {

              @Override
              public void onErrorResponse(VolleyError error) {
                  VolleyLog.d(TAG, "Error: " + error.getMessage());

              }
          });

          AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

      }
}
