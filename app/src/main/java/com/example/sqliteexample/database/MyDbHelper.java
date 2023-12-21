package com.example.sqliteexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqliteexample.Models.TodoModel;

import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "TodoDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "TodoTable";
//    private static final String KEY_ID = "ID";
//    private static final String KEY_TITLE = "TITLE";
//    private static final String KEY_DESCRIPTION = "DESCRIPTION";

    public MyDbHelper(@Nullable Context context) {
        super(context, DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, DESCRIPTION TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
    }

    //    insert data in database
    public void insertData(String title, String description){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("TITLE",title);
        cv.put("DESCRIPTION",description);

        database.insert(TABLE_NAME,null,cv);

//        String query = "INSERT INTO "+TABLE_NAME+" (TITLE,DESCRIPTION) VALUES ('"+title+"','"+description+"')";
//        database.execSQL(query);
    }

    //    update data in database
    public void updateData(String title, String description, int id){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("TITLE",title);
        cv.put("DESCRIPTION",description);

        database.update(TABLE_NAME,cv,"ID = ?",new String[]{String.valueOf(id)});

//        String query = "UPDATE "+TABLE_NAME+" SET TITLE = '"+title+"', DESCRIPTION = '"+description+"' WHERE ID = "+id;
//        database.execSQL(query);
    }

    //    delete data from database
    public void deleteData(int id){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        database.delete(TABLE_NAME,"ID = ?",new String[]{String.valueOf(id)});
//        String query = "DELETE FROM "+TABLE_NAME+" WHERE ID = "+id;
//        database.execSQL(query);
    }

    //  get all data from database
    public ArrayList<TodoModel> getAllData(){

        SQLiteDatabase database = this.getReadableDatabase();

        ArrayList<TodoModel> arrayList = new ArrayList<>();

        String query = "SELECT * FROM "+TABLE_NAME;

        //this cursor will point to the table
        Cursor cursor = database.rawQuery(query,null);

        //this loop will run until the last row of the table
        while(cursor.moveToNext()){
            TodoModel todoModel = new TodoModel();
            todoModel.id = cursor.getInt(0);
            todoModel.title = cursor.getString(1);
            todoModel.description = cursor.getString(2);

            arrayList.add(todoModel);
        }

        return arrayList;
    }
}

