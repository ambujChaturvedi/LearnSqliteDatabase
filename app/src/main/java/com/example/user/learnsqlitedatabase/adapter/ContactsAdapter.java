package com.example.user.learnsqlitedatabase.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.learnsqlitedatabase.R;
import com.example.user.learnsqlitedatabase.model.Contacts;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by user on 24-02-2018.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsHolder> {



    Context ct;
    ArrayList<Contacts> contactsArrayList;

    public ContactsAdapter(Context ct, ArrayList<Contacts> contactsArrayList) {
        this.ct = ct;
        this.contactsArrayList = contactsArrayList;
    }


    @Override
    public ContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ct).inflate(R.layout.recyclerview_row,parent,false);
        return new ContactsHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsHolder holder, int position) {
        Contacts contacts=contactsArrayList.get(position);

        holder.textView.setText(contacts.getName());
        Bitmap bitmap=holder.ByteArrayToBitmap(contacts.getImageByte());
        holder.imageView.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return contactsArrayList.size();
    }





    //--------------------------------------------------------------------------------------------------------------------------------

    public  class ContactsHolder extends RecyclerView.ViewHolder{
        CircleImageView imageView;
        TextView textView;

        public ContactsHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.profile_image);
            textView=itemView.findViewById(R.id.name);
        }
        public Bitmap ByteArrayToBitmap(byte[] byteArray)
        {
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
            Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
            return bitmap;
        }
    }
}