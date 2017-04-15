package com.example.dell.smartkitchen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogueFrag extends Fragment {

    TextView cataloguenumber;
    Animation animation1;
    Animation animation2;
    DatabaseReference dparent = FirebaseDatabase.getInstance().getReference();
    DatabaseReference cataloguenumberfirebase = dparent.child("CatalogueData").child("List");

    Spinner clspinner;
    DatabaseReference dparent1 = FirebaseDatabase.getInstance().getReference();
    DatabaseReference cataloguelistfirebase = dparent1.child("CatalogueData").child("List");




    public CatalogueFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_catalogue, container, false);
        animation1= AnimationUtils.loadAnimation(getContext(), R.anim.realrlanimate);
        animation2= AnimationUtils.loadAnimation(getContext(),R.anim.reallranimate);
        cataloguenumber=(TextView)view.findViewById(R.id.cl2);
        clspinner=(Spinner)view.findViewById(R.id.clspinner);




        return view;
    }

    public void onStart() {
        super.onStart();

        cataloguenumberfirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long number = dataSnapshot.getChildrenCount();
                cataloguenumber.setText("" + number);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        cataloguelistfirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> areas = new ArrayList<String>();

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("ProductName").getValue(String.class);
                    areas.add(areaName);
                }

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                clspinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }



}
