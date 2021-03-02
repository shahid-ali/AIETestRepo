package com.shahid.aietest.viewmodels;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shahid.aietest.AIEApplication;
import com.shahid.aietest.models.AIETask;
import com.shahid.aietest.models.TasksRepository;

import java.util.List;


public class TasksViewModel extends AndroidViewModel {

    private TasksRepository mRepository;

    private final LiveData<List<AIETask>> mAllTasks;



    public TasksViewModel (AIEApplication application) {
        super(application);
        mRepository = new TasksRepository(application);
        mAllTasks = mRepository.getAllTasks();
    }

    //fetching all the tasks from local db
    public LiveData<List<AIETask>> getAllTasks() { return mAllTasks; }

    //delete task with given rowid
    public void deleteTask(int rowId) { mRepository.delete(rowId); }


}