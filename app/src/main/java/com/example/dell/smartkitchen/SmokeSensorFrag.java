package com.example.dell.smartkitchen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class SmokeSensorFrag extends Fragment {

    TextView status1;
    TextView status2;
    TextView realtimedata1;
    TextView realtimedata2;
    TextView alarm1;
    TextView alarm2;
    TextView fan1;
    TextView fan2;
    TextView leak1;
    TextView leak2;
    TextView head;
    TextView condition1;
    TextView condition2;

    Button button1;
    Button button2;

    Animation animation1;
    Animation animation2;
    Animation animation3;




    public SmokeSensorFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_smoke_sensor, container, false);
        animation1= AnimationUtils.loadAnimation(getContext(),R.anim.realrlanimate);
        animation2= AnimationUtils.loadAnimation(getContext(),R.anim.reallranimate);
        animation3= AnimationUtils.loadAnimation(getContext(),R.anim.blink);

        button1=(Button)view.findViewById(R.id.ssb1);
        button1.startAnimation(animation2);
        button2=(Button)view.findViewById(R.id.ssb2);
        button2.startAnimation(animation1);

        status1=(TextView)view.findViewById(R.id.ss1);
        status2=(TextView)view.findViewById(R.id.ss2);

        realtimedata1=(TextView)view.findViewById(R.id.ss3);
        realtimedata2=(TextView)view.findViewById(R.id.ss4);

        alarm1=(TextView)view.findViewById(R.id.ss5);
        alarm2=(TextView)view.findViewById(R.id.ss6);

        fan1=(TextView)view.findViewById(R.id.ss7);
        fan2=(TextView)view.findViewById(R.id.ss8);

        leak1=(TextView)view.findViewById(R.id.ss9);
        leak2=(TextView)view.findViewById(R.id.ss10);

        condition1=(TextView)view.findViewById(R.id.ss11);
        condition2=(TextView)view.findViewById(R.id.ss12);

        head=(TextView)view.findViewById(R.id.smokesensorheading);
       // head.startAnimation(animation3);

        status1.startAnimation(animation1);
        status2.startAnimation(animation1);
        realtimedata1.startAnimation(animation2);
        realtimedata2.startAnimation(animation2);
        alarm1.startAnimation(animation1);
        alarm2.startAnimation(animation1);
        fan1.startAnimation(animation2);
        fan2.startAnimation(animation2);
        leak1.startAnimation(animation1);
        leak2.startAnimation(animation1);
        condition1.startAnimation(animation2);
        condition2.startAnimation(animation1);





        return view;
    }

}
