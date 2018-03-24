package wiseman.stonebridge.Services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import wiseman.stonebridge.Globals.Globals;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Wiseman on 2017-10-16.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService
{
    @Override
    public void onTokenRefresh()
    {
        String token = FirebaseInstanceId.getInstance().getToken();
        registerToken(token);
    }
    private void registerToken(String token)
    {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("Token",token).build();
        Request request = new Request.Builder().url(Globals.REGISTER_DEVICE_URL).post(body).build();
        try
        {
            client.newCall(request).execute();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
