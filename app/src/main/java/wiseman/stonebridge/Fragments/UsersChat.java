package wiseman.stonebridge.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import wiseman.stonebridge.Adapters.UsersAdapter;
import wiseman.stonebridge.Objects.KeyObject;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-02-09.
 */

public class UsersChat  extends Fragment {
    View view;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    UsersAdapter mAdapter;
    private DatabaseReference mdatabaseRef;
    KeyObject clarity;
    List<KeyObject> myDataset;
    String device;
    FragmentManager fragmentManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.users_chat_layout, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.chat_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mdatabaseRef =  FirebaseDatabase.getInstance().getReference().child("Users");
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
       // mLayoutManager.setReverseLayout(true);
        //mLayoutManager.setStackFromEnd(true);
        fragmentManager = getFragmentManager();
        myDataset = new ArrayList<KeyObject>();
        mAdapter = new UsersAdapter(view.getContext(),myDataset,fragmentManager);
        mRecyclerView.setAdapter(mAdapter);
        getData();
        return view;
    }
    public void getData()
    {
        mdatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                myDataset.add(new KeyObject(dataSnapshot.getKey()));
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s){
                clarity =dataSnapshot.getValue(KeyObject.class);
                myDataset.add(new KeyObject(clarity.getKey()));
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s){

            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
    }
}
