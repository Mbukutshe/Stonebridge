package wiseman.stonebridge.Fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import wiseman.stonebridge.Functions.Functions;
import wiseman.stonebridge.Functions.InsertToDatabase;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2017-10-18.
 */

public class NewGroup extends AppCompatActivity {
    TextView ok,back;
    Functions functions;
    RelativeLayout create_layout;
    InsertToDatabase insertToDatabase;
    EditText group_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_group_layout);
        functions = new Functions(getApplicationContext());
        create_layout = (RelativeLayout)findViewById(R.id.create_layout);
        create_layout.getBackground().setAlpha(150);
        group_name = (EditText)findViewById(R.id.group_name_text);
        ok = (TextView)findViewById(R.id.disclaimer_ok);
        back = (TextView)findViewById(R.id.close_new_group);
        ok.setOnClickListener(functions);
        back.setOnClickListener(functions);
        Spinner spinner = (Spinner)findViewById(R.id.group_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.users,R.layout.dropdown_items);
        adapter.setDropDownViewResource(R.layout.dropdown_items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                functions.passGroupViews(group_name,adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                functions.passGroupViews(group_name,adapterView.getItemAtPosition(0).toString());
            }
        });
    }
}
