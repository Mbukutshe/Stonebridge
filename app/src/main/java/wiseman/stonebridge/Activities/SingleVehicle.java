package wiseman.stonebridge.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

import wiseman.stonebridge.Globals.VehiclesDetails;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-01-25.
 */

public class SingleVehicle extends AppCompatActivity implements View.OnClickListener{

    TextView prices,feature,transmission,mileage,province;
    ImageView vehicles;
    Bundle extras;
    DatabaseReference mdatabaseRef;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_vehicle_fragment);
        extras = getIntent().getExtras();
        getSupportActionBar().setTitle(VehiclesDetails.name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prices = (TextView)findViewById(R.id.subject_message);
        feature = (TextView)findViewById(R.id.message_message);
        vehicles = (ImageView)findViewById(R.id.video_thumbnail);
        transmission = (TextView)findViewById(R.id.transmission);
        mileage = (TextView)findViewById(R.id.mileage);
        province = (TextView)findViewById(R.id.province);
        fragmentManager = getSupportFragmentManager();
        mdatabaseRef = FirebaseDatabase.getInstance().getReference().child(FirebaseInstanceId.getInstance().getToken());
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        ///format.setCurrency(Currency.getInstance("R"));
        String results = format.format(Double.parseDouble(VehiclesDetails.price));
        results = "R"+results.substring(1);
        Picasso.with(this).load(VehiclesDetails.vehicle).into(vehicles);
        prices.setText(results);
        feature.setText(VehiclesDetails.features);
        transmission.setText(VehiclesDetails.transmission);
        mileage.setText(VehiclesDetails.mileage+"km");
        province.setText(VehiclesDetails.province);

        vehicles.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.video_thumbnail:
                Intent intent = new Intent(view.getContext(), ZoomPicture.class);
                intent.putExtra("vehicle",VehiclesDetails.vehicle);
                startActivity(intent);
            break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.questions) {
            startActivity(new Intent(getApplicationContext(),Clarity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
