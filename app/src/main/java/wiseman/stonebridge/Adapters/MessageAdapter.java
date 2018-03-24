package wiseman.stonebridge.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

import wiseman.stonebridge.Holders.MessageHolder;
import wiseman.stonebridge.Objects.Item;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2017-10-15.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageHolder>{

    public static List<Item> mDataset;
    Context context,c;
    public MessageAdapter(Context context, List<Item> mDataset) {
        this.mDataset = mDataset;
        this.context=context;
    }
    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        c = parent.getContext();
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_messages, parent, false);
        // set the view's size, margins, paddings and layout parameters

        MessageHolder vh = new MessageHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MessageHolder holder, int position) {
        if (mDataset.get(position).getAttach().toString().equalsIgnoreCase("no")) {
            holder.message.setText(mDataset.get(position).getMessage());
            holder.author.setText(mDataset.get(position).getAuthor());
            holder.date.setText(mDataset.get(position).getDate());
            holder.subject.setText("" + mDataset.get(position).getSubject());
            holder.link.setText(mDataset.get(position).getLink());
            holder.filename.setText(mDataset.get(position).getFilename());
        }

        final Animation upAnim = AnimationUtils.loadAnimation(context,R.anim.fromtop_translation);
        holder.itemView.clearAnimation();
        holder.itemView.startAnimation(upAnim);


        if(mDataset.get(position).getUrgent().toString().equalsIgnoreCase("yes"))
        {
            holder.urgent.setBackgroundResource(R.drawable.important);
        }
        else
        {
            holder.urgent.setBackgroundResource(R.drawable.notimportant);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.message.setMaxLines(50);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}

