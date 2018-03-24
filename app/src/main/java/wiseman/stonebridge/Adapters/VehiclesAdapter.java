package wiseman.stonebridge.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import wiseman.stonebridge.Activities.Clarity;
import wiseman.stonebridge.Activities.SingleVehicle;
import wiseman.stonebridge.Functions.Animation;
import wiseman.stonebridge.Globals.VehiclesDetails;
import wiseman.stonebridge.Holders.VehiclesHolder;
import wiseman.stonebridge.Objects.VehicleObject;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-01-25.
 */

public class VehiclesAdapter extends RecyclerView.Adapter<VehiclesHolder>{
    List<VehicleObject> mDataset;
    Context context,c;
    public RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    public static  int COUNT_DOWN=500;
    CountDownTimer countDownTimer;
    Animation anim;
    public VehiclesAdapter(Context context, List<VehicleObject> mDataset, RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        this.mDataset = mDataset;
        this.context=context;
        this.recyclerView = recyclerView;
        this.layoutManager = layoutManager;
        anim = new Animation(context);
    }
    @Override
    public VehiclesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_vehicles, parent, false);
        // set the view's size, margins, paddings and layout parameters

        VehiclesHolder vh = new VehiclesHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final VehiclesHolder holder, final int position) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        ///format.setCurrency(Currency.getInstance("R"));
        String results = format.format(Double.parseDouble(mDataset.get(position).getPrice()));
        results = "R"+results.substring(1);
        Picasso.with(context).load(mDataset.get(position).getUrl()).into(holder.video_thumbnail);
        holder.subject.setText(mDataset.get(position).getName());
        holder.message.setText(mDataset.get(position).getFeaturers());
        holder.video_duration.setText("Ask");
        holder.description.getBackground().setAlpha(140);
        holder.video_footer.getBackground().setAlpha(200);
        holder.video_footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,Clarity.class));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, SingleVehicle.class);
                VehiclesDetails.vehicle = mDataset.get(position).getUrl();
                VehiclesDetails.name = mDataset.get(position).getName();
                VehiclesDetails.price = mDataset.get(position).getPrice();
                VehiclesDetails.features = mDataset.get(position).getFeaturers();
                context.startActivity(intent);
            }
        });
        anim.goDown(holder.itemView);
        anim.goDown(holder.video_thumbnail);
        anim.goDown(holder.subject);
        anim.goDown(holder.message);
        anim.goDown(holder.video_duration);
        anim.goDown(holder.video_footer);
        countDownTimer = new CountDownTimer(COUNT_DOWN,16)
        {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
            }
        };
        countDownTimer.start();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    private Bitmap convertImageViewToBitmap(ImageView v){

        Bitmap bm=((BitmapDrawable)v.getDrawable()).getBitmap();

        return bm;
    }

}
