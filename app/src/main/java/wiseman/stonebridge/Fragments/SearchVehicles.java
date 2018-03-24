package wiseman.stonebridge.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-01-26.
 */

public class SearchVehicles extends Fragment {
    View view;
    LinearLayout search;
    AppCompatSpinner make,price,year;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.search_fragment,container,false);
        return view;
    }
}
