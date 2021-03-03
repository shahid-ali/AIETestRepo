package com.shahid.aietest.utills;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.shahid.aietest.AIEApplication;
import com.shahid.aietest.ui.addtask.AddUpdateTaskViewModel;
import com.shahid.aietest.ui.tasks.TasksViewModel;


public class ViewModelFactory implements ViewModelProvider.Factory{
    private AIEApplication application;

    public ViewModelFactory(AIEApplication application){
        this.application = application;
    }
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TasksViewModel.class)) {
            return (T) new TasksViewModel(application);
        }
        else if (modelClass.isAssignableFrom(AddUpdateTaskViewModel.class)) {
            return (T) new AddUpdateTaskViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}