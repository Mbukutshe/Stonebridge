package wiseman.stonebridge.Adapters;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wiseman.stonebridge.Fragments.UploadVehicle;
import wiseman.stonebridge.Globals.Globals;
import wiseman.stonebridge.Globals.Vehicle;
import wiseman.stonebridge.Holders.TrashAndEditHolder;
import wiseman.stonebridge.Objects.TrashAndEdit;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-02-12.
 */

public class TrashAndEditAdapter extends RecyclerView.Adapter<TrashAndEditHolder>
{
    Context context;
    List<TrashAndEdit> mDataset;
   android.support.v4.app.FragmentManager fragmentManager;
    RequestQueue requestQueue;
    ActionBar actionBar;
    public TrashAndEditAdapter(Context context, List<TrashAndEdit> mDataset, android.support.v4.app.FragmentManager fragmentManager, RequestQueue requestQueue)
    {
        this.context =context;
        this.mDataset = mDataset;
        this.fragmentManager = fragmentManager;
        this.requestQueue = requestQueue;
        this.actionBar = actionBar;
    }

    @Override
    public TrashAndEditHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trash_items, parent, false);
        // set the view's size, margins, paddings and layout parameters

        TrashAndEditHolder vh = new  TrashAndEditHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(TrashAndEditHolder holder, final int position)
    {
        Picasso.with(context).load(mDataset.get(position).url).into(holder.picture);
        holder.name.setText(mDataset.get(position).make+" "+mDataset.get(position).model);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.UPLOAD_OPERATION = "edit";
                Vehicle.id = mDataset.get(position).id;
                Vehicle.province = mDataset.get(position).province;
                Vehicle.make = mDataset.get(position).make;
                Vehicle.transmission = mDataset.get(position).transmission;
                Vehicle.model = mDataset.get(position).model;
                Vehicle.mileage= mDataset.get(position).mileage;
                Vehicle.year = mDataset.get(position).year;
                Vehicle.price = mDataset.get(position).price;
                Vehicle.features = mDataset.get(position).featurers;
                Vehicle.url = mDataset.get(position).url;
                fragmentManager.beginTransaction().replace(R.id.admin_fragment,new UploadVehicle()).addToBackStack("Edit").commit();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.cars);
                builder.setMessage(Html.fromHtml("<font color='#627984'>Are you sure you want to delete this Item?</font>"))
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            trash(mDataset.get(position).id+"",position);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    public void trash(final String id,final int position)
    {
        final ProgressDialog myProgressDialog = new ProgressDialog(context);
        myProgressDialog.show();
        myProgressDialog.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
        StringRequest request = new StringRequest(Request.Method.POST, Globals.TRASH_VEHICLE_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.equalsIgnoreCase(Globals.GROUP_CREATION_SUCCESS)) {
                            myProgressDialog.dismiss();
                            Toast.makeText(context,"Deletion completed", Toast.LENGTH_LONG).show();
                            mDataset.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position,mDataset.size());
                        }
                        else
                        {
                            myProgressDialog.dismiss();
                            Toast.makeText(context,"Deletion not completed", Toast.LENGTH_LONG).show();
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",id);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}
