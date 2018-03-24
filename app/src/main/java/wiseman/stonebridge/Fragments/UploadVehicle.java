package wiseman.stonebridge.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import wiseman.stonebridge.Globals.Globals;
import wiseman.stonebridge.Globals.Vehicle;
import wiseman.stonebridge.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Wiseman on 2018-01-27.
 */

public class UploadVehicle extends Fragment implements View.OnClickListener{
    View view;
    FrameLayout choose;
    EditText make,price,features;
    String car_make,car_price,car_features;
    ImageView car;
    Bitmap vehicle;
    Uri uri;
    LinearLayout upload;
    RequestQueue requestQueue;
    Bitmap bitmap;
    int id = 999999;
    String changed;
    ProgressDialog myProgressDialog,progress;
    private static final int RESULT_LOAD_IMAGE=1;
    public static  int COUNT_DOWN=1000;
    CountDownTimer countDownTimer;
    AppCompatImageView mImgCheck;
    AnimatedVectorDrawableCompat avd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_upload_vehicle,container,false);
        choose = (FrameLayout)view.findViewById(R.id.choose_vehicle_layout);
        choose.getBackground().setAlpha(150);
        getActivity().setTitle("Upload Vehicle");
        requestQueue = Volley.newRequestQueue(view.getContext());
        myProgressDialog = new ProgressDialog(view.getContext());
        progress = new ProgressDialog(view.getContext());

        make = (EditText)view.findViewById(R.id.vehicle_make);
        price = (EditText)view.findViewById(R.id.vehicle_price);
        features = (EditText)view.findViewById(R.id.vehicle_features);
        upload = (LinearLayout)view.findViewById(R.id.upload_button);
        car = (ImageView)view.findViewById(R.id.vehilce_photo);
        if(Globals.UPLOAD_OPERATION.equalsIgnoreCase("edit"))
        {

            make.setText(Vehicle.make);
            price.setText(Vehicle.price);
            features.setText(Vehicle.features);
            Picasso.with(view.getContext()).load(Vehicle.url).into(car);
            id=Vehicle.id;
            changed = "false";
        }

        choose.setOnClickListener(this);
        upload.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.choose_vehicle_layout:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,RESULT_LOAD_IMAGE);
            break;
            case R.id.upload_button:
                car_make = make.getText().toString();
                 car_price = price.getText().toString();
                car_features = features.getText().toString();
                if(!(car_make.isEmpty()||car_price.isEmpty()||car_features.isEmpty()))
                {
                    upload(car_make,car_price,car_features);
                }
                else
                {
                    Toast.makeText(view.getContext(),"All fields are required", Toast.LENGTH_LONG).show();
                }
            break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            Uri photo = data.getData();

            car.setDrawingCacheEnabled(true);
            car.buildDrawingCache();
            car.setImageURI(photo);
            bitmap = ((BitmapDrawable) car.getDrawable()).getBitmap();
            changed = "true";
        }
    }
    public void upload(final String car_make, final String car_price, final String car_features)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] imageBytes =  bytes.toByteArray();
        final String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        myProgressDialog.show();
        myProgressDialog.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
        StringRequest request = new StringRequest(Request.Method.POST, Globals.INSERT_VEHICLE_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if( Globals.GROUP_CREATION_SUCCESS.equalsIgnoreCase(response))
                        {
                            myProgressDialog.dismiss();

                            myProgressDialog.dismiss();
                            progress.show();
                            progress.setContentView(R.layout.success_layout);
                            mImgCheck = (AppCompatImageView)progress.findViewById(R.id.success_image);
                            avd = AnimatedVectorDrawableCompat.create(view.getContext(),R.drawable.animated_check);
                            // drawable = mImgCheck.getDrawable();
                            // mImgCheck.setImageDrawable(avd);
                            ((Animatable) mImgCheck.getDrawable()).start();
                            countDownTimer = new CountDownTimer(COUNT_DOWN,16) {
                                @Override
                                public void onTick(long l) {
                                }
                                @Override
                                public void onFinish(){
                                    mImgCheck.setVisibility(View.GONE);
                                    progress.dismiss();
                                }
                            };
                            countDownTimer.start();
                        }
                        else
                        {
                            myProgressDialog.dismiss();
                            Toast.makeText(view.getContext(),response, Toast.LENGTH_LONG).show();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("id",id+"");
                parameters.put("style_name",car_make);
                parameters.put("price",car_price);
                parameters.put("features",car_features);
                parameters.put("photo",encodedImage);
                parameters.put("changed",changed);
                return parameters;
            }
        };
        requestQueue.add(request);
    }
}
