package wiseman.stonebridge.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2017-10-16.
 */

public class GroupHolder extends RecyclerView.ViewHolder {
    public TextView group_name;
    public GroupHolder(View itemView)
    {
        super(itemView);
        group_name = (TextView)itemView.findViewById(R.id.group_name);
    }
}
