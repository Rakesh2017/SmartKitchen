package com.example.dell.smartkitchen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.Visualizer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    //login
    TabHost tabHost;
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;

    //signup
    private EditText inputEmail1, inputPassword1;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar1;

    ImageButton imageButton;

    //shared Preferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Animation animation;
    ImageButton imageButton1;

    EditText smartkeyet;
    String smartkeyvalue;
    DatabaseReference smartref=FirebaseDatabase.getInstance().getReference();
    DatabaseReference smartref1=smartref.child("smartkey");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageButton=(ImageButton)findViewById(R.id.rot);
        imageButton1=(ImageButton)findViewById(R.id.rot1);

        animation=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink);
        imageButton1.startAnimation(animation);

        sharedPreferences=getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        smartkeyet=(EditText)findViewById(R.id.smartkey);

        //tab widget
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tab1 = tabHost.newTabSpec("A");
        tab1.setIndicator("Login");
        tab1.setContent(R.id.tab1);
        TabHost.TabSpec tab2 = tabHost.newTabSpec("B");
        tab2.setIndicator("Sign-up");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

        //login
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        //register
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail1 = (EditText) findViewById(R.id.email1);
        inputPassword1 = (EditText) findViewById(R.id.password1);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        //shared Preferences data
        String emailname=sharedPreferences.getString("A","");
        inputEmail.setText(emailname);

        String passwordname=sharedPreferences.getString("B","");
        inputPassword.setText(passwordname);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();



      /*  btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });*/





        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else if(!smartkeyet.getText().toString().trim().equals(smartkeyvalue)){
                                        Toast.makeText(MainActivity.this, "incorrect Smart-key ! Account creation denied...", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    //email is stored in Shared Preferences
                                    editor.putString("A",inputEmail.getText().toString()).commit();
                                    editor.putString("B",inputPassword.getText().toString()).commit();
                                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                    startActivity(intent);
                                    Toast.makeText(MainActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
            }
        });


        //register

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email1 = inputEmail1.getText().toString().trim();
                String password1 = inputPassword1.getText().toString().trim();
                String smartkey1= smartkeyet.getText().toString().trim();

                if (TextUtils.isEmpty(email1)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password1)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password1.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(smartkey1)) {
                    Toast.makeText(getApplicationContext(), "Enter Smart Key!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!smartkey1.equals(smartkeyvalue)) {
                    Toast.makeText(getApplicationContext(),"incorrect Smart-key ! Account creation denied...", Toast.LENGTH_LONG).show();
                    return;
                }

                progressBar1.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email1, password1)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(getApplicationContext(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Successfully Registered! You can login now." ,
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });
    }

    protected void onStart(){
        super.onStart();

        smartref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                smartkeyvalue=dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        progressBar1.setVisibility(View.GONE);
    }
}



