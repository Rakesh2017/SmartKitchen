package com.example.dell.smartkitchen;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class Automation extends Fragment {

    Button button;
    Button button1;

    DatabaseReference dparent = FirebaseDatabase.getInstance().getReference();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    ImageView imageView;




    public Automation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_automation, container, false);
        button=(Button)view.findViewById(R.id.on);
        button1=(Button)view.findViewById(R.id.off);
        imageView=(ImageView)view.findViewById(R.id.bulbfromfirebase);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dparent.child("light").setValue(1);
                Toast.makeText(getContext(), "light on", Toast.LENGTH_SHORT).show();
                storageReference.child("bulbon.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.e("Tuts+", "uri: " + uri.toString());
                        Picasso.with(getContext()).load(uri.toString()).into(imageView);

                    }
                });
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dparent.child("light").setValue(0);
                Toast.makeText(getContext(), "light off", Toast.LENGTH_SHORT).show();
                storageReference.child("bulb.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.e("Tuts+", "uri: " + uri.toString());
                        Picasso.with(getContext()).load(uri.toString()).into(imageView);

                    }
                });

            }
        });

        return view;
    }

}
