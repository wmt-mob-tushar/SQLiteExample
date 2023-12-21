package com.example.sqliteexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqliteexample.Adapter.RecycleTodoAdapter;
import com.example.sqliteexample.Models.TodoModel;
import com.example.sqliteexample.database.MyDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<TodoModel> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    FloatingActionButton btnAddContact;
    RecycleTodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recycle view declaration
        recyclerView = (RecyclerView) findViewById(R.id.rv_todolist);

        //float btn to add new contact and create dilog to add new contact
        btnAddContact = findViewById(R.id.btn_float_dialog);

        btnAddContact.setOnClickListener(v -> {

            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.add_update_dialog);

            EditText etTitle = dialog.findViewById(R.id.et_title);
            EditText etDescription = dialog.findViewById(R.id.et_description);
            Button btnAdd = dialog.findViewById(R.id.btn_add_update);

            btnAdd.setOnClickListener(v1 -> {

                String title = "",description = "";

                if(etTitle.getText().toString().isEmpty()){
                    Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
                }else {
                    title= etTitle.getText().toString();
                }

                if(etDescription.getText().toString().isEmpty()){
                    Toast.makeText(this, "Please enter number", Toast.LENGTH_SHORT).show();
                }else {
                    description = etDescription.getText().toString();
                }

                if(!title.isEmpty() && !description.isEmpty()){
                    MyDbHelper database = new MyDbHelper(this);
                    database.insertData(title,description);
                    arrayList = database.getAllData();
                    adapter = new RecycleTodoAdapter(this,arrayList);
                    recyclerView.setAdapter(adapter);
                    dialog.dismiss();
                }
            });

            dialog.show();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));// define layout of recycle views

        //get data from database
        MyDbHelper myDbHelper = new MyDbHelper(this);
        arrayList = myDbHelper.getAllData();

        adapter = new RecycleTodoAdapter(this,arrayList);
        recyclerView.setAdapter(adapter);

    }
}