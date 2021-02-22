package com.example.sanaati.Customers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanaati.R;
import com.example.sanaati.UsersAuth.Class.Users;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmpProfileActivity extends AppCompatActivity {

    Users mEmpdata;
    TextView name, job, address;
    ImageView whatsapp_image,call_image, complain_image;
    CircleImageView profimg;
    Button request_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        mEmpdata = (Users)getIntent().getSerializableExtra("Empprofile");

        name = findViewById(R.id.name);
        job = findViewById(R.id.job);
        address = findViewById(R.id.address);
        profimg = findViewById(R.id.img);
        Uri Imagedata= Uri.parse(mEmpdata.image);
        Picasso.get().load(Imagedata).into(profimg);
        name.setText(mEmpdata.name);
        job.setText(mEmpdata.job);
        address.setText(mEmpdata.addressd);

        whatsapp_image= findViewById(R.id.whatsapp_image);
        call_image= findViewById(R.id.call_image);
        complain_image= findViewById(R.id.complain_image);
        request_btn= findViewById(R.id.request_btn);

        whatsapp_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = mEmpdata.phone; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = getApplicationContext().getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        call_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u = Uri.parse("tel:" +mEmpdata.phone);
                Intent i = new Intent(Intent.ACTION_DIAL, u);

                try
                {
                    startActivity(i);
                }
                catch (SecurityException s)
                {
                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        complain_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(EmpProfileActivity.this);
                LayoutInflater inflater = EmpProfileActivity.this.getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.complaindialoge, null));
                final AlertDialog dialog = builder.create();
                ((FrameLayout) dialog.getWindow().getDecorView().findViewById(android.R.id.content)).setForeground(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.show();
                dialog.getWindow().setAttributes(lp);
                final EditText comlain_et=dialog.findViewById(R.id.comlain_et);
                final Button send=dialog.findViewById(R.id.send);
                final Button exit=dialog.findViewById(R.id.exit);
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String, Object> usercomplain = new HashMap<>();
                        usercomplain.put("clientId", getSharedPreferences("Info", Context.MODE_PRIVATE).getString("UserId",""));
                        usercomplain.put("clientName", getSharedPreferences("Info", Context.MODE_PRIVATE).getString("Name",""));
                        usercomplain.put("empId", mEmpdata.userid);
                        usercomplain.put("empName", mEmpdata.name);
                        usercomplain.put("complainText",comlain_et.getText().toString());

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        CollectionReference complainRef = db.collection("Compliments");
                        complainRef.add(usercomplain)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(EmpProfileActivity.this, "تم ارسال الشكوى بنجاح", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(EmpProfileActivity.this, "لقد حدث خطأ...حاول مرة اخرى", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(EmpProfileActivity.this);
                LayoutInflater inflater = EmpProfileActivity.this.getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.dialog_twochoises, null));
                final AlertDialog dialog = builder.create();
                ((FrameLayout) dialog.getWindow().getDecorView().findViewById(android.R.id.content)).setForeground(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.show();
                dialog.getWindow().setAttributes(lp);
                final Button yes=dialog.findViewById(R.id.btn2);
                final Button no=dialog.findViewById(R.id.btn1);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Date currentDateTime = Calendar.getInstance().getTime();
                        Map<String, Object> talabat = new HashMap<>();
                        talabat.put("clientId", getSharedPreferences("Info", Context.MODE_PRIVATE).getString("UserId",""));
                        talabat.put("empId", mEmpdata.userid);
                        talabat.put("DateTime",currentDateTime);
                        talabat.put("empLocation",mEmpdata.location);

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        CollectionReference talabRef = db.collection("Talabat");
                        talabRef.add(talabat)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(EmpProfileActivity.this, "الحرفي في طريقه اليك", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();

                                        Bundle bundle = new Bundle();
                                        bundle.putString("openMapFragment","yes");
                                        bundle.putString("empLocation",mEmpdata.location);
                                        bundle.putString("empName",mEmpdata.name);
                                        bundle.putString("empJob",mEmpdata.job);
                                        bundle.putString("clientLocation",getSharedPreferences("Info", Context.MODE_PRIVATE).getString("UserLocation",""));
                                        MapFragment fragInfo = new MapFragment();
                                        fragInfo.setArguments(bundle);
//                                        transaction.replace(R.id.fragment_single, fragInfo);
//                                        transaction.commit();
                                        startActivity(new Intent(EmpProfileActivity.this, MainActivity.class));
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(EmpProfileActivity.this, "لقد حدث خطأ...حاول مرة اخرى", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

    }
}