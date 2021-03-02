package com.shahid.aietest.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.shahid.aietest.utills.DateConverter;

import java.util.Date;


//Full search text to support uniqe task name
@Fts4
@Entity(tableName = "task")
@TypeConverters(DateConverter.class)
public class AIETask {

    @PrimaryKey
    @ColumnInfo(name = "rowid")
    public int rowid;

    @ColumnInfo(name = "task_name")
    public String taskName;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "dateCreated")
    public Date dateCreated;

    @ColumnInfo(name = "taskDate")
    public Date taskDate;
}


