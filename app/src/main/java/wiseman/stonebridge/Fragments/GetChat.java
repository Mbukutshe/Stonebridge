package wiseman.stonebridge.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wiseman.stonebridge.Adapters.ClarityAdapter;
import wiseman.stonebridge.Globals.Device;
import wiseman.stonebridge.Objects.ClarityObject;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-02-10.
 */

public class GetChat extends Fragment implements View.OnClickListener{
    View view;
    FrameLayout fab_send;
    LinearLayout layout_Caption;
    EditText caption;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    ClarityAdapter mAdapter;
    private DatabaseReference mdatabaseRef;
    List<ClarityObject> myDataset;
    ClarityObject clarity;
    String device;
    SimpleDateFormat dateformat;
    Calendar c;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.admin_chat_layout, container, false);
        layout_Caption = (LinearLayout)view.findViewById(R.id.layout_caption);
        fab_send = (FrameLayout)view.findViewById(R.id.fab_send);
        caption = (EditText)view.findViewById(R.id.title_image);
        fab_send.setOnClickListener(this);
        Firebase.setAndroidContext(this.getContext());
        c = Calendar.getInstance();
        dateformat = new SimpleDateFormat("hh:mm aa");
        mdatabaseRef =  FirebaseDatabase.getInstance().getReference().child("Users").child(Device.token).child("02:00 am");

        mRecyclerView = (RecyclerView)view.findViewById(R.id.my_recycler_comment);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        myDataset = new ArrayList<ClarityObject>();
        mAdapter = new ClarityAdapter(this.getContext(),myDataset,mRecyclerView,mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        getData();
        return view;
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id)
        {
            case R.id.fab_send:
                String time = dateformat.format(c.getTime());
                Map<String,String> comment = new HashMap<String,String>();
                comment.put("message",caption.getText().toString());
                comment.put("time",time);
                mdatabaseRef.push().setValue(comment);
                break;
        }
    }
    public void getData()
    {
        mdatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                clarity =dataSnapshot.getValue(ClarityObject.class);
                myDataset.add(new ClarityObject(clarity.time,clarity.message,clarity.who));
                mAdapter.notifyDataSetChanged();
                mLayoutManager.scrollToPosition(myDataset.size()-1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s){
                clarity =dataSnapshot.getValue(ClarityObject.class);
                myDataset.add(new ClarityObject(clarity.time,clarity.message,clarity.who));
                mAdapter.notifyDataSetChanged();
                mLayoutManager.scrollToPosition(myDataset.size()-1);
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
