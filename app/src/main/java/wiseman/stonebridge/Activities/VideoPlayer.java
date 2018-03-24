package wiseman.stonebridge.Activities;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Wiseman on 2018-03-21.
 */

public class VideoPlayer extends AppCompatActivity {
    MediaSessionCompat mediaSessionCompat;
    PlaybackStateCompat.Builder stateBuilder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ComponentName mediaButtonReceiver = new ComponentName(getApplicationContext(),MediaButtonReceiver.class);
        mediaSessionCompat = new MediaSessionCompat(this,"Tag",mediaButtonReceiver,null);
        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        mediaButtonIntent.setClass(this, MediaButtonReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,mediaButtonIntent,0);
        mediaSessionCompat.setMediaButtonReceiver(pendingIntent);
        stateBuilder = new PlaybackStateCompat.Builder().setActions(PlaybackStateCompat.ACTION_PLAY| PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSessionCompat.setPlaybackState(stateBuilder.build());
        MediaControllerCompat mediaControllerCompat = new MediaControllerCompat(this,mediaSessionCompat);
        MediaControllerCompat.setMediaController(this,mediaControllerCompat);
    }
}
