package wiseman.stonebridge.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import wiseman.stonebridge.Functions.Animation;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-03-23.
 */

public class About extends AppCompatActivity implements OnMapReadyCallback,View.OnClickListener {
    LinearLayout message,call,emailto;
    ScrollView scrollView;
    public static  int COUNT_DOWN=200;
    CountDownTimer countDownTimer;
    wiseman.stonebridge.Functions.Animation anim;
    private GoogleMap mMap;
    Marker marker;
    MarkerOptions options;
    TextView email,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
        message = (LinearLayout)findViewById(R.id.title_message);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        scrollView.getBackground().setAlpha(140);
        anim = new Animation(getApplicationContext());
        anim.goDown(message);
        call = (LinearLayout)findViewById(R.id.call_number);
        call.setOnClickListener(this);
        emailto = (LinearLayout)findViewById(R.id.email_to);
        emailto.setOnClickListener(this);
        email = (TextView)findViewById(R.id.email);
        email.setOnClickListener(this);
        phone = (TextView)findViewById(R.id.phone);
        phone.setOnClickListener(this);
        countDownTimer = new CountDownTimer(COUNT_DOWN,16)
        {
            @Override
            public void onTick(long l) {

            }
            @Override
            public void onFinish() {
            }
        };
        countDownTimer.start();
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
      //  updateCoordinates();
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20), 2000, null);
        mMap.setMinZoomPreference(18.0f);
        mMap.setMaxZoomPreference(25.0f);
        LatLng points = new LatLng(-29.648928,31.054690);
        options = new MarkerOptions().position(points).title("13 Talwant Singh Road");
        marker = mMap.addMarker(options);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(points));
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.phone:
                call.setVisibility(View.VISIBLE);
                emailto.setVisibility(View.GONE);
                break;
            case R.id.call_number:

                break;
            case R.id.email:
                call.setVisibility(View.GONE);
                emailto.setVisibility(View.VISIBLE);
                break;
            case R.id.email_to:
                emailto.setVisibility(View.GONE);
                String[] TO = {"info@dazzleondivas.co.za"};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/html");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Enquiry");
                emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("" + ""));
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    Log.i("Finished...", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
