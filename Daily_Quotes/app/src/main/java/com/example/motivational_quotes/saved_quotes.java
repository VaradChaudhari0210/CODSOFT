package com.example.motivational_quotes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.motivational_quotes.Quotes.Quote_adapter;
import com.example.motivational_quotes.Quotes.Saved_quote_model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class saved_quotes extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton backbtn;
    ImageButton unsave,share;
    Quote_adapter adapter;
    Database_helper helper;
    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_quotes);

        //setting the Id's of all the elements
        unsave = findViewById(R.id.unsavebtn);
        share = findViewById(R.id.saved_sharebtn);
        recyclerView = findViewById(R.id.rv_saved_quotes);
        helper = new Database_helper(this);
        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //going to the Main_activity layout
                Intent intent = new Intent(saved_quotes.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //showing the message if there are no tasks in the database
        adapter = new Quote_adapter(this,helper);
        try{
            show_quotes(adapter, helper);
        } catch (Exception e){
            Toast.makeText(this, "No Quotes Found", Toast.LENGTH_SHORT).show();
        }
    }
    private void show_quotes(Quote_adapter adapter, Database_helper helper) {
        ArrayList<Saved_quote_model> list = new ArrayList<>();
        list = helper.show();
        if(list.isEmpty()){
            Toast.makeText(this, "No Quotes found", Toast.LENGTH_SHORT).show();
        } else {
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setList(list);
            adapter.notifyDataSetChanged();
        }
    }
}
