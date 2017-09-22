package com.example.helvi.myecommercedemo.activities.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.helvi.myecommercedemo.R;
import com.example.helvi.myecommercedemo.activities.adapters.CartAdapter;
import com.example.helvi.myecommercedemo.activities.model.Cart;
import com.example.helvi.myecommercedemo.activities.net.AppController;
import com.example.helvi.myecommercedemo.activities.util.MyDB;
import com.example.helvi.myecommercedemo.activities.util.Constants;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;
import java.util.ArrayList;



public class CartActivity extends AppCompatActivity
{
    private static final String TAG ="" ;
    MyDB myHelper;
    SQLiteDatabase db;
    ArrayList<Cart> items = new ArrayList<>();
    ArrayList<String> data = new ArrayList<>();

    Cursor cursor;
    int total=0;
    String mobileShed;
    Button btn_add,btn_sub,btn_checkout;
    EditText edit_quantity;
    String res;


    //Paypal
    public static final int PAYPAL_REQUEST_CODE = 123;
    private static PayPalConfiguration payPalConfiguration = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Constants.PAYPAL_CLIENT_ID);
    String mPaymentAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        myHelper=new MyDB(this);
        db=myHelper.getReadableDatabase();

        SharedPreferences prefs = getSharedPreferences("file1", MODE_PRIVATE);
        mobileShed=prefs.getString("mobile",null);
        Log.d("Mobile",mobileShed);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_cart);
        btn_checkout= (Button) findViewById(R.id.buttonCheckout);

        recyclerView.setHasFixedSize(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        cursor=db.rawQuery("SELECT * FROM " + myHelper.TABLE,null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {


            do {
                String ID = cursor.getString(cursor.getColumnIndex(myHelper.PID));
                String PNAME = cursor.getString(cursor.getColumnIndex(myHelper.PNAME));
                String PQUANTITY = cursor.getString(cursor.getColumnIndex(myHelper.PQUANTITY));
                String PRIZE = cursor.getString(cursor.getColumnIndex(myHelper.PRIZE));
                String IMAGE = cursor.getString(cursor.getColumnIndex(myHelper.IMAGE));

                //total+=Integer.parseInt(PRIZE);

                Cart cty = new Cart(ID, PNAME, PQUANTITY, PRIZE, IMAGE);
                items.add(cty);


            } while (cursor.moveToNext());


            if (items.size() > 0) {
                CartAdapter adapter = new CartAdapter(this, items);
                recyclerView.setAdapter(adapter);

            } else {
                ArrayList<Cart> items = new ArrayList<>();
                CartAdapter adapter = new CartAdapter(this, items);
                recyclerView.setAdapter(adapter);
            }

        }

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                placeOrder();
                Intent intent = new Intent(CartActivity.this, PayPalService.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
                startService(intent);

                getPayment();

            }
        });


      }

      public void placeOrder()
      {


          cursor=db.rawQuery("SELECT * FROM " + myHelper.TABLE,null);
          cursor.moveToFirst();

          do
          {
              String ID= cursor.getString(cursor.getColumnIndex(myHelper.PID));
              String PNAME=cursor.getString(cursor.getColumnIndex(myHelper.PNAME));
              String PQUANTITY=cursor.getString(cursor.getColumnIndex(myHelper.PQUANTITY));
              String PRIZE=cursor.getString(cursor.getColumnIndex(myHelper.PRIZE));
              //String IMAGE=cursor.getString(cursor.getColumnIndex(myHelper.IMAGE));

              total+=Integer.parseInt(PRIZE);

              String url="http://rjtmobile.com/ansari/shopingcart/androidapp/orders.php?"+"&item_id="+ID+"&item_names="+PNAME+"&item_quantity="+PQUANTITY+"&final_price="+PRIZE+"&mobile="+mobileShed;

              String  tag_string_req = "string_req";

              StringRequest strReq = new StringRequest(Request.Method.GET,
                      url, new Response.Listener<String>() {

                  @Override
                  public void onResponse(String response)
                  {
                      res=response;
                      Log.i("Order:",res+"");
                      if(res.contains("Order Confirmed"))
                      {

                          Toast.makeText(CartActivity.this,"Order successfully Placed",Toast.LENGTH_LONG).show();
                      }

                  }
              }, new Response.ErrorListener() {

                  @Override
                  public void onErrorResponse(VolleyError error) {
                      VolleyLog.d(TAG, "Error: " + error.getMessage());

                  }
              });

              AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

          } while(cursor.moveToNext());

      }


    public void getPayment()
    {
        mPaymentAmount = Integer.toString(total);
        Log.i("HELVI",mPaymentAmount);
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(mPaymentAmount),"USD","payment",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent paymentIntent = new Intent(CartActivity.this, PaymentActivity.class);
        paymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
        paymentIntent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(paymentIntent, PAYPAL_REQUEST_CODE);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (paymentConfirmation != null) {
                    try {
                        String paymentDetails = paymentConfirmation.toJSONObject().toString();
                        Toast.makeText(this, "Payment Details: " + paymentDetails, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "JSONException", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.cart_payment, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout)
        {
            Intent logoutIntent=new Intent(CartActivity.this,HomeActivity.class);
            startActivity(logoutIntent);
            return true;
        }
        if (id == R.id.action_payment)
        {
            placeOrder();
            Intent intent = new Intent(CartActivity.this, PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
            startService(intent);

            getPayment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

