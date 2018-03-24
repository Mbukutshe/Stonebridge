package wiseman.stonebridge.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import wiseman.stonebridge.Adapters.AdminAdapter;
import wiseman.stonebridge.Globals.Access;
import wiseman.stonebridge.Objects.Dashboard;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-01-29.
 */

public class AdministratorDashboard extends AppCompatActivity implements View.OnClickListener{
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    List<Dashboard> names;
    android.support.v4.app.FragmentManager fragmentManager;
    ActionBar actionBar;
    Fragment currentFragment;
    LinearLayout public_group,employee_group,admins_group,core_groups;
    AppCompatCheckBox public_box,employee_box,admins_box;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_fragment_layout);
        getSupportActionBar().setTitle("Administrator");
        getSupportActionBar().setIcon(R.drawable.admin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        core_groups = (LinearLayout)findViewById(R.id.core_groups);
        public_group = (LinearLayout)findViewById(R.id.public_group);
        employee_group = (LinearLayout)findViewById(R.id.employee_group);
        admins_group = (LinearLayout)findViewById(R.id.administrators_group);
        public_group.setOnClickListener(this);
        employee_group.setOnClickListener(this);
        admins_group.setOnClickListener(this);

        public_box = (AppCompatCheckBox)findViewById(R.id.public_box);
        employee_box = (AppCompatCheckBox)findViewById(R.id.employee_box);
        admins_box = (AppCompatCheckBox)findViewById(R.id.admin_box);

        mRecyclerView = (RecyclerView)findViewById(R.id.admin_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        names = getAdmin();
        fragmentManager = getSupportFragmentManager();
        actionBar = getSupportActionBar();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, mLayoutManager.getOrientation()));
        AdminAdapter adapter = new AdminAdapter(this,names,mRecyclerView,mLayoutManager,fragmentManager,actionBar,core_groups);
        mRecyclerView.setAdapter(adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_options, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.options_sign_out) {
            Access.granted = false;
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id)
        {
            case R.id.public_group:
                if(public_box.isChecked())
                {
                    public_box.setChecked(false);
                }
                else
                {
                    public_box.setChecked(true);
                }

            break;
            case R.id.employee_group:
                if(employee_box.isChecked())
                {
                    employee_box.setChecked(false);
                }
                else
                {
                    employee_box.setChecked(true);
                }
            break;
            case R.id.administrators_group:
                if(admins_box.isChecked())
                {
                    admins_box.setChecked(false);
                }
                else
                {
                    admins_box.setChecked(true);
                }
            break;
        }
    }

    private List<Dashboard> getAdmin()
    {
        List<Dashboard> names = new ArrayList<>();
        names.add(new Dashboard("Send Messages",R.drawable.send));
        names.add(new Dashboard("Chats",R.drawable.chat));
        names.add(new Dashboard("Upload",R.drawable.uploadvehicle));
        names.add(new Dashboard("New Group",R.drawable.newgroup));
        names.add(new Dashboard("Quotation",R.drawable.quotation));
        names.add(new Dashboard("Locate Employees",R.drawable.locate));
        names.add(new Dashboard("Edit or Delete",R.drawable.edit));
        return names;
    }

    @Override
    public void onBackPressed() {
        String title =  getSupportActionBar().getTitle().toString();
        if(title.equalsIgnoreCase("Perform Operations"))
        {
            core_groups.setVisibility(View.GONE);
            getSupportActionBar().setTitle("Administrators");
        }
        else
        {
            getSupportActionBar().setTitle("Administrators");
            core_groups.setVisibility(View.VISIBLE);
        }

        super.onBackPressed();
    }
}
