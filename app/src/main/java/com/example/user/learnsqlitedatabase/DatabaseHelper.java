package com.example.user.learnsqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.user.learnsqlitedatabase.model.Contacts;

import java.util.ArrayList;

/**
 * Created by user on 22-02-2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    final static String dataBaseName="images.db";
    final static String TABLE_NAME="Contacts";
    public static final String IMAGE_ID = "id";
    public static final String IMAGE = "image";


    private static final String CREATE_IMAGES_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + IMAGE + " BLOB NOT NULL,"+"NAME TEXT);";
    private String SearchColumnname="SearchColumnname";


    //  constructor

    public DatabaseHelper(Context context) {

        super(context, dataBaseName, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //// we create our table in this method
        sqLiteDatabase.execSQL(CREATE_IMAGES_TABLE);




    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public boolean addData(String name,byte[] img){
        //  getting the database

     SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("image",img);
        contentValues.put("name",name);

        long results=database.insert(TABLE_NAME,null,contentValues);
        if (results==-1){
            return false;
        }
        else
            return true;

    }

    public ArrayList<Contacts> getAllData(){
        ArrayList<Contacts> allContacts=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"image","Name"};
        Log.d(TAG, "getAllData: "+columns);
        Cursor cursor=db.query(TABLE_NAME,columns,
                null,null,null,null,null);
        if (cursor!=null && cursor.moveToNext()){
            do {
                Contacts contacts=new Contacts();
                contacts.setImageByte(cursor.getBlob(cursor.getColumnIndex("image")));
                contacts.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                allContacts.add(contacts);
            }
            while (cursor.moveToNext());
        }

        return allContacts;
    }

    public ArrayList<Contacts> getSearchList(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<Contacts> searchList=new ArrayList<>();

        try {
//            Cursor mCursor=db.query(true,TABLE_NAME,new String[]{"NAME"},SearchColumnname + "='" + name
//                    + "'",null,null,null,null,null,null);
//            Log.d(TAG, "getSearchList: Cursor"+mCursor.toString());

            String sql="SELECT * FROM "+TABLE_NAME+" WHERE "+"NAME"+" LIKE '%"+name+"%'";
            Cursor mCursor=db.rawQuery(sql,null);

        if (mCursor != null ) {
            Log.d(TAG, "getSearchList: cursor is not null");
            mCursor.moveToFirst();
            for (int i = 0; i < mCursor.getCount(); i++) {
                Contacts contacts=new Contacts();
                contacts.setImageByte(mCursor.getBlob(mCursor.getColumnIndex("image")));
                contacts.setName(mCursor.getString(mCursor.getColumnIndex("NAME")));
                searchList.add(contacts);
                mCursor.moveToNext();
            }
            mCursor.close();
            return searchList;
        }
        return searchList;
    } catch (Exception e) {
        e.printStackTrace();
    }

        return searchList;


    }











}
