package com.shahid.aietest.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.shahid.aietest.R;
import com.shahid.aietest.ui.fragments.TasksFragment;

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
}