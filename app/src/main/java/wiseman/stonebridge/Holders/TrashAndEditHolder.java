package wiseman.stonebridge.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-02-12.
 */

public class TrashAndEditHolder extends RecyclerView.ViewHolder {
    public ImageView picture;
    public TextView name;
    public FrameLayout edit,delete;
    public TrashAndEditHolder (View tv)
    {
        super(tv);
        picture = (ImageView)tv.findViewById(R.id.picture);
        name = (TextView)tv.findViewById(R.id.name);
        edit = (FrameLayout)tv.findViewById(R.id.edit);
        delete =(FrameLayout)tv.findViewById(R.id.delete);
    }
}
