package com.example.dell.smartkitchen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistorywaterFrag extends Fragment {


    DatabaseReference dparent = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dref=dparent.child("messages");

    ListView listview;
    ArrayList<String> list=new ArrayList<>();

    ArrayAdapter<String> adapter;

    public HistorywaterFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_historywater, container, false);


        listview=(ListView)view.findViewById(R.id.listviewhistorywater);
        adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,list);
        listview.setAdapter(adapter);


        return view;
    }

    public void onStart() {
        super.onStart();


        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                for (com.google.firebase.database.DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String name = dataSnapshot1.getValue(String.class);
                    list.add(name);
                    adapter.notifyDataSetChanged();
                }


                // String name = dataSnapshot.child("messages").child("A").getValue(String.class);
                //list.add(name);
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
