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
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnalyticsData extends Fragment {



    DatabaseReference dparent = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dref = dparent.child("LpuHistoricalData");
    DatabaseReference dref1 = dref.child("Smoke");

    ArrayList<Integer> list = new ArrayList<>();


    int data;
    int x1=0,y1=0;

    Date d1,d2,d3;

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
        list.add(10);





        return view;
    }
public void onStart(){
    super.onStart();

    dref1.limitToLast(1).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String x="";
                try{
                    y1= dataSnapshot1.child("value").getValue(Integer.class);
                    list.add(y1);


                }
                catch (Exception e){

                }
                //Toast.makeText(getContext(), y1, Toast.LENGTH_SHORT).show();
                Calendar calendar = Calendar.getInstance();
                d1 = calendar.getTime();
                calendar.add(Calendar.DATE, 1);
                d2 = calendar.getTime();
                calendar.add(Calendar.DATE, 1);
                d3 = calendar.getTime();


            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                    new DataPoint(d1 , y1),
                    new DataPoint(d1 , y1+100),
                   // new DataPoint(d1 , y1+100),
                    //new DataPoint(d1 , y1-300),
                   /* new DataPoint(list.get(1), list.get(1)),
                    new DataPoint(list.get(2), list.get(2)),
                    new DataPoint(list.get(3), list.get(3)),
                    new DataPoint(list.get(4), list.get(4)),
                    new DataPoint(list.get(5), list.get(5)),
                    new DataPoint(list.get(6), list.get(6)),
                    new DataPoint(list.get(7), list.get(7)),
                    new DataPoint(list.get(8), list.get(8)),*/

            });


            graph.addSeries(series);
            graph.setTitle("Smoke Sensor");
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setYAxisBoundsManual(true);

            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
            graph.getGridLabelRenderer().setNumHorizontalLabels(4);
           // graph.getGridLabelRenderer().setNumHorizontalLabels(9);
            graph.getGridLabelRenderer().setNumVerticalLabels(10);
            graph.getViewport().setMinY(250);
            graph.getViewport().setMaxY(700);
            graph.getViewport().setMinX(d1.getTime());
            graph.getViewport().setMaxX(d3.getTime());
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getGridLabelRenderer().setHumanRounding(true);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

}


}
