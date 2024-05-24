package com.example.todo_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class Database_helper extends SQLiteOpenHelper {
    public static final String TASK_TABLE = "TASK";
    public static final String COL_ID = "ID";
    public static final String COL_TITLE = "TITLE";
    public static final String COL_DESCRIPTION = "DESCRIPTION";
    public static final String COL_STATUS = "STATUS";
    public static final String COL_DATE = "DATE";
    public static final String COL_TIME = "TIME";
    public Database_helper(@Nullable Context context) {
        super(context, "tasks.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_query = "CREATE TABLE " + TASK_TABLE + " ( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_TITLE + " TEXT, " + COL_DESCRIPTION + " TEXT ," + COL_STATUS + " BOOLEAN, " + COL_DATE + " DATE, " + COL_TIME + " TIME )";
        db.execSQL(create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE);
        onCreate(db);
    }

    public boolean addOne(Task_Model model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE,model.getTitle());
        cv.put(COL_DESCRIPTION,model.getDescription());
        cv.put(COL_STATUS,false);
        cv.put(COL_DATE,model.getDate());
        cv.put(COL_TIME,model.getTime());
        long insert = db.insert(TASK_TABLE, null, cv);
        return insert == -1;
    }

    public ArrayList<Task_Model> show(){
        ArrayList<Task_Model> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TASK_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                boolean status = cursor.getInt(3) == 1;
                String date = cursor.getString(4);
                String time = cursor.getString(5);
                Task_Model model = new Task_Model(id,title,description,status,date,time);
                taskList.add(model);
            } while(cursor.moveToNext());
            return taskList;
        } else {
            return null;
        }
    }

    public boolean delete(Task_Model model){
        SQLiteDatabase db = this.getWritableDatabase();
        String delete_query = "DELETE FROM "+ TASK_TABLE +" WHERE "+ COL_ID +" = "+ model.getId();
        Cursor cursor = db.rawQuery(delete_query, null);
        if(cursor.moveToFirst()){
            return true;
        } else {
            return false;
        }
    }

    public void update_task(Task_Model model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE,model.getTitle());
        cv.put(COL_DESCRIPTION,model.getDescription());
        cv.put(COL_DATE,model.getDate());
        cv.put(COL_TIME,model.getTime());

        //updating the title
        db.update(TASK_TABLE, cv, COL_ID + "=?", new String[]{String.valueOf(model.getId())});
        db.close();
    }

    public void updateStatus(int id, boolean status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_STATUS,status);
        db.update(TASK_TABLE, cv, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
