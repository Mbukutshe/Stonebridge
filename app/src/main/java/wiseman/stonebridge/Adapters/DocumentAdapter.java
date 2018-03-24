package wiseman.stonebridge.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import wiseman.stonebridge.Holders.DocumentHolder;
import wiseman.stonebridge.Objects.Item;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2017-10-15.
 */

public class DocumentAdapter extends RecyclerView.Adapter<DocumentHolder>{

    List<Item> mDataset;
    Context context,c;
    Animation upAnim;
    public DocumentAdapter(Context context, List<Item> mDataset) {
        this.mDataset = mDataset;
        this.context=context;
    }
    @Override
    public DocumentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        c = parent.getContext();
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_documents, parent, false);
        // set the view's size, margins, paddings and layout parameters

        DocumentHolder vh = new DocumentHolder(view);
        return vh;
    }
    @Override
    public void onBindViewHolder(final DocumentHolder holder, final int position) {
        if(mDataset.get(position).getAttach().toString().equalsIgnoreCase("yes")) {
            holder.message.setText(mDataset.get(position).getMessage());
            holder.author.setText(mDataset.get(position).getAuthor());
            holder.date.setText(mDataset.get(position).getDate());
            holder.subject.setText("" + mDataset.get(position).getSubject());
            holder.link.setText(mDataset.get(position).getLink());
            holder.filename.setText(mDataset.get(position).getFilename());
            holder.document_download.getBackground().setAlpha(150);
            if(holder.filename.getText().toString().endsWith(".pdf"))
            {
                holder.urgent.setBackgroundResource(R.drawable.pdfdocument);
            }
            else if(holder.filename.getText().toString().endsWith(".doc"))
            {
                holder.urgent.setBackgroundResource(R.drawable.worddocument);
            }
            else if(holder.filename.getText().toString().endsWith(".docx"))
            {
                holder.urgent.setBackgroundResource(R.drawable.worddocument);
            }
            else if(holder.filename.getText().toString().endsWith(".txt"))
            {
                holder.urgent.setBackgroundResource(R.drawable.txtdocument);
            }
            else if(holder.filename.getText().toString().endsWith(".xls"))
            {
                holder.urgent.setBackgroundResource(R.drawable.xlsdocument);
            }
            else if(holder.filename.getText().toString().endsWith(".xlsx"))
            {
                holder.urgent.setBackgroundResource(R.drawable.xlsdocument);
            }
            else if(holder.filename.getText().toString().endsWith(".ppt"))
            {
                holder.urgent.setBackgroundResource(R.drawable.pptdocument);
            }
            else if(holder.filename.getText().toString().endsWith(".pptx"))
            {
                holder.urgent.setBackgroundResource(R.drawable.pptdocument);
            }
            else
            {
                holder.urgent.setBackgroundResource(R.drawable.justdocument);
            }
        }
        upAnim = AnimationUtils.loadAnimation(context,R.anim.fromtop_translation);
        holder.itemView.clearAnimation();
        holder.itemView.startAnimation(upAnim);

        holder.urgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upAnim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                holder.urgent.startAnimation(upAnim);
                ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if(isConnected)
                {
                    String url = holder.link.getText()+"";
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(browserIntent);
                }
                else
                {
                    LayoutInflater infla = LayoutInflater.from(holder.itemView.getContext());
                    View layout =infla.inflate(R.layout.toast_container_layout,(ViewGroup)holder.itemView.findViewById(R.id.toast_layout));
                    TextView textview = (TextView)layout.findViewById(R.id.toast_message);
                    textview.setText("No Internet Connection!");
                    Toast toast = new Toast(context);
                    toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

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