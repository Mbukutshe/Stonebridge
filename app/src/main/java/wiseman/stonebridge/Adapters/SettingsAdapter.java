package wiseman.stonebridge.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import wiseman.stonebridge.Holders.SettingsHolder;
import wiseman.stonebridge.Objects.Manufacturers;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-01-29.
 */

public class SettingsAdapter extends RecyclerView.Adapter<SettingsHolder> {
    List<Manufacturers> mDataset;
    Context context,c;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    public SettingsAdapter(Context context, List<Manufacturers> mDataset, RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager)
    {
        this.mDataset = mDataset;
        this.context=context;
        this.recyclerView = recyclerView;
        this.layoutManager = layoutManager;
    }
    @Override
    public SettingsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manufaturer_name, parent, false);
        // set the view's size, margins, paddings and layout parameters

       SettingsHolder vh = new SettingsHolder(view);
        return vh;
    }
    @Override
    public void onBindViewHolder(SettingsHolder holder, int position) {
        holder.manufacturer.setText(mDataset.get(position).getName());
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
