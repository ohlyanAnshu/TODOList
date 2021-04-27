package com.example.todo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyTasks> myTasks;

    public TaskAdapter(Context context, ArrayList<MyTasks> myTasks) {
        this.context = context;
        this.myTasks = myTasks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.items_tasks, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.taskTitle.setText(myTasks.get(position).getTaskTitle());
        holder.desc.setText(myTasks.get(position).getDesc());
        holder.date.setText(myTasks.get(position).getDate());
        holder.time.setText(myTasks.get(position).getTime());

        final String getTaskTitle = myTasks.get(position).getTaskTitle();
        final String getDesc = myTasks.get(position).getDesc();
        final String getDate = myTasks.get(position).getDate();
        final String getTime = myTasks.get(position).getTime();
        final String getKey = myTasks.get(position).getTaskKey();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, editTask.class);
                intent.putExtra("taskTitle", getTaskTitle);
                intent.putExtra("desc", getDesc);
                intent.putExtra("date", getDate);
                intent.putExtra("time", getTime);
                intent.putExtra("taskKey", getKey);
                System.out.println(getTaskTitle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTasks.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView taskTitle, desc, date, time, taskKey;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            taskTitle = (TextView) itemView.findViewById(R.id.taskTitle);
            desc = (TextView) itemView.findViewById(R.id.desc);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }

}
