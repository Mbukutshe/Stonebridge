package wiseman.stonebridge.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import wiseman.stonebridge.Holders.ClarityHolder;
import wiseman.stonebridge.Holders.ResponderHolder;
import wiseman.stonebridge.Objects.ClarityObject;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-02-08.
 */

public class ClarityAdapter extends RecyclerView.Adapter{
    List<ClarityObject> mDataset;
    Context context;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private static final int VIEW_TYPE_QUESTION = 1;
    private static final int VIEW_TYPE_RESPONSE = 2;
    public ClarityAdapter(Context context, List<ClarityObject> mDataset, RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager)
    {
        this.mDataset = mDataset;
        this.context=context;
        this.recyclerView = recyclerView;
        this.layoutManager = layoutManager;
    }
    @Override
    public int getItemViewType(int position) {
        ClarityObject message = ( ClarityObject) mDataset.get(position);

        if (message.who.equals("enquirer")) {
            // If the current user is the sender of the message
            return VIEW_TYPE_QUESTION;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_RESPONSE;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        // set the view's size, margins, paddings and layout parameters

        if (viewType == VIEW_TYPE_QUESTION) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.question_item, parent, false);
            return new ClarityHolder(view);
        } else if (viewType == VIEW_TYPE_RESPONSE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.incoming_question_layout, parent, false);
            return new ResponderHolder(view);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        ClarityObject message = (ClarityObject) mDataset.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_QUESTION:
                ((ClarityHolder) holder).bind(message);
                break;
            case VIEW_TYPE_RESPONSE:
                ((ResponderHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
