package com.shahid.aietest.models;

import androidx.lifecycle.LiveData;

import com.shahid.aietest.AIEApplication;

import java.util.List;


public class TasksRepository {

    private  TaskDOA taskDOA;
    private LiveData<List<AIETask>> allTasks;


    public TasksRepository(AIEApplication application) {
        TaskDatabase db = TaskDatabase.getDatabase(application);
        taskDOA = db.taskDOA();
        allTasks = taskDOA.getAllTasks();
    }

    //get All Tasks stored in DB
    public LiveData<List<AIETask>> getAllTasks() {
        return allTasks;
    }

    //get single task in DB
    public LiveData<AIETask> getTask(int rowid) {
        return taskDOA.getTask(rowid);
    }

    //update task with updated name and taskDate
    public  void update(String taskName, Long taskDate, int rowId) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDOA.update(taskName,taskDate,rowId);
        });
    }


    //delte task with given rowId
    public  void delete(int id) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDOA.deleteByRowId(id);
        });
    }


    //insert newley added task into db
    public void insert(AIETask aieTask) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDOA.insert(aieTask);
        });
    }

    //search for existing task name to prevent duplicate task name
    public LiveData<List<AIETask>> searchFor(String searchFor)
    {
        return taskDOA.searchFor(searchFor);
    }
}
