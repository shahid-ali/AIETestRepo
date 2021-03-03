package com.shahid.aietest.ui.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shahid.aietest.AIEApplication;
import com.shahid.aietest.R;
import com.shahid.aietest.models.AIETask;
import com.shahid.aietest.ui.addtask.AddUpdateTaskActivity;
import com.shahid.aietest.utills.ViewModelFactory;

import java.lang.ref.WeakReference;
import java.util.List;

public class TasksFragment extends Fragment implements TaskDelegate {

    private final static String TAG=TasksFragment.class.getName();

    private TasksViewModel mViewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton addTaskBtn;

    public static TasksFragment newInstance() {
        return new TasksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tasks_fragment, container, false);
        recyclerView=view.findViewById(R.id.recyclerview);
        addTaskBtn=view.findViewById(R.id.add_task_fab);
        setListener();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewModelFactory viewModelFactory = new ViewModelFactory((AIEApplication)getActivity().getApplication());

        mViewModel = new ViewModelProvider(this,viewModelFactory).get(TasksViewModel.class);

        mViewModel.getAllTasks().observe(getViewLifecycleOwner(), new Observer<List<AIETask>>() {
            @Override
            public void onChanged(List<AIETask> aieTasks) {
                setupAdapter(aieTasks);
            }
        });
    }

    private void setupAdapter(List<AIETask> aieTasks)
    {
        Log.i(TAG,"setupAdapter "+aieTasks.size());

        TasksAdapter tasksAdapter=new TasksAdapter(getActivity(),aieTasks);
        tasksAdapter.taskDelegate=new WeakReference<TaskDelegate>(this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(tasksAdapter);
    }

    private void setListener()
    {
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addTaskIntent=new Intent(getActivity(), AddUpdateTaskActivity.class);
                getActivity().startActivity(addTaskIntent);
            }
        });
    }

    /********* TaskDelegate *******/
    public  void updateItem(int rowid)
    {
        Intent addTaskIntent=new Intent(getActivity(), AddUpdateTaskActivity.class);
        addTaskIntent.putExtra(getString(R.string.row_id),rowid);
        getActivity().startActivity(addTaskIntent);
    }

    public void  deleteItem(int rowId)
    {
        mViewModel.deleteTask(rowId);
    }

}