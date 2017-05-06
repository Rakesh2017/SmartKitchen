package com.example.dell.smartkitchen;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.awareness.snapshot.internal.Snapshot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;

import android.graphics.Color;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnalyticsData extends Fragment {



    DatabaseReference dparent = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dref = dparent.child("LpuHistoricalData");
    DatabaseReference dref1 = dref.child("Smoke");

    int data;
    int x1=0,y1=0;

    GraphView graph;

    public AnalyticsData() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analytics_data, container, false);
        graph = (GraphView) view.findViewById(R.id.graph);






        return view;
    }
public void onStart(){
    super.onStart();

    dref1.limitToLast(10).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String x="";
                try{
                    x = dataSnapshot1.child("time").getValue(String.class);
                    x1=Integer.parseInt(x);
                    y1= dataSnapshot1.child("value").getValue(Integer.class);


                }
                catch (Exception e){

                }


            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                    new DataPoint(x1, y1),
                    new DataPoint(x1+1, y1+1),
                    new DataPoint(x1+1, y1+1),
                    new DataPoint(x1+1, y1+1),
                    new DataPoint(x1+1, y1+1),
                    new DataPoint(x1+1, y1+1),


            });
            graph.addSeries(series);
            graph.getGridLabelRenderer().setNumHorizontalLabels(2);
            graph.getGridLabelRenderer().setNumVerticalLabels(3);

            graph.getViewport().setMinY(0);
            graph.getViewport().setMaxY(5);
            graph.getViewport().setXAxisBoundsManual(true);


            graph.getGridLabelRenderer().setHumanRounding(false);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

}


}
