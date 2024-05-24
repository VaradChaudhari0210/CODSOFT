package com.example.motivational_quotes.Quotes;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.motivational_quotes.Database_helper;
import com.example.motivational_quotes.R;

import java.util.ArrayList;

public class Quote_adapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Saved_quote_model> list;
    private Database_helper helper;

    public Quote_adapter(Context context, Database_helper helper) {
        this.context = context;
        this.helper = helper;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_item,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Saved_quote_model model = this.list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.quote.setText(model.getQuote());
        viewHolder.author.setText(model.getAuthor());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<Saved_quote_model> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView quote,author;
        private ImageButton unsave,share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quote = itemView.findViewById(R.id.saved_quote);
            author = itemView.findViewById(R.id.saved_author);
            unsave = itemView.findViewById(R.id.unsavebtn);
            share = itemView.findViewById(R.id.saved_sharebtn);

            unsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Saved_quote_model model = Quote_adapter.this.list.get(getAdapterPosition());
                    helper.getWritableDatabase();
                    helper.delete(model);
                    list.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    Toast.makeText(context, "Quote Removed successfully", Toast.LENGTH_SHORT).show();
                }
            });
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    Saved_quote_model model = Quote_adapter.this.list.get(getAdapterPosition());
                    clipboardManager.setText("Quote : "+model.getQuote()+"\nAuthor : "+model.getAuthor());
                    Toast.makeText(context, "Quote Copied to clipboard", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
