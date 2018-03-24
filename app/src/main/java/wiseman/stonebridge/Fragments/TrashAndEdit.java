package wiseman.stonebridge.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import wiseman.stonebridge.Adapters.TrashAndEditAdapter;
import wiseman.stonebridge.Globals.Globals;
import wiseman.stonebridge.R;
import wiseman.stonebridge.Services.TrashAndEditJSONParser;

/**
 * Created by Wiseman on 2018-02-12.
 */

public class TrashAndEdit extends Fragment {
    View view;
    ConnectivityManager connectivityManager;
    NetworkInfo activeNetwork;
    boolean isConnected;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    TrashAndEditAdapter adapter;
    RequestQueue requestQueue;
    List<wiseman.stonebridge.Objects.TrashAndEdit> mDataset;
    android.support.v4.app.FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.trash_vehicle_layout, container, false);

        connectivityManager = (ConnectivityManager)view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        requestQueue = Volley.newRequestQueue(view.getContext());
        mRecyclerView = (RecyclerView)view.findViewById(R.id.trash_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        fragmentManager = getFragmentManager();
        if(!isConnected)
        {
            Dialog dialog = new Dialog(view.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.no_internet_fragment);
            dialog.show();
        }
        else
        {
            fetch(view.getContext(), mRecyclerView);
        }
        return view;
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
                            mDataset = TrashAndEditJSONParser.parseData(response);
                            adapter = new TrashAndEditAdapter(view.getContext(),mDataset,fragmentManager,requestQueue);
                            mRecyclerView.setAdapter(adapter);
                            RelativeLayout layout = (RelativeLayout) myProgressDialog.findViewById(R.id.progress_layout);
                            ProgressBar bar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
                            ImageView image = (ImageView) myProgressDialog.findViewById(R.id.progress_image);
                            Animation anim = AnimationUtils.loadAnimation(context, R.anim.zoom_out);
                            layout.startAnimation(anim);
                            image.startAnimation(anim);
                            bar.startAnimation(anim);
                            myProgressDialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(context,"No Uploaded Vehicles", Toast.LENGTH_LONG).show();
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

        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}
