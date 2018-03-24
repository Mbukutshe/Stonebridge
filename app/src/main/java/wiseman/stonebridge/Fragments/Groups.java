package wiseman.stonebridge.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wiseman.stonebridge.Functions.Retrieve;
import wiseman.stonebridge.R;


/**
 * Created by Wiseman on 2018-01-28.
 */

public class Groups extends Fragment {
    View view;
    RecyclerView groups_recycler_view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.groups_fragment,container,false);
        groups_recycler_view = (RecyclerView)view.findViewById(R.id.groups_recycler_view);
        Retrieve retrieve = new Retrieve();
        retrieve.getGroups(view.getContext(),groups_recycler_view);
        return view;
    }
}
