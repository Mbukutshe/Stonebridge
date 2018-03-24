package wiseman.stonebridge.Functions;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wiseman.stonebridge.Adapters.DocumentAdapter;
import wiseman.stonebridge.Adapters.GroupAdapter;
import wiseman.stonebridge.Adapters.MessageAdapter;
import wiseman.stonebridge.Globals.Globals;
import wiseman.stonebridge.Objects.Groups;
import wiseman.stonebridge.Objects.Item;
import wiseman.stonebridge.R;
import wiseman.stonebridge.Services.GroupsJSONParser;
import wiseman.stonebridge.Services.PostJSONParser;

/**
 * Created by Wiseman on 2017-10-15.
 */

public class Retrieve{
    RequestQueue requestQueue;
    LinearLayout empty;
    TextView icon,icon_message;
    RecyclerView.Adapter mAdapter;

    public void fetch(final Context context, final RecyclerView recyclerView, final String type, final LinearLayout empty, final TextView icon, final TextView icon_message)
    {
        final ProgressDialog myProgressDialog = new ProgressDialog(context);
        myProgressDialog.show();
        myProgressDialog.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
        StringRequest request = new StringRequest(Request.Method.POST, Globals.RETRIEVE_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equalsIgnoreCase(Globals.RETRIEVAL_EMPTY_CONDITION)) {
                            List<Item> items = PostJSONParser.parseData(response);
                            if(Globals.RETRIEVAL_MESSAGE_CONDITION.equals(type))
                            {
                                mAdapter= new MessageAdapter(context,items);
                            }
                            else
                            {
                                mAdapter = new DocumentAdapter(context, items);
                            }
                            recyclerView.setAdapter(mAdapter);
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
                        }
                        else
                        {
                          recyclerView.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                            if(Globals.RETRIEVAL_MESSAGE_CONDITION.equals(type))
                            {
                                icon.setBackgroundResource(R.drawable.nomessages);
                                icon_message.setText("No Messages To Show");
                            }
                            else
                            {
                                icon.setBackgroundResource(R.drawable.nodocfile);
                                icon_message.setText("No Documents To Show");
                            }
                            myProgressDialog.dismiss();
                        }


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
                parameters.put(Globals.RETRIEVAL_TYPE,type);
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void getGroups(final Context context, final RecyclerView recyclerView)
    {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, mLayoutManager.getOrientation()));
        StringRequest request = new StringRequest(Request.Method.POST, Globals.GROUPS_SELECTION,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equalsIgnoreCase(Globals.RETRIEVAL_EMPTY_CONDITION)) {
                            List<Groups> items = GroupsJSONParser.parseData(response);
                            mAdapter= new GroupAdapter(context,items);
                            recyclerView.setAdapter(mAdapter);

                          /*  recyclerView.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.GONE);*/
                        }
                        else
                        {
                         /* recyclerView.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                            icon.setBackgroundResource(R.drawable.nomessages);
                            icon_message.setText("No Messages To Show");
                            myProgressDialog.dismiss();*/
                        }


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
                parameters.put(Globals.RETRIEVAL_TYPE,"type");
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

}
