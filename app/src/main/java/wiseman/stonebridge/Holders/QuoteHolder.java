package wiseman.stonebridge.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-02-26.
 */

public class QuoteHolder extends RecyclerView.ViewHolder {
    public TextView description,quantity,price;
    public QuoteHolder(View view)
    {
        super(view);
        description = (TextView)view.findViewById(R.id.text_desc);
        quantity = (TextView)view.findViewById(R.id.text_quantity);
        price = (TextView)view.findViewById(R.id.text_price);
    }
}
