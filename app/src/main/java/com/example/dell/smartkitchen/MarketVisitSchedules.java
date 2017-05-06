package com.example.dell.smartkitchen;


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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


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
        Collections.reverse(list);
        listview.setAdapter(adapter);
        return view;


    }

    public void onStart(){
        super.onStart();

        dref.limitToLast(16).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (long i = dataSnapshot.getChildrenCount(); i >= 1; i--) {
                    String marketname = dataSnapshot.child("VisitDay" + i).child("MarketName").getValue(String.class);
                    String date = dataSnapshot.child("VisitDay" + i).child("date").getValue(String.class);
                    list.add(date + "    :    " + marketname);

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date d1 = new Date();
                    String todaydate = df.format(d1);


                    if (todaydate.equals(date)) {
                        int k=1;
                        if (k==1){
                            DispAlarm();
                            k=k+1;
                        }



                    }


                }
                Collections.reverse(list);
                adapter.notifyDataSetChanged();
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

        /*Intent intent = new Intent(getContext(), AlertWater.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

        mBuilder.setContentIntent(pendingIntent);*/



        mBuilder.setSmallIcon(R.drawable.gasleakagenoti);
        mBuilder.setContentTitle("Market Schedule");
        mBuilder.setContentText("Check application to see List");

        NotificationManager mNotificationManager =

                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());

    }


}
