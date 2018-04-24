package com.example.user.learnsqlitedatabase;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 1;
    ImageView pfofile_image;
    Button save_btn;
    Button view_dataBse_btn;
    private final static int REQUEST_CODE_Gallery = 1;
    Uri imageuri;
    EditText name;
    Bitmap convertedBitmap;
    DatabaseHelper databaseHelper;
    Button search_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: fired");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init
        pfofile_image = findViewById(R.id.image);
        save_btn = findViewById(R.id.save_btn);
        view_dataBse_btn = findViewById(R.id.view_btn);
        name=findViewById(R.id.name);
        databaseHelper=new DatabaseHelper(this);
        search_btn=findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        pfofile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkdevicePermission();
            }


        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name=name.getText().toString();

                  byte[] imgByte=imageViewtoByte(convertedBitmap);
                saveImageToDatabase(Name,imgByte);
            }
        });
        view_dataBse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ShowDatabase.class);
                startActivity(intent);
            }
        });
    }


    public void checkdevicePermission() {
        Log.d(TAG, "checkdevicePermission: startd");
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            }

        } else {
            Log.d(TAG, "checkdevicePermission: we already  have the permission");
            callGalleryforImage();
        }


    }

    public void callGalleryforImage() {
        Log.d(TAG, "callGalleryforImage: Calling gallery for image");
        Intent startGallery = new Intent(Intent.ACTION_PICK);
        startGallery.setType("image/*");
        startActivityForResult(startGallery, REQUEST_CODE_Gallery);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: started" + requestCode);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: Permission Granted:");
                callGalleryforImage();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: started" + data.toString());
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_Gallery && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult:after result code " + requestCode);

            imageuri = data.getData();
            Log.d(TAG, "onActivityResult: Got the Image Uri" + imageuri);
            Toast.makeText(this, "We Got the image from Gallery", Toast.LENGTH_SHORT).show();

            // changing to byte array with input stream
            try {
                InputStream inputStream=getContentResolver().openInputStream(imageuri);
                convertedBitmap= BitmapFactory.decodeStream(inputStream);
                pfofile_image.setImageBitmap(convertedBitmap);


            }
            catch (FileNotFoundException e){
                Log.d(TAG, "onActivityResult: "+e.getMessage());

            }




        }


    }

  public void saveImageToDatabase(String name,byte[] img){

       boolean insertData= databaseHelper.addData(name,img);
       if(insertData){
           Toast.makeText(this, "Data Got Inserted Into the Database Sucessfully", Toast.LENGTH_SHORT).show();
       }
       else{
           Toast.makeText(this, "Data Insertion Failed" , Toast.LENGTH_SHORT).show();
       }


  }


    private byte[] imageViewtoByte(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }



    //-------------------------------------------------------------------------------------------------------------------------------------------------------
}
