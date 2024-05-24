package com.example.todo_list;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private EditText title,description;
    private FloatingActionButton fb;
    private Button addbtn,datebtn,timebtn;
    private RecyclerView rc_view;
    Database_helper helper;
    task_adapter adapter;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rc_view = findViewById(R.id.rv_tasks);
        helper = new Database_helper(MainActivity.this);
        //showing the message if there are no tasks in the database
        try{
            adapter = new task_adapter(MainActivity.this,helper);
            show_Task(helper,adapter);
        } catch (Exception e){
            Toast.makeText(MainActivity.this, "No Tasks Added", Toast.LENGTH_SHORT).show();
        }
        fb = findViewById(R.id.fb_add);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialog();
            }
        });
    }
    private void show_dialog() {
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_task_add);
        dialog.show();
        add_task();
    }
    private void add_task() {
        //date and time
        datebtn = dialog.findViewById(R.id.datebtn);
        timebtn = dialog.findViewById(R.id.timebtn);
        Date_Time_Picker date_time_picker = new Date_Time_Picker();
        date_time_picker.set_date_time(datebtn,timebtn,this);

        //add task
        title = dialog.findViewById(R.id.et_task_title);
        description = dialog.findViewById(R.id.et_task_desc);
        addbtn = dialog.findViewById(R.id.addbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task_Model model = null;
                //if condition to see if user has entered title and due date
                if(title.getText().toString().isEmpty() ||
                        datebtn.getText().toString().equalsIgnoreCase("DATE") ||
                        timebtn.getText().toString().equalsIgnoreCase("TIME")) {
                    Toast.makeText(MainActivity.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                } else {
                    model = new Task_Model(1, title.getText().toString(), description.getText().toString(), false, datebtn.getText().toString(), timebtn.getText().toString());
                    Toast.makeText(MainActivity.this, "Task Added Successfully", Toast.LENGTH_SHORT).show();
                }
                if(model!=null){
                    adapter = new task_adapter(MainActivity.this,helper);
                    dialog.dismiss();
                    helper.addOne(model);
                    show_Task(helper,adapter);
                }
            }
        });
    }
    public void show_Task(Database_helper helper,task_adapter adapter) {
        ArrayList<Task_Model> list;
        list = helper.show();
        if(list.isEmpty()){
            Toast.makeText(MainActivity.this, "No Tasks Added", Toast.LENGTH_SHORT).show();
        } else{
            rc_view.setAdapter(adapter);
            rc_view.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            adapter.setModel(list);
        }
    }
}