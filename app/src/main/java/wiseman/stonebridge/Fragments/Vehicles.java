package wiseman.stonebridge.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wiseman.stonebridge.Adapters.VehiclesAdapter;
import wiseman.stonebridge.Globals.Globals;
import wiseman.stonebridge.Objects.VehicleObject;
import wiseman.stonebridge.R;
import wiseman.stonebridge.Services.VehiclesDataset;

/**
 * Created by Wiseman on 2018-01-25.
 */

public class Vehicles extends Fragment implements View.OnClickListener{
    View view;
    LinearLayout search_layout,search,search_vehicle_layout,search_elements;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    VehiclesAdapter adapter;
    List<VehicleObject> mDataset;
    String make="Any",year,price=0+"",province="All";
    RequestQueue requestQueue;
    LinearLayout empty;
    TextView icon,icon_message;
    RecyclerView.Adapter mAdapter;
    ConnectivityManager connectivityManager;
    NetworkInfo activeNetwork;
    boolean isConnected;
    FrameLayout try_again;
    LinearLayout no_internet;
    public static  int COUNT_DOWN=200;
    CountDownTimer countDownTimer;
    wiseman.stonebridge.Functions.Animation anim;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vehicles,container,false);

        connectivityManager = (ConnectivityManager)view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        try_again = (FrameLayout)view.findViewById(R.id.try_again);
        no_internet = (LinearLayout)view.findViewById(R.id.no_internet);
        try_again.setOnClickListener(this);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.vehicles_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        if(!isConnected)
        {
            no_internet.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
        else
        {
            mRecyclerView.setVisibility(View.VISIBLE);
            fetch(view.getContext(), mRecyclerView);
        }

        search_layout = (LinearLayout)view.findViewById(R.id.search_layout);
        search_elements = (LinearLayout)view.findViewById(R.id.search_elements);
        search_vehicle_layout = (LinearLayout)view.findViewById(R.id.search_vehicle_layout);
        search_layout.getBackground().setAlpha(180);
        search = (LinearLayout)view.findViewById(R.id.search_button);
        search_layout.setVisibility(View.GONE);
        search_vehicle_layout.setVisibility(View.GONE);
        empty = (LinearLayout)view.findViewById(R.id.empty_layout);
        icon = (TextView)view.findViewById(R.id.empty_icon);
        icon_message = (TextView)view.findViewById(R.id.empty_icon_message);
        Spinner prov = (Spinner)view.findViewById(R.id.province_spinner);

        ArrayAdapter<CharSequence> provi = ArrayAdapter.createFromResource(view.getContext(),R.array.provinces,R.layout.dropdown_items);
        provi.setDropDownViewResource(R.layout.dropdown_items);
        prov.setAdapter(provi);
        prov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                province= adapterView.getItemAtPosition(i).toString();
                if(i==0)
                {
                    province="All";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                province = "All";
            }
        });

        Spinner spinner = (Spinner)view.findViewById(R.id.make_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),R.array.make,R.layout.dropdown_items);
        adapter.setDropDownViewResource(R.layout.dropdown_items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              make = adapterView.getItemAtPosition(i).toString();
                if(i==0)
                {
                    make="Any";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                make="Any";
            }
        });

        Spinner spin = (Spinner)view.findViewById(R.id.years_spinner);
        ArrayAdapter<CharSequence> adapt = ArrayAdapter.createFromResource(view.getContext(),R.array.years,R.layout.dropdown_items);
        adapt.setDropDownViewResource(R.layout.dropdown_items);
        spin.setAdapter(adapt);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               year = i+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                year = 0+"";
            }
        });

        Spinner spinna = (Spinner)view.findViewById(R.id.price_spinner);
        ArrayAdapter<CharSequence> adapta = ArrayAdapter.createFromResource(view.getContext(),R.array.prices,R.layout.dropdown_items);
        adapta.setDropDownViewResource(R.layout.dropdown_items);
        spinna.setAdapter(adapta);
        spinna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               price = i+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                price = 0+"";
            }
        });

       search.setOnClickListener(this);
        anim = new wiseman.stonebridge.Functions.Animation(view.getContext());
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        activeNetwork = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        switch(id)
        {
            case R.id.search_button:
                if(search_elements.getVisibility()== View.GONE)
                {
                    search_vehicle_layout.setBackgroundResource(R.drawable.searchback);
                    search_elements.setVisibility(View.VISIBLE);
                    anim.goDown(search_elements);
                }
                else
                {
                    anim.goUp(search_elements);
                    countDownTimer = new CountDownTimer(COUNT_DOWN,16) {
                        @Override
                        public void onTick(long l) {

                        }
                        @Override
                        public void onFinish() {
                            search_elements.setVisibility(View.GONE);
                        }
                    };
                    countDownTimer.start();
                    search_vehicle_layout.setBackgroundResource(R.drawable.searchbutton);
                    if(!isConnected)
                    {
                        no_internet.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }
                    else
                    {

                        mRecyclerView.setVisibility(View.VISIBLE);
                        filter(this.view.getContext());
                    }
                }
            break;
            case R.id.try_again:
                if(!isConnected)
                {
                    no_internet.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
                else
                {
                    mLayoutManager = new LinearLayoutManager(this.view.getContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    no_internet.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    fetch(this.view.getContext(),mRecyclerView);
                }
            break;
        }
    }
    public void fetch(final Context context, final RecyclerView recyclerView) {
        final ProgressDialog myProgressDialog = new ProgressDialog(context);
        myProgressDialog.show();
        myProgressDialog.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
        StringRequest request = new StringRequest(Request.Method.GET, Globals.RETRIEVE_VEHICLES_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equalsIgnoreCase(Globals.RETRIEVAL_EMPTY_CONDITION)) {
                            mDataset = VehiclesDataset.parseData(response);
                            adapter = new VehiclesAdapter(view.getContext(),mDataset,mRecyclerView,mLayoutManager);
                            mRecyclerView.setAdapter(adapter);
                            RelativeLayout layout = (RelativeLayout) myProgressDialog.findViewById(R.id.progress_layout);
                            ProgressBar bar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
                            ImageView image = (ImageView) myProgressDialog.findViewById(R.id.progress_image);
                            Animation anim = AnimationUtils.loadAnimation(context, R.anim.zoom_out);
                            layout.startAnimation(anim);
                            image.startAnimation(anim);
                            bar.startAnimation(anim);
                            myProgressDialog.dismiss();
                            recyclerView.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.GONE);
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                            icon.setBackgroundResource(R.drawable.nomessages);
                            icon_message.setText("No Vehicles To Show");
                            myProgressDialog.dismiss();
                        }


                    }
                },
                new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context,error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                if (response.headers == null)
                {
                    // cant just set a new empty map because the member is final.
                    response = new NetworkResponse(
                            response.statusCode,
                            response.data,
                            Collections.<String, String>emptyMap(), // this is the important line, set an empty but non-null map.
                            response.notModified,
                            response.networkTimeMs);


                }
                return super.parseNetworkResponse(response);
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(request);
    }
    public void filter(final Context context)
    {
        final ProgressDialog myProgressDialog = new ProgressDialog(context);
        myProgressDialog.show();
        myProgressDialog.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
        StringRequest request = new StringRequest(Request.Method.POST, Globals.FILTERING_VEHICLES_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equalsIgnoreCase(Globals.RETRIEVAL_EMPTY_CONDITION))
                        {
                            mDataset = VehiclesDataset.parseData(response);
                            adapter = new VehiclesAdapter(view.getContext(),mDataset,mRecyclerView,mLayoutManager);
                            mRecyclerView.setAdapter(adapter);
                            RelativeLayout layout = (RelativeLayout) myProgressDialog.findViewById(R.id.progress_layout);
                            ProgressBar bar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
                            ImageView image = (ImageView) myProgressDialog.findViewById(R.id.progress_image);
                            Animation anim = AnimationUtils.loadAnimation(context, R.anim.zoom_out);
                            layout.startAnimation(anim);
                            image.startAnimation(anim);
                            bar.startAnimation(anim);
                            myProgressDialog.dismiss();
                            mRecyclerView.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.GONE);
                        }
                        else
                        {
                            mRecyclerView.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                            icon.setBackgroundResource(R.drawable.nomessages);
                            icon_message.setText("No Vehicles To Show");
                            myProgressDialog.dismiss();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("province",province);
                parameters.put("make",make);
                parameters.put("price",price);
                parameters.put("year",year);
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(request);
    }
}
