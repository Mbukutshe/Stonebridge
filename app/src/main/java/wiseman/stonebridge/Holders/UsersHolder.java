package wiseman.stonebridge.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-02-09.
 */

public class UsersHolder extends RecyclerView.ViewHolder {
    public TextView user;
    public LinearLayout user_item;
    public UsersHolder(View tv)
    {
        super(tv);
        user = (TextView)tv.findViewById(R.id.user_name);
        user_item = (LinearLayout)tv.findViewById(R.id.user_chat_item);
    }

}
