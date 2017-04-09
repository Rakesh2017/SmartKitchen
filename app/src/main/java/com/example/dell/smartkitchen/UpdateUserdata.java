package com.example.dell.smartkitchen;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import static android.app.Activity.RESULT_OK;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateUserdata extends Fragment {


    //private ImageView imageView;
    private Button uploadButton;
    EditText username;
    EditText usercontact;
    EditText usermail;
    private static final int GALLERY_INTENT = 2;
    private Button mSelctImage;
    private Button uploadimage;
    private StorageReference mStorage;
    private StorageReference mStorage1;
    private ProgressDialog mProgress;

    private Button mUploadbtn;
    private final static int CAMERA_REQUEST_CODE = 1;



    DatabaseReference dparent = FirebaseDatabase.getInstance().getReference();
    DatabaseReference refname = dparent.child("username");
    DatabaseReference refcontact = dparent.child("usercontact");
    DatabaseReference refmail = dparent.child("usermail");
    Button submitbutton;


    public UpdateUserdata() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_userdata, container, false);
        mStorage = FirebaseStorage.getInstance().getReference();
        mStorage1 = FirebaseStorage.getInstance().getReference();
        mSelctImage = (Button) view.findViewById(R.id.uploadbutton);
        mUploadbtn = (Button) view.findViewById(R.id.uploadbuttoncamera);
        mProgress = new ProgressDialog(getContext());



        username = (EditText) view.findViewById(R.id.username);
        usercontact = (EditText) view.findViewById(R.id.usercontact);
        usermail = (EditText) view.findViewById(R.id.usermail);
        submitbutton = (Button) view.findViewById(R.id.submitbtn);
        // Firebase.setAndroidContext(getContext());
        mSelctImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);

            }
        });

        mUploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);


            }

            });





        return view;
    }


    public void onStart() {
        super.onStart();

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refname.setValue(username.getText().toString().trim());
                refcontact.setValue(usercontact.getText().toString().trim());
                refmail.setValue(usermail.getText().toString().trim());

                Toast.makeText(getContext(), "profile updated", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void onActivityResult(final int requestCode, int resultCode,final Intent data) {

        super.onActivityResult(requestCode,resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            mProgress.setMessage("uploading...");
            mProgress.show();
            Uri uri = data.getData();
            StorageReference filepath1 = mStorage.child("ProfilePhotos").child("userimage");
            filepath1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Upload Success", Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();


                }
            });
        }

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            mProgress.setMessage("uploading...");
            mProgress.show();
            Uri uri = data.getData();


            StorageReference filepath = mStorage1.child("ProfilePhotos").child("userimage");
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Upload Success", Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();


                }
            });



        }


    }
}

