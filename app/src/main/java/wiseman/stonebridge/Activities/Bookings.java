package wiseman.stonebridge.Activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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
 * Created by Wiseman on 2018-03-22.
 */

public class Bookings extends AppCompatActivity implements View.OnClickListener{
    EditText first_name,mobile_number,email,message;
    LinearLayout makeup,hair,morning,afternoon,evening,anytime,book;
    AppCompatCheckBox makeup_check,hair_check,morning_check,afternoon_check,evening_check,anytime_check;
    String name,number,phone,address,say,appointment_type,best_time;
    ScrollView subscribe_layout;
    ProgressDialog myProgressDialog,progress;
    RequestQueue requestQueue;
    public static  int COUNT_DOWN=1000;
    CountDownTimer countDownTimer;
    AppCompatImageView mImgCheck;
    AnimatedVectorDrawableCompat avd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookings_layout);
        appointment_type="Makeup";
        best_time = "Morning";
        first_name = findViewById(R.id.name);
        mobile_number = findViewById(R.id.number);
        email = findViewById(R.id.email);
        message = findViewById(R.id.text_message);

        makeup = findViewById(R.id.makeup);
        makeup.setOnClickListener(this);
        hair = findViewById(R.id.hair);
        hair.setOnClickListener(this);
        morning = findViewById(R.id.morning);
        morning.setOnClickListener(this);
        afternoon = findViewById(R.id.afternoon);
        afternoon.setOnClickListener(this);
        evening = findViewById(R.id.evening);
        evening.setOnClickListener(this);
        anytime = findViewById(R.id.anytime);
        anytime.setOnClickListener(this);
        book = findViewById(R.id.book_button);
        book.setOnClickListener(this);

        makeup_check = findViewById(R.id.makeup_check);
        makeup_check.setOnClickListener(this);
        makeup_check.setChecked(true);
        hair_check = findViewById(R.id.hair_check);
        hair_check.setOnClickListener(this);
        morning_check = findViewById(R.id.morning_check);
        morning_check.setOnClickListener(this);
        morning_check.setChecked(true);
        afternoon_check = findViewById(R.id.afternoon_check);
        afternoon_check.setOnClickListener(this);
        evening_check = findViewById(R.id.evening_check);
        evening_check.setOnClickListener(this);
        anytime_check = findViewById(R.id.anytime_check);
        anytime_check.setOnClickListener(this);

        subscribe_layout =(ScrollView) findViewById(R.id.subscribe_layout);
        subscribe_layout.getBackground().setAlpha(180);
        myProgressDialog = new ProgressDialog(this);
        progress = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.book_button:
                name = first_name.getText().toString();
                number = mobile_number.getText().toString();
                address = email.getText().toString();
                say = message.getText().toString();
                upload(name,number,address,say);
            break;
            case R.id.makeup:
                makeup_check.setChecked(true);
                hair_check.setChecked(false);
                appointment_type = "MakeUp";
            break;
            case R.id.makeup_check:
                makeup_check.setChecked(true);
                hair_check.setChecked(false);
                appointment_type = "MakeUp";
            break;
            case R.id.hair:
                hair_check.setChecked(true);
                makeup_check.setChecked(false);
                appointment_type = "Hair";
            break;
            case R.id.hair_check:
                hair_check.setChecked(true);
                makeup_check.setChecked(false);
                appointment_type = "Hair";
            break;
            case R.id.morning:
                morning_check.setChecked(true);
                afternoon_check.setChecked(false);
                evening_check.setChecked(false);
                anytime_check.setChecked(false);
                best_time = "Morning";
            break;
            case R.id.morning_check:
                morning_check.setChecked(true);
                afternoon_check.setChecked(false);
                evening_check.setChecked(false);
                anytime_check.setChecked(false);
                best_time = "Morning";
            break;
            case R.id.afternoon:
                afternoon_check.setChecked(true);
                morning_check.setChecked(false);
                evening_check.setChecked(false);
                anytime_check.setChecked(false);
                best_time = "Afternoon";
            break;
            case R.id.afternoon_check:
                afternoon_check.setChecked(true);
                morning_check.setChecked(false);
                evening_check.setChecked(false);
                anytime_check.setChecked(false);
                best_time = "Afternoon";
            break;
            case R.id.evening:
                evening_check.setChecked(true);
                morning_check.setChecked(false);
                afternoon_check.setChecked(false);
                anytime_check.setChecked(false);
                best_time = "Evening";
            break;
            case R.id.evening_check:
                evening_check.setChecked(true);
                morning_check.setChecked(false);
                afternoon_check.setChecked(false);
                anytime_check.setChecked(false);
                best_time = "Evening";
            break;
            case R.id.anytime:
                anytime_check.setChecked(true);
                morning_check.setChecked(false);
                afternoon_check.setChecked(false);
                evening_check.setChecked(false);
                best_time = "Anytime";
            break;
            case R.id.anytime_check:
                anytime_check.setChecked(true);
                morning_check.setChecked(false);
                afternoon_check.setChecked(false);
                evening_check.setChecked(false);
                best_time = "Anytime";
            break;
        }
    }
    public void upload(final String email, final String name, final String cell, final String say)
    {
        myProgressDialog.show();
        myProgressDialog.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
        StringRequest request = new StringRequest(Request.Method.POST, Globals.BOOK_URL,

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
                parameters.put("phone",cell);
                parameters.put("message",say);
                parameters.put("appointment",appointment_type);
                parameters.put("best_time",best_time);
                parameters.put("date","");
                parameters.put("device", Device.token);
                return parameters;
            }
        };
        requestQueue.add(request);
    }
}
