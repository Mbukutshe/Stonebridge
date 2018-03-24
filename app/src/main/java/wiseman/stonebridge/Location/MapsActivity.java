package wiseman.stonebridge.Location;

/**
 * Created by Wiseman on 2017-10-17.
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import wiseman.stonebridge.Globals.Globals;
import wiseman.stonebridge.Objects.MapCoordinates;
import wiseman.stonebridge.R;
import wiseman.stonebridge.Services.Coordinates;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,LocationListener {

    private GoogleMap mMap;
    private double lat;
    private double log;
    private LocationManager mlocManager;
    MyLocationListener locationListener;
    RequestQueue requestQueue;
    MarkerOptions options;
    List<MapCoordinates> coordinates;
    Marker marker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_maps);
       getSupportActionBar().setTitle("My Employees Last Location(s)");
        mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //locationListener = new MyLocationListener(getApplicationContext());
        boolean enabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        try
        {
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,this);
        }
        catch(SecurityException e)
        {

        }
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateCoordinates();
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20), 2000, null);
        mMap.setMinZoomPreference(6.0f);
        mMap.setMaxZoomPreference(14.0f);
      // mMap.animateCamera(CameraUpdateFactory.zoomTo(100));
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        mMap.setMyLocationEnabled(true);
    }
    @Override
    public void onLocationChanged(Location loc)
    {
    /*
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        mMap.setMyLocationEnabled(true);*/
        /*
        lat=loc.getLatitude();
        log=loc.getLongitude();
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(view.getContext(), Locale.getDefault());
        LatLng points = new LatLng(lat, log);
        marker.setPosition(points);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(points, 16.0f));
        if(ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        mMap.setMyLocationEnabled(true);
        try {
            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            //  String address = addresses.get(0).getAddressLine(0);
            //    String City = addresses.get(0).getLocality().toString();

        }catch (MalformedURLException e) {
            Toast.makeText(view.getContext(), e.getMessage().toString() , Toast.LENGTH_LONG).show();
        }

        catch (IOException e) {
            Toast.makeText(view.getContext(), e.getMessage().toString() , Toast.LENGTH_LONG).show();
        }*/
    }

    @Override

    public void onProviderDisabled(String provider)
    {
        Toast.makeText(getApplicationContext(),"Gps Disabled", Toast.LENGTH_SHORT ).show();
    }

    @Override

    public void onProviderEnabled(String provider)
    {
        Toast.makeText( getApplicationContext(),"Gps Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override

    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }
    public void updateCoordinates()
    {
        StringRequest request = new StringRequest(Request.Method.GET, Globals.RETRIEVE_MAP_COORDINATES_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        coordinates = Coordinates.parseData(response);
                        for(int i=0;i<coordinates.size();i++)
                        {
                            double latitude = Double.parseDouble(coordinates.get(i).getLatitude());
                            double longitude = Double.parseDouble(coordinates.get(i).getLongitude());
                            LatLng points = new LatLng(latitude,longitude);
                            options = new MarkerOptions().position(points).title("Your Location");
                            marker = mMap.addMarker(options);
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(points));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
}
