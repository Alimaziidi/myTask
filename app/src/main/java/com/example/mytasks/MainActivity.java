package com.example.mytasks;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fabAddTask;
    TaskAdapter taskAdapter;
    List<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fabAddTask = findViewById(R.id.fabAddTask);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("اضافه کردن وظیفه");

                View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_task, null);
                builder.setView(dialogView);

                EditText etTitle = dialogView.findViewById(R.id.etTitle);

                EditText etDescription = dialogView.findViewById(R.id.etDescription);


                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (!etTitle.getText().toString().isEmpty() && !etDescription.getText().toString().isEmpty()) {

                            String title = etTitle.getText().toString();
                            String description = etDescription.getText().toString();


                            Task newTask = new Task(title, description, false);


                            taskAdapter.addTask(newTask);
                        } else {

                            Toast.makeText(MainActivity.this, "لطفا مقدار خود را پر کنید!", Toast.LENGTH_SHORT).show();

                        }


                    }
                });


                builder.setNegativeButton("Cancel", null);


                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        saveListTasks();


    }

    private void saveListTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyTasks", Context.MODE_PRIVATE);


        String taskListJson = sharedPreferences.getString("TaskList", "");


        Gson gson = new Gson();
        Type taskListType = new TypeToken<List<Task>>() {}.getType();
        List<Task> taskList = gson.fromJson(taskListJson, taskListType);


        if (taskList != null) {
            this.taskList.addAll(taskList);
            taskAdapter.notifyDataSetChanged();
        }
    }

    protected void onPause() {
        super.onPause();


        SharedPreferences sharedPreferences = getSharedPreferences("MyTasks", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();




        Gson gson = new Gson();
        String taskListJson = gson.toJson(taskList);


        editor.putString("TaskList", taskListJson);
        editor.apply();
    }
}