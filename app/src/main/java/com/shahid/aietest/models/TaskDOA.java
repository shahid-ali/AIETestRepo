package com.shahid.aietest.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDOA {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(AIETask task);

    @Query("DELETE FROM task WHERE rowid = :rowid")
    abstract void deleteByRowId(long rowid);

    @Query("UPDATE task SET task_name = :taskName, taskDate = :taskDate  WHERE rowid = :rowid")
    void update(String taskName,Long taskDate, int rowid);

    @Query("SELECT rowid,task_name,user_name,dateCreated,taskDate FROM task ORDER BY taskDate DESC")
    LiveData<List<AIETask>> getAllTasks();

    @Query("SELECT rowid,task_name,user_name,dateCreated,taskDate FROM task WHERE rowid= :rowid")
    LiveData<AIETask> getTask(int rowid);


    @Query("SELECT rowid,task_name,user_name,dateCreated,taskDate FROM task WHERE task_name  MATCH :searchFor")
    LiveData<List<AIETask>> searchFor(String searchFor);
}
