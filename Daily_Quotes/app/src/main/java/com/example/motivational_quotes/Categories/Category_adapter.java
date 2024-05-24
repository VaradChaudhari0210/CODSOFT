package com.example.motivational_quotes.Categories;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.motivational_quotes.Quotes.quote_dialog;
import com.example.motivational_quotes.R;


import java.util.ArrayList;
import java.util.List;

public class Category_adapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<String> Category_list;
    Category_model category_model = new Category_model();

    public Category_adapter(Context context) {
        this.context = context;
        this.Category_list = category_model.getCategories();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.textView.setText(Category_list.get(position));
    }

    @Override
    public int getItemCount() {
        return Category_list.size();
    }

    public void filterList(List<String> filteredlist) {
        Category_list = (ArrayList<String>) filteredlist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt_category_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, quote_dialog.class);
            intent.putExtra("category",textView.getText().toString());
            startActivity(context,intent,null);
        }
    }
}