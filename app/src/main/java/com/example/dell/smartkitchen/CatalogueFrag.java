package com.example.dell.smartkitchen;


import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    DatabaseReference dparent2 = FirebaseDatabase.getInstance().getReference();
    DatabaseReference cataloguesensordata = dparent2.child("CatalogueData").child("List");

    DatabaseReference dparent3 = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dref3 = dparent3.child("LpuHistoricalData");
    DatabaseReference dref4 = dref3.child("Ultra");
    int value=0;


    Button btn;
    TextView productname;
    TextView quantleft;
    TextView daysleft;
    String name;

    int pos;
    int box1sensordata=0;


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
        btn=(Button)view.findViewById(R.id.marketvisit);
        productname=(TextView)view.findViewById(R.id.productname);
        quantleft=(TextView)view.findViewById(R.id.quantleft);
        daysleft=(TextView)view.findViewById(R.id.daysleft);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new MarketCalendar()).addToBackStack(null).commit();
            }
        });

        clspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                disp();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return view;
    }

    public void onStart() {
        super.onStart();

        dref4.orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (com.google.firebase.database.DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    try {
                        box1sensordata = dataSnapshot1.child("value").getValue(Integer.class);
                        Toast.makeText(getContext(), box1sensordata, Toast.LENGTH_SHORT).show();
                        int sensordata = box1sensordata;
                        if (sensordata > 0 && sensordata <= 3) {
                            quantleft.setText("80%");
                            daysleft.setText("25 days");
                        }

                        if (sensordata > 3 && sensordata <= 8) {
                            quantleft.setText("50%");
                            daysleft.setText("15 days");
                        }
                        if (sensordata > 8 && sensordata <= 12) {
                            quantleft.setText("20%");
                            daysleft.setText("10 days");
                        }
                        if (sensordata > 12 && sensordata <= 15) {
                            quantleft.setText("7%");
                            daysleft.setText("2 days");
                            DispAlarm();
                        }

                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



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
    public void disp() {
        super.onStart();
        cataloguesensordata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (pos == 0) {
                    name = dataSnapshot.child("Box1").child("ProductName").getValue(String.class);
                    productname.setText(name);
                    //   box1sensordata = dataSnapshot.child("Box1").child("SensorValue").getValue(Integer.class);

                } else if (pos >= 1) {
                    int poss = pos + 1;
                    name = dataSnapshot.child("Box" + poss).child("ProductName").getValue(String.class);
                    productname.setText(name);
                    Toast.makeText(getContext(), "Only 1 Box is installed!!, Showing Sensors values of 1st Box", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void DispAlarm(){
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getContext(), notification);
        r.play();

        Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(1000);



        //notification page
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getContext());

//Create the intent that’ll fire when the user taps the notification//

        Intent intent = new Intent(getContext(), AlertWater.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

        mBuilder.setContentIntent(pendingIntent);

        mBuilder.setSmallIcon(R.drawable.gasleakagenoti);
        mBuilder.setContentTitle("Sugar Content critically low");
        mBuilder.setContentText("Do not forget to buy sugar on your next market visit");

        NotificationManager mNotificationManager =

                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());

    }

}






