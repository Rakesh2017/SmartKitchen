package com.example.dell.smartkitchen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.firebase.client.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Messages extends Fragment {
    DatabaseReference dparent = FirebaseDatabase.getInstance().getReference();

    DatabaseReference dref=dparent.child("messages");


    ListView listview;
    ArrayList<String> list=new ArrayList<>();
    ArrayAdapter<String> adapter;

    public Messages() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        listview=(ListView)view.findViewById(R.id.listview);
        adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,list);
        listview.setAdapter(adapter);
        dref= FirebaseDatabase.getInstance().getReference();



        return view;
    }

    public void onStart() {
        super.onStart();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("messages").child("A").getValue(String.class);
                list.add(name);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
