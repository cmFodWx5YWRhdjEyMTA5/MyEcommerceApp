package com.example.helvi.myecommercedemo.activities.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.helvi.myecommercedemo.R;
import com.example.helvi.myecommercedemo.activities.net.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
* Forget class to set
* new password for the user
*
* */
public class ForgetActivity extends AppCompatActivity {

   private Button btn_cancel_forget;
    private Button btn_phone_forget;
    private EditText edit_phone_forget;
    private String input_mobile_forget;
    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        btn_cancel_forget = (Button) findViewById(R.id.buttonCancel);
        btn_phone_forget = (Button) findViewById(R.id.buttonForgetPswd);
        edit_phone_forget = (EditText) findViewById(R.id.editTextPhoneForget);

        btn_cancel_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelIntent = new Intent(ForgetActivity.this, HomeActivity.class);
                startActivity(cancelIntent);


            }
        });

        btn_phone_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_mobile_forget = edit_phone_forget.getText().toString().trim();

                if((!input_mobile_forget.isEmpty())&&(input_mobile_forget.length()==10)) {
                    String tag_json_arry = "json_array_req";

                    String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/shop_fogot_pass.php?" + "&mobile=" + input_mobile_forget;


                    JsonArrayRequest req = new JsonArrayRequest(url,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
                                        for (int i = 0; i < response.length(); i++) {
                                            JSONObject jsonObject = response.getJSONObject(i);
                                            String msg = jsonObject.getString("msg");
                                            String userPassword = jsonObject.getString("UserPassword");
                                            String userMobile = jsonObject.getString("UserMobile");

                                           Toast.makeText(ForgetActivity.this, "Password has been sent to your email", Toast.LENGTH_LONG).show();

                                            Intent intentForget = new Intent(ForgetActivity.this, ResetActivity.class);
                                            intentForget.putExtra("userMobile", userMobile);
                                            startActivity(intentForget);

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

                    AppController.getInstance().addToRequestQueue(req, tag_json_arry);

                }
                else
                {
                    Toast.makeText(ForgetActivity.this, "Please Enter Valid Mobile of 10 digit", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
