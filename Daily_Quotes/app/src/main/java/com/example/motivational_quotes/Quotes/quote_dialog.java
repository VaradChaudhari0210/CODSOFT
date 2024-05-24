package com.example.motivational_quotes.Quotes;

import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.motivational_quotes.Database_helper;
import com.example.motivational_quotes.MainActivity;
import com.example.motivational_quotes.R;
import com.example.motivational_quotes.quotes_api.Quote_response_listener;
import com.example.motivational_quotes.quotes_api.Request_manager;



import java.util.List;
import java.util.Random;

public class quote_dialog extends AppCompatActivity {

    Request_manager manager;
    ProgressDialog dialog;
    TextView quote, author;
    ImageButton backbtn,savebtn,sharebtn;
    String category;
    Database_helper helper;
    Saved_quote_model model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quote_dialog);

        quote = findViewById(R.id.txt_quote);
        author = findViewById(R.id.txt_author);
        backbtn = findViewById(R.id.backtbn);
        helper = new Database_helper(this);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(quote_dialog.this, MainActivity.class);
                startActivity(intent);
            }
        });

        savebtn = findViewById(R.id.savebtn);
        sharebtn = findViewById(R.id.sharebtn);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sav_quote = quote.getText().toString();
                String sav_author = author.getText().toString();
                if(!helper.isQuoteExists(sav_quote,sav_author)){
                    model = new Saved_quote_model(quote.getText().toString(),author.getText().toString());
                    helper.addOne(model);
                    Toast.makeText(quote_dialog.this, "Quote Saved Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(quote_dialog.this, "Quote Already Saved", Toast.LENGTH_SHORT).show();
                }

            }
        });

        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) quote_dialog.this.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setText("Quote : "+quote.getText().toString()+"\nAuthor : "+author.getText().toString());
                Toast.makeText(quote_dialog.this, "Quote Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        category = getIntent().getStringExtra("category");

        manager = new Request_manager(this,category); // or update the constructor
        manager.GetAllQuotes(listener);//need to update to add category
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.show();

    }



    private final Quote_response_listener listener = new Quote_response_listener() {
        @Override
        public void didfetch(List<Quote_model> responses, String msg) {
            showData(responses);
            dialog.dismiss();
        }
        @Override
        public void diderror(String msg) {
            dialog.dismiss();
            Toast.makeText(quote_dialog.this, msg, Toast.LENGTH_SHORT).show();
        }
    };
    private void showData(List<Quote_model> responses) {
        Random random = new Random();
        int index = random.nextInt(responses.size());
        quote.setText(responses.get(index).getQuote());
        author.setText(responses.get(index).getAuthor());
    }
}
