package com.example.motivational_quotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.motivational_quotes.Quotes.Quote_model;
import com.example.motivational_quotes.Quotes.Saved_quote_model;

import java.util.ArrayList;
import java.util.Arrays;

public class Database_helper extends SQLiteOpenHelper {
    public static final String COL_QUOTE = "QUOTE";
    public static final String QUOTES_TABLE = COL_QUOTE + "S";
    public static final String COL_AUTHOR = "AUTHOR";

    public Database_helper(@Nullable Context context) {
        super(context,"quotes.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_query = "CREATE TABLE " + QUOTES_TABLE + " ( "  + COL_QUOTE + " TEXT PRIMARY KEY, " + COL_AUTHOR + " TEXT )";
        db.execSQL(create_query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    //method to add a new quote
    public boolean addOne(Saved_quote_model model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_QUOTE, model.getQuote());
        cv.put(COL_AUTHOR, model.getAuthor());
        long result = db.insert(QUOTES_TABLE, null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //method to see duplicate entries of quotes
    public boolean isQuoteExists(String quote,String author) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Saved_quote_model> list = show();
        if (list != null) {
            for (Saved_quote_model model : list) {
                if (model.getQuote().equals(quote) && model.getAuthor().equals(author)) {
                    return true;
                }
            }
        }
        return false;
    }

    //method to show all the saved quotes
    public ArrayList<Saved_quote_model> show(){
        ArrayList<Saved_quote_model> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + QUOTES_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String quote = cursor.getString(0);
                String author = cursor.getString(1);
                Saved_quote_model model = new Saved_quote_model(quote, author);
                list.add(model);
            } while (cursor.moveToNext());
            return list;
        } else {
            return null;
        }
    }

    //method to delete a particular quote
    public boolean delete(Saved_quote_model model) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(QUOTES_TABLE, COL_QUOTE + "=?", new String[]{String.valueOf(model.getQuote())});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}