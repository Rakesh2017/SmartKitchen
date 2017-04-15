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
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogueFrag extends Fragment {

    TextView cataloguenumber;
    Animation animation1;
    Animation animation2;
    DatabaseReference dparent = FirebaseDatabase.getInstance().getReference();
    DatabaseReference cataloguenumberfirebase = dparent.child("CatalogueData").child("List");


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
        cataloguenumber=(TextView)view.findViewById(R.id.cl1);
        cataloguenumber.startAnimation(animation1);




        return view;
    }

}
