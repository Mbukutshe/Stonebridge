package wiseman.stonebridge.Location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Wiseman on 2017-10-17.
 */

public class MyLocationListener implements LocationListener
{
    Context context;
    private GoogleMap mMap;
    public static double lati,longi;
    public MyLocationListener(Context context)
    {
        this.context = context;
    }
    @Override
    public void onLocationChanged(Location loc)
    {
        lati=loc.getLatitude();
        longi=loc.getLongitude();
        Toast.makeText(context, lati+"", Toast.LENGTH_LONG).show();
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(lati, longi)).title("Pristigio");
        try {
            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
          //  String address = addresses.get(0).getAddressLine(0);
         //    String City = addresses.get(0).getLocality().toString();

        }catch (MalformedURLException e) {
             Toast.makeText(context, e.getMessage().toString() , Toast.LENGTH_LONG).show();
        }

        catch (IOException e) {
             Toast.makeText(context, e.getMessage().toString() , Toast.LENGTH_LONG).show();
        }
    }

    @Override

    public void onProviderDisabled(String provider)
    {
        Toast.makeText(context,"Gps Disabled", Toast.LENGTH_SHORT ).show();
    }

    @Override

    public void onProviderEnabled(String provider)
    {
        Toast.makeText( context,"Gps Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override

    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }
}
