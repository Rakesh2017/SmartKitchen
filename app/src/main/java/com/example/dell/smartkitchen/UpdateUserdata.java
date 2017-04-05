package com.example.dell.smartkitchen;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import static android.app.Activity.RESULT_OK;
import com.firebase.client.Config;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import javax.xml.transform.Result;




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
    private ProgressDialog mProgress;




    // creating an instance of Firebase Storage
   // FirebaseStorage storage = FirebaseStorage.getInstance();
    //creating a storage reference. Replace the below URL with your Firebase storage URL.
    //StorageReference storageRef = storage.getReferenceFromUrl("gs://smart-kitchen-29de5.appspot.com");


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
        View view =  inflater.inflate(R.layout.fragment_update_userdata, container, false);
        mStorage=FirebaseStorage.getInstance().getReference();
        mSelctImage=(Button)view.findViewById(R.id.uploadbutton);
        mProgress= new ProgressDialog(getContext());

        //Firebase.setAndroidContext(getContext());
        //getting the reference of the views
      //  imageView = (ImageView)view.findViewById(R.id.imageview);
       // uploadButton = (Button) view.findViewById(R.id.uploadbutton);

        username=(EditText)view.findViewById(R.id.username);
        usercontact=(EditText)view.findViewById(R.id.usercontact);
        usermail=(EditText)view.findViewById(R.id.usermail);
        submitbutton=(Button)view.findViewById(R.id.submitbtn);
       // Firebase.setAndroidContext(getContext());
         mSelctImage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                 intent.setType("image/*");
                 startActivityForResult(intent, GALLERY_INTENT);

             }
         });


       //onImageViewClick(); // for selecting an Image from gallery.
       //onUploadButtonClick(); // for uploading the image to Firebase Storage.





        return view;
    }

    public void onStart()
    {
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




    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            mProgress.setMessage("uploading...");
            mProgress.show();
            Uri uri = data.getData();
            StorageReference filepath = mStorage.child("ProfilePhotos").child("userimage");
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Upload Success", Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                }
            });


        }
    }




   /* protected  void onImageViewClick(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                //i.setAction(Intent.ACTION_GET_CONTENT);
                i.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(i, "Select Picture"),SELECT_PICTURE );
            }
        });

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode==RESULT_OK){
            if(requestCode==SELECT_PICTURE){
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i("IMAGE PATH TAG", "Image Path : " + path);
                    // Set the image in ImageView
                    imageView.setImageURI(selectedImageUri);

                }
            }
        }
    }
    private String getPathFromURI(Uri contentUri) throws NullPointerException{
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);

            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                res = cursor.getString(column_index);
            }
            cursor.close();
            return res;
        }



    protected void onUploadButtonClick(){

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating a reference to the full path of the file. myfileRef now points
                // gs://fir-demo-d7354.appspot.com/myuploadedfile.jpg
                StorageReference myfileRef = storageRef.child("myuploadedfile.jpg");
                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();
                Bitmap bitmap = imageView.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = myfileRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getContext(), "TASK FAILED", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "TASK SUCCEEDED", Toast.LENGTH_SHORT).show();
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        String DOWNLOAD_URL = downloadUrl.getPath();
                        Log.v("DOWNLOAD URL", DOWNLOAD_URL);
                        Toast.makeText(getContext(), DOWNLOAD_URL, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }*/
}

