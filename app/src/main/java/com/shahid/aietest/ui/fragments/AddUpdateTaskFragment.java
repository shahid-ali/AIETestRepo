package com.shahid.aietest.ui.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.shahid.aietest.AIEApplication;
import com.shahid.aietest.R;
import com.shahid.aietest.models.AIETask;
import com.shahid.aietest.utills.DateUtills;
import com.shahid.aietest.viewmodels.AddUpdateTaskViewModel;
import com.shahid.aietest.viewmodels.ViewModelFactory;

import java.util.Calendar;


public class AddUpdateTaskFragment extends Fragment {

    private static final String TAG=AddUpdateTaskFragment.class.getName();

    private AddUpdateTaskViewModel mViewModel;
    private EditText taskNameET,taskDateET;
    private Button addUpdateTaskButton;
    private int rowId=-10;  // defualt value is negative which means now value is passed from previous code

    public static AddUpdateTaskFragment newInstance(int rowId) {
        return new AddUpdateTaskFragment(rowId);
    }

    private AddUpdateTaskFragment(int rowId)
    {
        this.rowId=rowId;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.add_update_fragment, container, false);
        taskNameET=view.findViewById(R.id.task_name_et);
        taskDateET=view.findViewById(R.id.task_date_et);

        addUpdateTaskButton=view.findViewById(R.id.add_task_button);
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //init viewmodel
        ViewModelFactory viewModelFactory = new ViewModelFactory((AIEApplication)getActivity().getApplication());
        mViewModel = new ViewModelProvider(this,viewModelFactory).get(AddUpdateTaskViewModel.class);


        addListener();
        addObservers();
    }


    //add observer for single task data when in edit mode
    private  void  addObservers()
    {
        if(mViewModel.setRowId(rowId))
        {
            mViewModel.getTask().observe(getViewLifecycleOwner(), new Observer<AIETask>() {
                @Override
                public void onChanged(AIETask aieTask) {

                    taskNameET.setText(aieTask.taskName);
                    taskDateET.setText(new DateUtills().getDateFormat(aieTask.taskDate));
                    mViewModel.setCalendar(aieTask.taskDate);
                }
            });
        }
    }


    //add listeners for task name edittext & date picker
    private void addListener()
    {
        addUpdateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.insertUpdateTask(taskNameET.getText().toString());
                getActivity().finish();
            }
        });



        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mViewModel.setCalendar(dayOfMonth,monthOfYear,year);
               taskDateET.setText(mViewModel.getSelectedDate());
            }
        };

        taskDateET.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar selectedCalendar=mViewModel.getCurrentCalendar();
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), dateListener, selectedCalendar
                        .get(Calendar.YEAR), selectedCalendar.get(Calendar.MONTH),
                        selectedCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(mViewModel.getCurrentCalendar().getTimeInMillis());
                datePickerDialog.show();
            }
        });
    }
}