package com.shahid.aietest.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.shahid.aietest.R;
import com.shahid.aietest.ui.fragments.AddUpdateTaskFragment;

public class AddUpdateTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_update_task_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {

            //negative number is to keep track if no values being passed from previous screen or calling code
            int rowId=getIntent().getIntExtra(getString(R.string.row_id),-10);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AddUpdateTaskFragment.newInstance(rowId))
                    .commitNow();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }
}