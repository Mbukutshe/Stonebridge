package wiseman.stonebridge.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wiseman.stonebridge.Adapters.QuoteAdapter;
import wiseman.stonebridge.Objects.QuoteObject;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-02-26.
 */

public class Quote extends Fragment {
    View view;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    QuoteAdapter adapter;
    List<QuoteObject> list;
    EditText description,quantity,price;
    TextView total;
    LinearLayout add,send;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.quotation_layout,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.quote_recycler_view);
        description = (EditText)view.findViewById(R.id.edit_desc);
        quantity = (EditText)view.findViewById(R.id.edit_quantity);
        price = (EditText)view.findViewById(R.id.edit_price);
        add = (LinearLayout)view.findViewById(R.id.add_quote);
        send = (LinearLayout)view.findViewById(R.id.send_button);
        total = (TextView)view.findViewById(R.id.text_total);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(manager);
        list = getList();
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), manager.getOrientation()));
        adapter = new QuoteAdapter(view.getContext(),list,description,quantity,price,add,send,total);
        recyclerView.setAdapter(adapter);
        return view;
    }
    public List<QuoteObject> getList()
    {
        List<QuoteObject> list = new ArrayList<>();
        list.add(new QuoteObject("","",""));
        return list;
    }
}
