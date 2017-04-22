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
public class Historicaldata extends Fragment {

    Button btn1;
    Button btn2;
    Animation animation1;
    Animation animation2;


    public Historicaldata() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_historicaldata, container, false);


        btn1=(Button)view.findViewById(R.id.historybtn1);
        btn2=(Button)view.findViewById(R.id.historybtn2);
        animation1= AnimationUtils.loadAnimation(getContext(), R.anim.reallranimate);
        animation2= AnimationUtils.loadAnimation(getContext(),R.anim.realrlanimate);

        btn1.startAnimation(animation2);
        btn2.startAnimation(animation1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistorySmokeFrag()).addToBackStack(null).commit();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistorywaterFrag()).addToBackStack(null).commit();

            }
        });



        return  view;
    }

}
