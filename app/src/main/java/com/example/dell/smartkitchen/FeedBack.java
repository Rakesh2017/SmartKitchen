package com.example.dell.smartkitchen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedBack extends Fragment {

    TextView textView;
    Button button;

    DatabaseReference refparent = FirebaseDatabase.getInstance().getReference().child("feedback");
    DatabaseReference refchild = refparent.child("a");



    public FeedBack() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_feed_back, container, false);

        textView=(TextView)view.findViewById(R.id.feedbacktext);
        button=(Button)view.findViewById(R.id.feedbackbtn);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                refchild.setValue(textView.getText().toString().trim());
                Toast.makeText(getContext(), "Response Submitted", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
