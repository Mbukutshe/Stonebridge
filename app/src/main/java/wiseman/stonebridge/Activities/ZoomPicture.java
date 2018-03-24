package wiseman.stonebridge.Activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-01-31.
 */

public class ZoomPicture extends AppCompatActivity {
    Bitmap image;
    ImageView zoom_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoom_layout);
        Bundle extras = getIntent().getExtras();
        zoom_image = (ImageView)findViewById(R.id.zoom_image);
        Picasso.with(this).load(extras.getString("vehicle")).into(zoom_image);
    }
}
