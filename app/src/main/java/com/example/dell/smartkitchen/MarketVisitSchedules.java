package com.example.dell.smartkitchen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarketVisitSchedules extends Fragment {

    ListView listview;
    ArrayList<String> list=new ArrayList<>();
    ArrayAdapter<String> adapter;

    DatabaseReference dparent = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dref=dparent.child("MarketVisitData");


    public MarketVisitSchedules() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_market_visit_schedules, container, false);

        listview=(ListView)view.findViewById(R.id.schedulelistview);
        adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,list);
        listview.setAdapter(adapter);
        return view;


    }

    public void onStart(){
        super.onStart();

        dref.limitToLast(16).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(long i=dataSnapshot.getChildrenCount() ; i>=1 ;i--)
                {
                    String marketname = dataSnapshot.child("VisitDay"+i).child("MarketName").getValue(String.class);
                    String date = dataSnapshot.child("VisitDay"+i).child("date").getValue(String.class);
                    list.add(date + "    :    " + marketname);


                }
                Collections.reverse(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
