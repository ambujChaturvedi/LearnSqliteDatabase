package com.example.user.learnsqlitedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.learnsqlitedatabase.adapter.ContactsAdapter;
import com.example.user.learnsqlitedatabase.model.Contacts;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
  EditText search;
  DatabaseHelper helper;
  RecyclerView searchRecyclerview;
  ArrayList<Contacts> searchList;
    private static final String TAG = "SearchActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search=findViewById(R.id.search_bar);
        searchRecyclerview=findViewById(R.id.recyclerviewSearch);



        // databaseHelper
        helper=new DatabaseHelper(this);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence query, int i, int i1, int i2) {

                String search_text=query.toString().toLowerCase();
                searchList=helper.getSearchList(search_text);
                Log.d(TAG, "onTextChanged: "+searchList);
                searchRecyclerview.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                ContactsAdapter adapter=new ContactsAdapter(SearchActivity.this,searchList);
                searchRecyclerview.setAdapter(adapter);
                adapter.notifyDataSetChanged();



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

}
