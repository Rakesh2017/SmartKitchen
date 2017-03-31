package com.example.dell.smartkitchen;

import android.app.FragmentManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //animation on circles

    ImageView circle1;
    ImageView circle2;
    ImageView circle3;
    ImageView circle4;
    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;

    Animation circlesanimation;
    TextView userprofilenamehomepage;
    Animation slidedownanimation;
    DatabaseReference dparent  = FirebaseDatabase.getInstance().getReference();
    DatabaseReference refname = dparent.child("username");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        circle1=(ImageView)findViewById(R.id.circle1);
        circle2=(ImageView)findViewById(R.id.circle2);
        circle3=(ImageView)findViewById(R.id.circle3);
        circle4=(ImageView)findViewById(R.id.circle4);
        img1=(ImageView)findViewById(R.id.img1);
        img2=(ImageView)findViewById(R.id.img2);
        img3=(ImageView)findViewById(R.id.img3);
        img4=(ImageView)findViewById(R.id.img4);

        circlesanimation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotatecircles);
        slidedownanimation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slidedown);
        circle1.startAnimation(circlesanimation);
        circle2.startAnimation(circlesanimation);
        circle3.startAnimation(circlesanimation);
        circle4.startAnimation(circlesanimation);
        img1.startAnimation(slidedownanimation);
        img2.startAnimation(slidedownanimation);
        img3.startAnimation(slidedownanimation);
        img4.startAnimation(slidedownanimation);

        userprofilenamehomepage=(TextView)findViewById(R.id.userprofilenamehomepage);


        //fragment
        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Fragment fragment=new Fragment();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();





        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    protected void onStart(){
        super.onStart();

                  refname.addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {
                          String name = dataSnapshot.getValue(String.class);
                          userprofilenamehomepage.setText("Hi "+name);
                      }

                      @Override
                      public void onCancelled(DatabaseError databaseError) {

                      }
                  });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new UserProfile()).addToBackStack(null).commit();

        } else if (id == R.id.nav_calenadar) {

        } else if (id == R.id.nav_messages) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Messages()).addToBackStack(null).commit();


        } else if (id == R.id.nav_manual) {

        } else if (id == R.id.nav_logout) {

        } else if (id == R.id.nav_contact) {

        }
          else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
