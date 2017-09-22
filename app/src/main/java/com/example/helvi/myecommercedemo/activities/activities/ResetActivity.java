package com.example.helvi.myecommercedemo.activities.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.helvi.myecommercedemo.R;
import com.example.helvi.myecommercedemo.activities.net.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This activity is Reset Password
 * Allow user to reset new password
 *
 */
public class ResetActivity extends AppCompatActivity {

    private static final String TAG = "";
    private Button btn_changepassword_reset,btn_cancel_reset;
    private EditText edit_phone_reset,edit_password_reset,edit_confirm_reset;
    private String res,input_phone_reset,input_password_reset,input_confirm_reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        btn_changepassword_reset= (Button) findViewById(R.id.buttonChangePswd);
        btn_cancel_reset= (Button) findViewById(R.id.buttonCancel);
        edit_phone_reset= (EditText) findViewById(R.id.editTextPhoneReset);
        edit_password_reset= (EditText) findViewById(R.id.editTextPasswordReset);
        edit_confirm_reset= (EditText) findViewById(R.id.editTextConfirmPasswrod);

        Intent intent1=getIntent();
        String fix_mobile=intent1.getStringExtra("userMobile");
        input_phone_reset=fix_mobile.trim();

        edit_phone_reset.setText(fix_mobile);

        btn_changepassword_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                input_password_reset=edit_password_reset.getText().toString().trim();
                input_confirm_reset=edit_confirm_reset.getText().toString().trim();

                if(!(input_password_reset.equals(""))&& !(input_confirm_reset.equals(""))&&(input_password_reset.length()>=6)&&(input_confirm_reset.length()>=6))
                {
                    String tag_string_req = "string_req";
                    String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/shop_reset_pass.php?" +
                            "&mobile=" + input_phone_reset + "&password=" + input_password_reset + "&newpassword=" + input_confirm_reset;

                    final JsonObjectRequest jsonRequest = new JsonObjectRequest
                            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONArray arrJSON = response.getJSONArray("msg");
                                        String result = arrJSON.getString(0);


                                        if (result.contains("password reset successfully")) {
                                            Toast.makeText(ResetActivity.this, "password reset successfully", Toast.LENGTH_LONG).show();
                                            Intent intentHome = new Intent(ResetActivity.this, HomeActivity.class);
                                            startActivity(intentHome);
                                        } else if (res.contains("wrong mobile number")) {
                                            Toast.makeText(ResetActivity.this, "wrong mobile number", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    VolleyLog.d(TAG, "Error: " + error.getMessage());

                                }
                            });

                    AppController.getInstance().addToRequestQueue(jsonRequest, tag_string_req);
                }
                else
                {
                    Toast.makeText(ResetActivity.this, "Please Enter Valid Password of 6 or more Characters", Toast.LENGTH_LONG).show();
                }

            }
        });

        btn_cancel_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intentHome = new Intent(ResetActivity.this, HomeActivity.class);
                startActivity(intentHome);

            }
        });


    }
}
