package wiseman.stonebridge.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import wiseman.stonebridge.Functions.Animation;
import wiseman.stonebridge.R;


/**
 * Created by Wiseman on 2018-02-11.
 */

public class GetIn extends AppCompatActivity {
    ScrollView background;
    ConnectivityManager connectivityManager;
    NetworkInfo activeNetwork;
    boolean isConnected;
    FrameLayout frame_network,frame_progress;
    Animation anim;
    public static  int COUNT_DOWN=200;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);
        background = (ScrollView) findViewById(R.id.splash_layout);
        background.getBackground().setAlpha(180);
        frame_network = (FrameLayout)findViewById(R.id.frame_network);
        frame_progress =(FrameLayout)findViewById(R.id.frame_progress);
        ((FrameLayout)findViewById(R.id.slogan)).getBackground().setAlpha(200);
        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                load();
            }
        }, 1000);

    }
    public void load()
    {
        anim = new Animation(this);
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
        activeNetwork = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected)
        {
            progressBar.setVisibility(View.GONE);
            frame_network.setVisibility(View.VISIBLE);
            anim.slideDown(frame_network);
            anim.slideUp(frame_progress);
            countDownTimer = new CountDownTimer(COUNT_DOWN,16) {
                @Override
                public void onTick(long l) {

                }
                @Override
                public void onFinish() {

                    frame_progress.setVisibility(View.GONE);
                }
            };
            countDownTimer.start();
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    anim.slideUp(frame_network);
                    countDownTimer = new CountDownTimer(COUNT_DOWN,16) {
                        @Override
                        public void onTick(long l) {

                        }
                        @Override
                        public void onFinish() {
                            frame_network.setVisibility(View.GONE);
                            frame_progress.setVisibility(View.VISIBLE);
                        }
                    };
                    countDownTimer.start();
                }
            }, 2000);
        }
        else
        {
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                   startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }, 1000);
        }
    }

}
