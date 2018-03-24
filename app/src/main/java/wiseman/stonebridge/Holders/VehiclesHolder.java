package wiseman.stonebridge.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import wiseman.stonebridge.R;
import wiseman.stonebridge.Views.Triangle;

/**
 * Created by Wiseman on 2018-01-25.
 */

public class VehiclesHolder  extends RecyclerView.ViewHolder{
    public TextView messageId,message,subject,link,author,date,urgent,video_duration,attach,filename;
    RelativeLayout card;
    public ImageView video_thumbnail;
    public FrameLayout play,duration;
    public LinearLayout description;
    public Triangle video_footer;
    public VehiclesHolder(View tv) {
        super(tv);
        video_thumbnail = (ImageView)tv.findViewById(R.id.video_thumbnail);
        message = (TextView)tv.findViewById(R.id.message_message);
        author = (TextView)tv.findViewById(R.id.author_message);
        date = (TextView)tv.findViewById(R.id.time_message);
        messageId = (TextView)tv.findViewById(R.id.messageid);
        urgent = (TextView)tv.findViewById(R.id.urgent);
        subject =(TextView)tv.findViewById(R.id.subject_message);
        card = (RelativeLayout) tv.findViewById(R.id.card_view_message);
        link = (TextView)tv.findViewById(R.id.link);
        filename = (TextView)tv.findViewById(R.id.filename);
        play = (FrameLayout)tv.findViewById(R.id.play_video_layout);
        duration = (FrameLayout)tv.findViewById(R.id.video_duration_layout);
        video_duration = (TextView)tv.findViewById(R.id.video_duration);
        video_footer = tv.findViewById(R.id.video_footer_layout);
        description = (LinearLayout)tv.findViewById(R.id.title_message);
    }
}
