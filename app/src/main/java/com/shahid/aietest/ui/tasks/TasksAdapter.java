package com.shahid.aietest.ui.tasks;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shahid.aietest.R;
import com.shahid.aietest.models.AIETask;
import com.shahid.aietest.utills.DateUtills;

import java.lang.ref.WeakReference;
import java.util.List;

;


public class TasksAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG=TasksAdapter.class.getName();
    Activity context;
    List<AIETask> tasks;
    DateUtills dateUtills;
    public WeakReference<TaskDelegate> taskDelegate;
    public TasksAdapter(Activity context, List<AIETask> tasks) {
        this.context = context;
        this.tasks = tasks;
        this.dateUtills=new DateUtills();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_list_item,parent, false);
        return new TasksAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((TasksAdapter.ViewHolder) viewHolder).setDetails(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout container;
        private TextView taskNameTV,dateCreatedTV;
        private ImageButton delBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container_aie);
            taskNameTV = itemView.findViewById(R.id.task_name);
            dateCreatedTV = itemView.findViewById(R.id.date_created);
            delBtn=itemView.findViewById(R.id.delete_btn);
        }

        private void setDetails(AIETask task)
        {
            taskNameTV.setText(task.taskName);

            String strDate = dateUtills.getDateFormat(task.dateCreated);
            dateCreatedTV.setText(strDate);


            container.setTag(R.id.container_aie,task.rowid);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(taskDelegate != null && taskDelegate.get() != null)
                    {
                        int rowId=(Integer) v.getTag(R.id.container_aie);
                        taskDelegate.get().updateItem(rowId);
                    }
                }
            });


            delBtn.setTag(R.id.delete_btn,task.rowid);
            delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(taskDelegate != null && taskDelegate.get() != null)
                    {
                        int rowId=(Integer) v.getTag(R.id.delete_btn);
                        taskDelegate.get().deleteItem(rowId);
                    }
                }
            });
        }
    }


}
