package wiseman.stonebridge.Adapters;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import wiseman.stonebridge.Fragments.GetChat;
import wiseman.stonebridge.Functions.Animation;
import wiseman.stonebridge.Functions.Functions;
import wiseman.stonebridge.Globals.Device;
import wiseman.stonebridge.Holders.UsersHolder;
import wiseman.stonebridge.Objects.KeyObject;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-02-09.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersHolder> {

    List<KeyObject> mDataset;
    Context context;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Animation anim;
    public static  int COUNT_DOWN=200;
    CountDownTimer countDownTimer;
    Functions functions;
    FragmentManager fragmentManager;
    ActionBar actionBar;
    public UsersAdapter(Context context, List<KeyObject> mDataset, FragmentManager fragmentManager)
    {
        this.mDataset = mDataset;
        this.context=context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public UsersHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_chat_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        UsersHolder vh = new UsersHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(UsersHolder holder, final int position)
    {
        holder.user.setText(mDataset.get(position).getKey());
        holder.user_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Device.token = mDataset.get(position).getKey();
          fragmentManager.beginTransaction().replace(R.id.admin_fragment, new GetChat()).addToBackStack("GetChat").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
