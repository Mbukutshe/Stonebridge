package wiseman.stonebridge.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import wiseman.stonebridge.Adapters.SettingsAdapter;
import wiseman.stonebridge.Objects.Manufacturers;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-01-29.
 */

public class Settings extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Spinner price;
    String prices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_fragment);
        getSupportActionBar().setTitle("Settings");
        mRecyclerView = (RecyclerView)findViewById(R.id.manufacturer_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        List<Manufacturers> names = getManufacturers();
        Configuration config = getResources().getConfiguration();
        if((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL)
        {
            mLayoutManager = new StaggeredGridLayoutManager(2,1);
        }
        else
        {
            mLayoutManager = new StaggeredGridLayoutManager(3,1);
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        SettingsAdapter adapter = new SettingsAdapter(getApplicationContext(),names,mRecyclerView,mLayoutManager);
        mRecyclerView.setAdapter(adapter);

        price = (Spinner)findViewById(R.id.price_range);
        ArrayAdapter<CharSequence> adapta = ArrayAdapter.createFromResource(getApplicationContext(),R.array.prices,R.layout.dropdown_items);
        adapta.setDropDownViewResource(R.layout.dropdown_items);
        price.setAdapter(adapta);
        price.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                prices = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                prices = adapterView.getItemAtPosition(0).toString();
            }
        });

    }
    private List<Manufacturers> getManufacturers()
    {
      List<Manufacturers> names = new ArrayList<>();
        names.add(new Manufacturers("Volvo"));
        names.add(new Manufacturers("BMW"));
        names.add(new Manufacturers("Mercedes"));
        names.add(new Manufacturers("Mazda"));
        names.add(new Manufacturers("VW"));
        names.add(new Manufacturers("Toyota"));
        names.add(new Manufacturers("Nissan"));
        names.add(new Manufacturers("Ford"));
        names.add(new Manufacturers("Chevrolet"));
        names.add(new Manufacturers("Isuzu"));
        names.add(new Manufacturers("Audi"));
      return names;
    }
}
