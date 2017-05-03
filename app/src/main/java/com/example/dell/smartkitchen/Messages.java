package com.example.dell.smartkitchen;


import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.firebase.client.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Messages extends Fragment {
    DatabaseReference dparent = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dref=dparent.child("messages");

    DatabaseReference dparent1 = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dref1=dparent1.child("messages");

    long count=1;



    ListView listview;
    ArrayList<String> list=new ArrayList<>();

    ArrayAdapter<String> adapter;




    public Messages() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        listview=(ListView)view.findViewById(R.id.listview);
        adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,list);
        Collections.reverse(list);
        listview.setAdapter(adapter);





        return view;
    }

    public void onStart() {
        super.onStart();

        dref.limitToLast(8).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                for (com.google.firebase.database.DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String name = dataSnapshot1.getValue(String.class);
                    list.add(name);
                }


                Collections.reverse(list);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void onResume(){
        super.onResume();
        adapter.clear();
    }




}
