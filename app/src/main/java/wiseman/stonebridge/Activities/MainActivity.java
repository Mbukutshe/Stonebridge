package wiseman.stonebridge.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

import wiseman.stonebridge.Fragments.SignUp;
import wiseman.stonebridge.Fragments.Vehicles;
import wiseman.stonebridge.Functions.Animation;
import wiseman.stonebridge.Functions.Functions;
import wiseman.stonebridge.Functions.InsertToDatabase;
import wiseman.stonebridge.Functions.Visible;
import wiseman.stonebridge.Globals.Access;
import wiseman.stonebridge.Globals.Device;
import wiseman.stonebridge.Globals.Globals;
import wiseman.stonebridge.Location.MyLocationListener;
import wiseman.stonebridge.R;
import wiseman.stonebridge.Services.DevicesJSONParser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback,LocationListener {
    ViewPager viewPager;
    FragmentManager fragmentManager;
    Functions functions;
    Visible visible;
    LinearLayout open_groups;
    Animation animation;
    InsertToDatabase insertToDatabase;
    private GoogleMap mMap;
    private double latitude;
    private double longitude;
    private LocationManager mlocManager;
    MyLocationListener locationListener;
    static RequestQueue requestQueue;
    MarkerOptions options;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        functions = new Functions(this);
        animation = new Animation(this);
        insertToDatabase = new InsertToDatabase(this);
        insertToDatabase.insertDeviceId(FirebaseInstanceId.getInstance().getToken());
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        // setupViewPager(viewPager);
        Device.token = FirebaseInstanceId.getInstance().getToken();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.motorlogo).setText("Styles"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.messages).setText("Messages"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.document).setText("Documents"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.gallery).setText("Media"));
        tabLayout.setOnTabSelectedListener(functions);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (slideOffset != 0)
                {

                }
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.content_main,new Vehicles()).commit();
        functions.setFragmentManager(fragmentManager);
        open_groups = (LinearLayout)findViewById(R.id.open_groups);
        open_groups.setVisibility(View.GONE);
        open_groups.setOnClickListener(functions);
        visible =new Visible();
        mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //locationListener = new MyLocationListener(getApplicationContext());
        boolean enabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        try
        {
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,this);
        }
        catch(SecurityException e)
        {

        }
}
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.cars);
                builder.setMessage(Html.fromHtml("<font color='#627984'>Are you sure you want to sign out?</font>"))
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                //super.onBackPressed();
            }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
       // startActivity(new Intent(getApplicationContext(),Settings.class));
        if (id == R.id.nav_admin)
        {
            if(Access.granted)
            {
                startActivity(new Intent(getApplicationContext(),AdministratorDashboard.class));
            }
            else
            {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }


        }
        else if (id == R.id.nav_sign_up) {
            startActivity(new Intent(getApplicationContext(),SignUp.class));
        }
        else
            if(id == R.id.nav_Help)
            {
                startActivity(new Intent(getApplicationContext(),Clarity.class));
            }
            else
                if(id == R.id.nav_rate)
                {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("market://details?id="+getPackageName()));
                   try
                   {
                       startActivity(i);
                   }
                   catch (android.content.ActivityNotFoundException exc)
                   {
                       startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id="+getPackageName())));
                   }

                }
                else
                    if(id==R.id.nav_booking)
                    {
                        startActivity(new Intent(getApplicationContext(),Bookings.class));
                    }
                    else
                    if(id==R.id.nav_about)
                    {
                        startActivity(new Intent(getApplicationContext(),About.class));
                    }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
    @Override
    public void onLocationChanged(Location loc)
    {
        latitude=loc.getLatitude();
        longitude=loc.getLongitude();
        updateCoordinates(FirebaseInstanceId.getInstance().getToken()+"",latitude+"",longitude+"");
    }

    @Override

    public void onProviderDisabled(String provider)
    {
        Toast.makeText(getApplicationContext(),"Gps Disabled", Toast.LENGTH_SHORT ).show();
    }

    @Override

    public void onProviderEnabled(String provider)
    {
        Toast.makeText(getApplicationContext(),"Gps Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override

    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }
    public void updateCoordinates(final String Token, final String Latitude, final String Longitude)
    {
        StringRequest request = new StringRequest(Request.Method.POST, Globals.UPDATE_MAP_COORDINATES_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
                parameters.put(Globals.TOKEN,Token);
                parameters.put(Globals.LATITUDE,Latitude);
                parameters.put(Globals.LONGITUDE,Longitude);
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
    public void readDevices()
    {
        StringRequest request = new StringRequest(Request.Method.POST, Globals.USERS_DEVICES_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DevicesJSONParser.parseData(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}
