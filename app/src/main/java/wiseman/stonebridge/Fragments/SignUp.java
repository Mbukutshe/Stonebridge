package wiseman.stonebridge.Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import wiseman.stonebridge.Globals.Device;
import wiseman.stonebridge.Globals.Globals;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-01-28.
 */

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    ScrollView subscribe_layout;
    String price;
    ProgressDialog myProgressDialog,progress;
    RequestQueue requestQueue;
    EditText email_address,full_name,cell_no,car_maker;
    String email,name,cell,make;
    LinearLayout subscribe;
    Spinner spin;
    public static  int COUNT_DOWN=1000;
    CountDownTimer countDownTimer;
    AppCompatImageView mImgCheck;
    AnimatedVectorDrawableCompat avd;
    Drawable drawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscribe_fragment);

        email_address = (EditText)findViewById(R.id.email);
        full_name = (EditText)findViewById(R.id.name);
        cell_no = (EditText)findViewById(R.id.number);
        car_maker = (EditText)findViewById(R.id.maker);
        subscribe = (LinearLayout) findViewById(R.id.subscribe_button);
        subscribe.setOnClickListener(this);

        subscribe_layout =(ScrollView) findViewById(R.id.subscribe_layout);
        subscribe_layout.getBackground().setAlpha(180);
        spin = (Spinner)findViewById(R.id.vehicle_price);
        ArrayAdapter<CharSequence> adapt = ArrayAdapter.createFromResource(getApplicationContext(),R.array.prices,R.layout.dropdown_items);
        adapt.setDropDownViewResource(R.layout.dropdown_items);
        spin.setAdapter(adapt);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                price = i+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                price = 0+"";
            }
        });

        myProgressDialog = new ProgressDialog(this);
        progress = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.subscribe_button:
                email = email_address.getText().toString();name = full_name.getText().toString();cell = cell_no.getText().toString();;
               if(!(email.isEmpty()||name.isEmpty()||cell.isEmpty()))
               {
                   upload(email,name,cell,make,price);
                   email_address.setText("");full_name.setText(""); cell_no.setText("");car_maker.setText("");
                   spin.setSelection(0);
               }
               else
               {
                   Toast.makeText(getApplicationContext(),"All Fields Are Required!", Toast.LENGTH_LONG).show();
               }
            break;
        }
    }

    public void upload(final String email, final String name, final String cell, final String manufacturer, final String price)
    {
        myProgressDialog.show();
        myProgressDialog.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
        StringRequest request = new StringRequest(Request.Method.POST, Globals.USER_SUBSCRIPTION_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if( Globals.GROUP_CREATION_SUCCESS.equalsIgnoreCase(response))
                        {
                            myProgressDialog.dismiss();
                            progress.show();
                            progress.setContentView(R.layout.success_layout);
                            mImgCheck = (AppCompatImageView)progress.findViewById(R.id.success_image);
                            avd = AnimatedVectorDrawableCompat.create(getApplicationContext(),R.drawable.animated_check);
                            // drawable = mImgCheck.getDrawable();
                            // mImgCheck.setImageDrawable(avd);
                            ((Animatable) mImgCheck.getDrawable()).start();
                            countDownTimer = new CountDownTimer(COUNT_DOWN,16) {
                                @Override
                                public void onTick(long l) {
                                }
                                @Override
                                public void onFinish(){
                                    mImgCheck.setVisibility(View.GONE);
                                    progress.dismiss();
                                    finish();
                                }
                            };
                            countDownTimer.start();
                        }
                        else
                        {
                            myProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("email",email);
                parameters.put("name",name);
                parameters.put("cell",cell);
                parameters.put("device", Device.token);
                return parameters;
            }
        };
        requestQueue.add(request);
    }
}
