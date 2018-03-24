package wiseman.stonebridge.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-01-29.
 */

public class SettingsHolder extends RecyclerView.ViewHolder{
    public TextView manufacturer;
    public SettingsHolder(View tv)
    {
        super(tv);
        manufacturer = (TextView)tv.findViewById(R.id.manufacturer);
    }
}
