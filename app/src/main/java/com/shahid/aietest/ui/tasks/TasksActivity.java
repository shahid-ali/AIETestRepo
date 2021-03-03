package com.shahid.aietest.ui.tasks;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.shahid.aietest.R;

public class TasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TasksFragment.newInstance())
                    .commitNow();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopService(new Intent(getBaseContext(), ConnectivityService.class));
    }
}