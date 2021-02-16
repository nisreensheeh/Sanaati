package com.example.sanaati.Customers.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;



import com.example.sanaati.R;
import com.example.sanaati.UsersAuth.Class.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment{

    Users mEmpdata;
    TextView name, job, address;
    ImageView whatsapp_image,call_image, complain_image;
    Button request_btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile, container, false);


//        mEmpdata = (Users) getIntent().getSerializableExtra("Empprofile");
//
//        name = findViewById(R.id.name);
//        job = findViewById(R.id.job);
//        address = findViewById(R.id.address);
//
//        name.setText(mEmpdata.getName());
//        job.setText(mEmpdata.getJob());
//        address.setText(mEmpdata.getAddressd());
//
//        whatsapp_image= findViewById(R.id.whatsapp_image);
//        call_image= findViewById(R.id.call_image);
//        complain_image= findViewById(R.id.complain_image);
//        request_btn= findViewById(R.id.request_btn);
//
//        whatsapp_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String contact = mEmpdata.getPhone(); // use country code with your phone number
//                String url = "https://api.whatsapp.com/send?phone=" + contact;
//                try {
//                    PackageManager pm = getApplicationContext().getPackageManager();
//                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse(url));
//                    startActivity(i);
//                } catch (PackageManager.NameNotFoundException e) {
//                    Toast.makeText(getApplicationContext(), "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        call_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Uri u = Uri.parse("tel:" +mEmpdata.getPhone());
//                Intent i = new Intent(Intent.ACTION_DIAL, u);
//
//                try
//                {
//                    startActivity(i);
//                }
//                catch (SecurityException s)
//                {
//                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_LONG)
//                            .show();
//                }
//            }
//        });
//
//        complain_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeProfileActivity.this);
//                LayoutInflater inflater = EmployeeProfileActivity.this.getLayoutInflater();
//                builder.setView(inflater.inflate(R.layout.complaindialoge, null));
//                final AlertDialog dialog = builder.create();
//                ((FrameLayout) dialog.getWindow().getDecorView().findViewById(android.R.id.content)).setForeground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                lp.copyFrom(dialog.getWindow().getAttributes());
//                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                dialog.show();
//                dialog.getWindow().setAttributes(lp);
//                final EditText comlain_et=dialog.findViewById(R.id.comlain_et);
//                final Button send=dialog.findViewById(R.id.send);
//                final Button exit=dialog.findViewById(R.id.exit);
//                send.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        Map<String, Object> usercomplain = new HashMap<>();
//                        usercomplain.put("clientId", getSharedPreferences("Info", Context.MODE_PRIVATE).getString("UserId",""));
//                        usercomplain.put("clientName", getSharedPreferences("Info", Context.MODE_PRIVATE).getString("Name",""));
//                        usercomplain.put("empId", mEmpdata.getUserId());
//                        usercomplain.put("empName", mEmpdata.getName());
//                        usercomplain.put("complainText",comlain_et.getText().toString());
//
//                        FirebaseFirestore db = FirebaseFirestore.getInstance();
//                        CollectionReference complainRef = db.collection("Compliments");
//                        complainRef.add(usercomplain)
//                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                    @Override
//                                    public void onSuccess(DocumentReference documentReference) {
//                                        Toast.makeText(EmployeeProfileActivity.this, "تم ارسال الشكوى بنجاح", Toast.LENGTH_SHORT).show();
//                                        dialog.dismiss();
//
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(EmployeeProfileActivity.this, "لقد حدث خطأ...حاول مرة اخرى", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                    }
//                });
//                exit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//            }
//        });
//
//        request_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeProfileActivity.this);
//                LayoutInflater inflater = EmployeeProfileActivity.this.getLayoutInflater();
//                builder.setView(inflater.inflate(R.layout.dialog_twochoises, null));
//                final AlertDialog dialog = builder.create();
//                ((FrameLayout) dialog.getWindow().getDecorView().findViewById(android.R.id.content)).setForeground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                lp.copyFrom(dialog.getWindow().getAttributes());
//                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                dialog.show();
//                dialog.getWindow().setAttributes(lp);
//                final Button yes=dialog.findViewById(R.id.btn2);
//                final Button no=dialog.findViewById(R.id.btn1);
//                yes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Date currentDateTime = Calendar.getInstance().getTime();
//                        Map<String, Object> talabat = new HashMap<>();
//                        talabat.put("clientId", getSharedPreferences("Info", Context.MODE_PRIVATE).getString("UserId",""));
//                        talabat.put("empId", mEmpdata.getUserId());
//                        talabat.put("DateTime",currentDateTime);
//                        talabat.put("empLocation",mEmpdata.getLocation());
//
//                        FirebaseFirestore db = FirebaseFirestore.getInstance();
//                        CollectionReference talabRef = db.collection("Talabat");
//                        talabRef.add(talabat)
//                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                    @Override
//                                    public void onSuccess(DocumentReference documentReference) {
//                                        Toast.makeText(EmployeeProfileActivity.this, "الحرفي في طريقه اليك", Toast.LENGTH_SHORT).show();
//                                        dialog.dismiss();
//
//                                        Bundle bundle = new Bundle();
//                                        bundle.putString("openMapFragment","yes");
//                                        bundle.putString("empLocation",mEmpdata.getLocation());
//                                        bundle.putString("empName",mEmpdata.getName());
//                                        bundle.putString("empJob",mEmpdata.getJob());
//                                        bundle.putString("clientLocation",getSharedPreferences("Info", Context.MODE_PRIVATE).getString("UserLocation",""));
//                                        MapFragment fragInfo = new MapFragment();
//                                        fragInfo.setArguments(bundle);
////                                        transaction.replace(R.id.fragment_single, fragInfo);
////                                        transaction.commit();
//                                        startActivity(new Intent(EmployeeProfileActivity.this, MainActivity.class));
//                                        finish();
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(EmployeeProfileActivity.this, "لقد حدث خطأ...حاول مرة اخرى", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                    }
//                });
//                no.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//
//            }
//        });


        return rootView;
    }

}
