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


/**
 * A simple {@link Fragment} subclass.
 */
public class WaterSensorFrag extends Fragment {

    TextView status1;
    TextView status2;
    TextView realtimedata1;
    TextView realtimedata2;
    TextView condition1;
    TextView condition2;
    Button button1;
    Button button2;

    Animation animation1;
    Animation animation2;

    public WaterSensorFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_water_sensor, container, false);

        animation1= AnimationUtils.loadAnimation(getContext(), R.anim.realrlanimate);
        animation2= AnimationUtils.loadAnimation(getContext(),R.anim.reallranimate);

        button1=(Button)view.findViewById(R.id.wsb1);
        button1.startAnimation(animation2);
        button2=(Button)view.findViewById(R.id.wsb2);
        button2.startAnimation(animation1);

        status1=(TextView)view.findViewById(R.id.ws1);
        status2=(TextView)view.findViewById(R.id.ws2);

        realtimedata1=(TextView)view.findViewById(R.id.ws3);
        realtimedata2=(TextView)view.findViewById(R.id.ws4);

        condition1=(TextView)view.findViewById(R.id.ws5);
        condition2=(TextView)view.findViewById(R.id.ws6);

        status1.startAnimation(animation1);
        status2.startAnimation(animation1);
        realtimedata1.startAnimation(animation2);
        realtimedata2.startAnimation(animation2);

        condition1.startAnimation(animation2);
        condition2.startAnimation(animation1);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Historicaldata()).addToBackStack(null).commit();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new WaterWork()).addToBackStack(null).commit();
            }
        });

        return view;
    }

}
