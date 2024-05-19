package com.example.mytasks;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    List<Task> tasks;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.textViewTitle.setText(task.getTitle());
        String statusText;
        if (task.isStatus()) {
            statusText = "انجام شده";
        } else {
            statusText = "در انتظار";
        }
        holder.textViewStatus.setText(statusText);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = holder.getLayoutPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Task task = tasks.get(position);
                    Intent intent = new Intent(v.getContext(), TaskDetailsActivity.class);
                    intent.putExtra("title", task.getTitle());
                    intent.putExtra("description", task.getDescription());
                    intent.putExtra("status", task.isStatus());
                    v.getContext().startActivity(intent);
                }

            }
        });


        holder.UpdateStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    task.setStatus(true);

                } else {
                    task.setStatus(false);

                }

                notifyDataSetChanged();
            }
        });

        holder.buttonDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int position = holder.getLayoutPosition();
                tasks.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, tasks.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void addTask(Task task) {
        tasks.add(task);
        notifyItemInserted(tasks.size() - 1);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        TextView textViewStatus;
        CheckBox UpdateStatus;
        Button buttonDeleteTask;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            UpdateStatus = itemView.findViewById(R.id.checkboxStatus);
            buttonDeleteTask = itemView.findViewById(R.id.buttonDeleteTask);
        }
    }
}
