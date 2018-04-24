package com.example.user.learnsqlitedatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user.learnsqlitedatabase.adapter.ContactsAdapter;
import com.example.user.learnsqlitedatabase.model.Contacts;

import java.util.ArrayList;

public class ShowDatabase extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Contacts> allContacts;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_viewdatabase);
        recyclerView=findViewById(R.id.recyclerview);
        databaseHelper=new DatabaseHelper(this);

        allContacts=databaseHelper.getAllData();
        ContactsAdapter contactsAdapter=new ContactsAdapter(this,allContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contactsAdapter);


    }
}
