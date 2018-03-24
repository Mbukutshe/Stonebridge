package wiseman.stonebridge.Fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import wiseman.stonebridge.Functions.Retrieve;
import wiseman.stonebridge.Globals.Globals;
import wiseman.stonebridge.R;


/**
 * Created by Wiseman on 2017-10-12.
 */

public class Documents extends Fragment implements View.OnClickListener{
    View view;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Retrieve read;
    ConnectivityManager connectivityManager;
    NetworkInfo activeNetwork;
    boolean isConnected;
    FrameLayout try_again;
    LinearLayout no_internet;

    LinearLayout empty;
    TextView icon,icon_message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_documents,container,false);

        connectivityManager = (ConnectivityManager)view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        try_again = (FrameLayout)view.findViewById(R.id.try_again);
        no_internet = (LinearLayout)view.findViewById(R.id.no_internet);
        try_again.setOnClickListener(this);

        empty = (LinearLayout)view.findViewById(R.id.empty_layout);
        icon = (TextView)view.findViewById(R.id.empty_icon);
        icon_message = (TextView)view.findViewById(R.id.empty_icon_message);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.document_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        Configuration config = getResources().getConfiguration();
        mLayoutManager = new LinearLayoutManager(this.getContext());
     /*   if((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL)
        {

        }
        else
        {
            mLayoutManager = new StaggeredGridLayoutManager(2,1);
        }*/
        mRecyclerView.setLayoutManager(mLayoutManager);
        read = new Retrieve();
        if(!isConnected)
        {
            no_internet.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
        else
        {
            read.fetch(view.getContext(),mRecyclerView, Globals.RETRIEVAL_DOCUMENT_CONDITION,empty,icon,icon_message);
            no_internet.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        activeNetwork = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        int id = view.getId();
        switch(id )
        {
            case R.id.try_again:
                if(!isConnected)
                {
                    no_internet.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
                else
                {
                    no_internet.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    read.fetch(view.getContext(),mRecyclerView,Globals.RETRIEVAL_DOCUMENT_CONDITION,empty,icon,icon_message);
                }
            break;
        }
    }
}
