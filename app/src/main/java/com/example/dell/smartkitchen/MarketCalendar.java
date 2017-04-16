package com.example.dell.smartkitchen;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarketCalendar extends Fragment {

    MaterialCalendarView calendar;
    String calendardate;
    String todaydate;
    Button submit;
    Button schedules;
    EditText et1;
    Date d,d1;


    DatabaseReference dparent = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dmarketvisitdata = dparent.child("MarketVisitData");
    long num;

    public MarketCalendar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_market_calendar, container, false);
        calendar = (MaterialCalendarView)view.findViewById(R.id.marketcalendar);
        submit=(Button)view.findViewById(R.id.submitmarketview);
        schedules=(Button)view.findViewById(R.id.allschedules);
        et1=(EditText)view.findViewById(R.id.etmarketname);

        schedules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,new MarketVisitSchedules()).addToBackStack(null).commit();
            }
        });


        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                d = date.getDate();
                calendardate = df.format(d);

                d1 = new Date();
                todaydate = df.format(d1);

                Toast.makeText(getActivity(),"Date selected : "+d, Toast.LENGTH_SHORT).show();


                if(!isConnectedToInternet())
                {

                    Toast.makeText(getActivity(),  " No internet Connection ", Toast.LENGTH_SHORT).show();
                }

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!d.after(d1)){
                            Toast.makeText(getActivity()," OOPS!! you can't go back in time, select other day", Toast.LENGTH_LONG).show();

                        }
                        else{
                            long updatednum = num+1;
                            dmarketvisitdata.child("VisitDay"+updatednum).child("date").setValue(calendardate);
                            dmarketvisitdata.child("VisitDay"+updatednum).child("MarketName").setValue(et1.getText().toString().trim());
                            Toast.makeText(getActivity(), "You are going to visit : "+et1.getText().toString().trim()+"  on  "+calendardate, Toast.LENGTH_LONG).show();

                        }


                    }
                });

            }
        });



        return view;
    }

    public void onStart(){
        super.onStart();
        dmarketvisitdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              num = dataSnapshot.getChildrenCount();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }




    public boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++)

                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

            }
        }
        return false;
    }

}
