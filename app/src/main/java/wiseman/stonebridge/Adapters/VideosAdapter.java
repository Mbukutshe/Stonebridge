package wiseman.stonebridge.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.CountDownTimer;
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

import wiseman.stonebridge.Activities.VideoPlayer;
import wiseman.stonebridge.Holders.VideosHolder;
import wiseman.stonebridge.Objects.Item;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2017-10-02.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosHolder> {
    List<Item> mDataset;
    Context context,c;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    wiseman.stonebridge.Functions.Animation anim;
    Animation upAnim;
    public static  int COUNT_DOWN=200;
    CountDownTimer countDownTimer;
    ProgressDialog myProgressDialog;
    public VideosAdapter(Context context, List<Item> mDataset, RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager, ProgressDialog myProgressDialog) {
        this.mDataset = mDataset;
        this.context=context;
        this.recyclerView = recyclerView;
        this.layoutManager = layoutManager;
        this.myProgressDialog = myProgressDialog;
        anim = new  wiseman.stonebridge.Functions.Animation(context);
    }
    @Override
    public VideosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        c = parent.getContext();
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_videos, parent, false);
        // set the view's size, margins, paddings and layout parameters

        VideosHolder vh = new VideosHolder(view);
        return vh;
    }
    @Override
    public void onBindViewHolder(final VideosHolder holder, final int position) {
        if (mDataset.get(position).getAttach().toString().equalsIgnoreCase("video")) {
            holder.message.setText(mDataset.get(position).getMessage());
            holder.author.setText(mDataset.get(position).getAuthor());
            holder.date.setText(mDataset.get(position).getDate());
            holder.subject.setText("" + mDataset.get(position).getSubject());
            holder.link.setText(mDataset.get(position).getLink());
            holder.filename.setText(mDataset.get(position).getFilename());
            holder.video_thumbnail.setImageBitmap(mDataset.get(position).getImage());
            holder.video_duration.setText(mDataset.get(position).getDuration());
            holder.play.getBackground().setAlpha(150);
            holder.video_footer.getBackground().setAlpha(180);
            upAnim = AnimationUtils.loadAnimation(context, R.anim.fromtop_translation);
            holder.itemView.clearAnimation();
            holder.itemView.startAnimation(upAnim);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((Activity)context).startActivity(new Intent(view.getContext(), VideoPlayer.class));
                }
            });
            holder.play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    upAnim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                    holder.play.startAnimation(upAnim);
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
            anim.goDown(holder.itemView);
            anim.goDown(holder.video_thumbnail);
            anim.goDown(holder.subject);
            anim.goDown(holder.message);
            anim.goDown(holder.date);
            anim.goDown(holder.author);
            anim.goDown(holder.video_duration);
            anim.goDown(holder.play);
            anim.goDown(holder.video_footer);
            countDownTimer = new CountDownTimer(COUNT_DOWN,16) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                }
            };
            countDownTimer.start();
        }
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
