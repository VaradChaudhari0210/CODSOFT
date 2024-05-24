package com.example.motivational_quotes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import android.widget.Toast;

import com.example.motivational_quotes.Categories.Category_adapter;
import com.example.motivational_quotes.Categories.Category_model;


import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    Button favbtn;
    private SearchView searchView;
    private RecyclerView rv_category;
    private Category_adapter category_adapter;
    Category_model category_model = new Category_model();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search_view);
        rv_category = findViewById(R.id.rv_categories);
        favbtn = findViewById(R.id.favbtn);

        favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, saved_quotes.class);
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<String> filteredlist = new ArrayList<>();
                for (String item : category_model.getCategories()) {
                    if (item.toLowerCase().contains(newText.toLowerCase())) {
                        filteredlist.add(item);
                    }
                }
                if (filteredlist.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    category_adapter.filterList(filteredlist);
                }
                return false;
            }
        });
        category_adapter = new Category_adapter(MainActivity.this);
        rv_category.setAdapter(category_adapter);
        rv_category.setLayoutManager(new GridLayoutManager(this, 2));
        rv_category.setHasFixedSize(true);
    }
}