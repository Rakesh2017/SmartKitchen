package com.example.dell.smartkitchen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class RealTimeData extends Fragment {

    Button btn1;
    Button btn2;
    Button btn3;

    Animation animation1;
    Animation animation2;


    public RealTimeData() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_real_time_data, container, false);

        btn1=(Button)view.findViewById(R.id.realbtn1);
        btn2=(Button)view.findViewById(R.id.realbtn2);
        btn3=(Button)view.findViewById(R.id.realbtn3);

        animation1= AnimationUtils.loadAnimation(getContext(),R.anim.reallranimate);
        animation2= AnimationUtils.loadAnimation(getContext(),R.anim.realrlanimate);

        btn1.startAnimation(animation2);
        btn2.startAnimation(animation1);
        btn3.startAnimation(animation2);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new CatalogueFrag()).addToBackStack(null).commit();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,new SmokeSensorFrag()).addToBackStack(null).commit();

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,new WaterSensorFrag()).addToBackStack(null).commit();

            }
        });

        return view;
    }

}
