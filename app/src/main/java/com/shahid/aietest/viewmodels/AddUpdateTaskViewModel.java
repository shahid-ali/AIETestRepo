package com.shahid.aietest.viewmodels;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shahid.aietest.AIEApplication;
import com.shahid.aietest.models.AIETask;
import com.shahid.aietest.models.TasksRepository;
import com.shahid.aietest.utills.DateUtills;

import java.util.Calendar;
import java.util.Date;

public class AddUpdateTaskViewModel extends AndroidViewModel {

    private static final  String TAG =  AddUpdateTaskViewModel.class.getName();

    private TasksRepository mRepository;
    private Calendar selectedCalendar;
    private int rowId=-10;
    private boolean isEditMode=false;


    public AddUpdateTaskViewModel (AIEApplication application) {
        super(application);
        mRepository = new TasksRepository(application);
        }


    //set rowid and return true if screen is opened in edit mode
    public boolean setRowId(int rowId)
    {
        this.rowId=rowId;
        if(rowId > 0)
        {
            isEditMode=true;
        }
        return isEditMode;
    }


    //get single task if in editmode
    public LiveData<AIETask> getTask()
    {
        return mRepository.getTask(rowId);
    }

    //check isEditMode and perform operation accordingly
    public void insertUpdateTask(String taskName) {

         Date taskTempDate=new Date();

        if (selectedCalendar == null) {
            taskTempDate = new DateUtills().getCalendar().getTime();
        } else {
            taskTempDate = selectedCalendar.getTime();
        }

        if(isEditMode)
        {
        mRepository.update(taskName,taskTempDate.getTime(),rowId);
        }
        else {
            AIETask task = new AIETask();
            task.taskName = taskName;
            task.dateCreated = new Date();
            task.taskDate=taskTempDate;
            task.userName="testUser";
            mRepository.insert(task);
        }
    }


    //set calendar if editmode
    public void setCalendar(Date date)
    {
        selectedCalendar=new DateUtills().getCalendar();
        selectedCalendar.setTime(date);
    }


    //set calendar from date picker
    public void setCalendar(int day,int month,int year)
    {
        selectedCalendar=new DateUtills().getCalendar();
        selectedCalendar.set(Calendar.YEAR, year);
        selectedCalendar.set(Calendar.MONTH, month);
        selectedCalendar.set(Calendar.DAY_OF_MONTH, day);
    }


    //get selected date to maintain user selection
    public String getSelectedDate()
    {
        if(selectedCalendar==null)
        {
            Calendar calendar=new DateUtills().getCalendar();
            Date date =  calendar.getTime();
            return new DateUtills().getDateFormat(date);
        }
        else
        {
            Date date =  selectedCalendar.getTime();
            return new DateUtills().getDateFormat(date);
        }
    }

}