package wiseman.stonebridge.Functions;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import wiseman.stonebridge.Activities.MainActivity;
import wiseman.stonebridge.Fragments.Documents;
import wiseman.stonebridge.Fragments.Gallery;
import wiseman.stonebridge.Fragments.Groups;
import wiseman.stonebridge.Fragments.Messages;
import wiseman.stonebridge.Fragments.Vehicles;
import wiseman.stonebridge.Globals.Globals;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2017-10-12.
 */

public class Functions implements View.OnClickListener,TabLayout.OnTabSelectedListener,View.OnKeyListener{
    Context context;
    FragmentManager fragmentManager;
    static CardView group_options;
    InsertToDatabase insertToDatabase;
    EditText group_name,text_message;
    String group_users;
    static RequestQueue requestQueue;
    public Functions(Context context)
    {
        this.context = context;
    }
    public void setFragmentManager(FragmentManager fragmentManager)
    {
        this.fragmentManager = fragmentManager;
    }
    public void setDoneAction(EditText editText)
    {
        editText.setImeOptions(EditorInfo.IME_ACTION_SEND);
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
    }
    public void passGroupViews(EditText group_name, String group_users)
    {
        this.group_name = group_name;
        this.group_users = group_users;
    }
    public void messageEditext(EditText text_message)
    {
        this.text_message = text_message;
    }
    @Override
    public void onClick(View view)
    {
        Visible visible = new Visible();
        Animation animation = new Animation(context);
        int id = view.getId();
        switch(id)
        {
            case R.id.btn_login:
                context.startActivity(new Intent(context, MainActivity.class));
             break;
            case R.id.open_groups:
                fragmentManager.beginTransaction().replace(R.id.content_main,new Groups()).commit();
            break;
            case R.id.disclaimer_ok:
                animation.setAlphaAnimation(view);
                insertToDatabase = new InsertToDatabase(view.getContext());
                insertToDatabase.createGroup(group_name,group_users);
            break;
            case R.id.close_new_group:
                animation.setAlphaAnimation(view);
            break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab)
    {
        switch (tab.getPosition())
        {
            case 0:
                fragmentManager.beginTransaction().replace(R.id.content_main,new Vehicles()).commit();
                break;
            case 1:
                fragmentManager.beginTransaction().replace(R.id.content_main,new Messages()).commit();

                break;
            case 2:
                fragmentManager.beginTransaction().replace(R.id.content_main,new Documents()).commit();

                break;
            case 3:
                fragmentManager.beginTransaction().replace(R.id.content_main,new Gallery()).commit();
                break;
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab)
    {

        switch (tab.getPosition())
        {
            case 0:
                fragmentManager.beginTransaction().replace(R.id.content_main,new Vehicles()).commit();
                break;
            case 1:
                fragmentManager.beginTransaction().replace(R.id.content_main,new Messages()).commit();

                break;
            case 2:
                fragmentManager.beginTransaction().replace(R.id.content_main,new Documents()).commit();

                break;
            case 3:
                fragmentManager.beginTransaction().replace(R.id.content_main,new Gallery()).commit();
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onKey(final View view, int i, KeyEvent keyEvent) {
        if(i == keyEvent.KEYCODE_ENTER){
           if(view.getId()==R.id.text_message)
           {
               if(!text_message.getText().toString().isEmpty())
               {
                   Date dt = new Date();
                   int hours = dt.getHours();
                   int minutes = dt.getMinutes();
                   String time = hours + ":" + minutes;
                   requestQueue = Volley.newRequestQueue(context);
                   StringRequest request = new StringRequest(Request.Method.POST, Globals.INSERT_POST_URL,

                           new Response.Listener<String>() {
                               @Override
                               public void onResponse(String response) {
                                   Toast.makeText(context,response, Toast.LENGTH_LONG).show();
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
                           parameters.put("subject","");
                           parameters.put("type","");
                           parameters.put("attachment","no");
                           parameters.put("username","Wiseman");
                           parameters.put("message",text_message.getText().toString());
                           parameters.put("urgent","no");
                           return parameters;
                       }
                   };
                   request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                   requestQueue.add(request);
               }
               else
               {
                   Toast.makeText(context,"Write something", Toast.LENGTH_LONG).show();
               }

           }
            return true;
        }
      return false;
    }
}
