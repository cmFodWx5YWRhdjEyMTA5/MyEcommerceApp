package com.example.helvi.myecommercedemo.activities.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.helvi.myecommercedemo.R;



/**
 * This is a Login Activity.
 * In which user enter Mobile and password.
 * This screen also contains Forget password and Join Now.
 *@author Helvi
 */

public class HomeActivity extends AppCompatActivity
{
    Button btn_login,btn_signUpFacebook;
    TextView txt_forgetPassword,txt_join;
    EditText edit_mobile,edit_password;

    String input_mobile,input_password;
    SharedPreferences spref;

    public static final String TAG = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        spref=getSharedPreferences("file1", Context.MODE_PRIVATE);
        btn_login = (Button) findViewById(R.id.buttonLoginRegi);
        btn_signUpFacebook = (Button) findViewById(R.id.buttonSignUpRegi);

        edit_mobile = (EditText) findViewById(R.id.editTextMobile);
        edit_password = (EditText) findViewById(R.id.editTextPassword);

        txt_forgetPassword = (TextView) findViewById(R.id.textViewForget);
        txt_join = (TextView) findViewById(R.id.textViewJoin);
        txt_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(HomeActivity.this, RegistrationActivity.class);
                startActivity(signUpIntent);

            }
        });

        txt_forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent CategoryIntent = new Intent(HomeActivity.this, ForgetActivity.class);
                startActivity(CategoryIntent);
            }
        });

        // Tag used to cancel the request

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_mobile = edit_mobile.getText().toString().trim();
                input_password = edit_password.getText().toString().trim();

                SharedPreferences.Editor editpref=spref.edit();
                editpref.putString("mobile",input_mobile);
                editpref.commit();

                if(input_password.equals("") ||input_mobile.equals(""))
                {
                    Toast.makeText(HomeActivity.this, "Please Enter Empty Fields", Toast.LENGTH_LONG).show();

                }
                else if(!(input_password.isEmpty()) && (!input_mobile.isEmpty()))
                 {
                     if (input_mobile.length() == 10)
                     {
                         if (input_password.length() >= 6)
                         {
                             loginData();
                         }
                         else
                         {
                             Toast.makeText(HomeActivity.this, "Password Should be 6 or more Characters", Toast.LENGTH_LONG).show();
                         }

                     } else {
                         Toast.makeText(HomeActivity.this, "Mobile Should be 10 digit Number", Toast.LENGTH_LONG).show();
                     }
                 }
                 else
                 {
                     Toast.makeText(HomeActivity.this, "Please Enter Empty Fields", Toast.LENGTH_LONG).show();
                 }



            }
        });
    }

        void loginData()
        {

           String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/shop_login.php?"+"mobile="+ input_mobile+
                   "&password="+input_password;



            StringRequest strReq = new StringRequest(Request.Method.GET,
                    url, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    try
                    {
                        if(response.contains("success"))
                        {
                            Intent intentLogin=new Intent(HomeActivity.this,ShopActivity.class);
                            startActivity(intentLogin);
                        }
                        else if(response.contains("Mobile Number not register"))
                        {
                            Toast.makeText(HomeActivity.this,"Mobile Number not register",Toast.LENGTH_LONG).show();
                        }
                        else if(response.contains("incorrect password"))
                        {
                            Toast.makeText(HomeActivity.this,"incorrect password",Toast.LENGTH_LONG).show();
                        }

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }
            );

                        Volley.newRequestQueue(this).add(strReq);

        }
}
