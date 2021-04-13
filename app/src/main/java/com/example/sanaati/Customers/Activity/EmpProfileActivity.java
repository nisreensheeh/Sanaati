package com.example.sanaati.Customers.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sanaati.Customers.Fragment.ProfileFragment;
import com.example.sanaati.R;
import com.example.sanaati.UsersAuth.Class.Users;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmpProfileActivity extends AppCompatActivity {

    Users mEmpdata;
    TextView name, job, address, email_txt, phone_txt;
    ImageView whatsapp_image,call_image, complain_image;
    CircleImageView profimg;
    Button request_btn;
    GridView gridViewCustom;
    ArrayList<String> jobs;
    RatingBar rBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        mEmpdata = (Users)getIntent().getSerializableExtra("Empprofile");

        name = findViewById(R.id.name_txt);
        address = findViewById(R.id.address_txt);
        profimg = findViewById(R.id.img);
        email_txt = findViewById(R.id.email_txt);
        phone_txt = findViewById(R.id.phone_txt);
        gridViewCustom = findViewById(R.id.gridViewCustom);

        jobs = new ArrayList<>();
        String[] temp =mEmpdata.job.split(",");
        for(int j=0; j<temp.length;j++){
            jobs.add(temp[j]);
        }
        gridViewCustom.setAdapter(new CustomGridViewAdapter());

        Uri Imagedata= Uri.parse(mEmpdata.image);
        Picasso.get().load(Imagedata).into(profimg);
        name.setText(mEmpdata.name);
        address.setText(mEmpdata.addressd);
        if(mEmpdata.email.equals("")){
            email_txt.setText("\"الموظف لم يزودنا بالايميل الخاص به\"");
            email_txt.setTextColor(getResources().getColor(R.color.colorPrimary));
        }else {
            email_txt.setText(mEmpdata.email);
        }

        phone_txt.setText(mEmpdata.phone);

        whatsapp_image= findViewById(R.id.whatsapp_image);
        call_image= findViewById(R.id.call_image);
        complain_image= findViewById(R.id.complain_image);
        request_btn= findViewById(R.id.request_btn);

        rBar = findViewById(R.id.ratingBar);
        rBar.setRating(Float.parseFloat(mEmpdata.rate));


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
                        usercomplain.put("clientId", getSharedPreferences("Info", Context.MODE_PRIVATE).getString("userid",""));
                        usercomplain.put("clientName", getSharedPreferences("Info", Context.MODE_PRIVATE).getString("name",""));
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
                final CircleImageView im=dialog.findViewById(R.id.im);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Date newDate = new Date();
                        SimpleDateFormat sdfServer = new SimpleDateFormat("dd/MM/yyyy");

                        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
                        String formattedDate= dateFormat.format(newDate);

//                        Date currentDateTime = Calendar.getInstance().getTime();
                        Map<String, Object> talabat = new HashMap<>();
                        talabat.put("clientId", getSharedPreferences("Info", Context.MODE_PRIVATE).getString("userid",""));
                        talabat.put("clientName", getSharedPreferences("Info", Context.MODE_PRIVATE).getString("name",""));
                        talabat.put("empId", mEmpdata.userid);
                        talabat.put("empName", mEmpdata.name);
                        talabat.put("requestDate",sdfServer.format(newDate));
                        talabat.put("requestTime",formattedDate);
                        talabat.put("empArrivedDateTime","");
                        talabat.put("empLeavedDateTime","");
                        talabat.put("clientLocation",getSharedPreferences("Info", Context.MODE_PRIVATE).getString("location",""));
                        talabat.put("empLocation",mEmpdata.location);
                        talabat.put("totalAmount","");
                        talabat.put("companyComission","");
                        talabat.put("customerEmpRate","");

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        CollectionReference talabRef = db.collection("Talabat");
                        talabRef.add(talabat)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {

//                                        AsyncT asyncT = new AsyncT();
//                                        asyncT.execute();
                                        RequestQueue mRequestQue = Volley.newRequestQueue(EmpProfileActivity.this);
                                        JSONObject json = new JSONObject();
                                        try {
                                            json.put("to", mEmpdata.token);
                                            JSONObject notificationObj = new JSONObject();
                                            notificationObj.put("title", "Sanaati Notification");
                                            notificationObj.put("body", "يوجد لديك طلب خدمة");
                                            //replace notification with data when went send data
                                            json.put("notification", notificationObj);
                                            json.put("data", notificationObj);

                                            String URL = "https://fcm.googleapis.com/fcm/send";
                                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                                                    json,null, null
                                            ) {
                                                @Override
                                                public Map<String, String> getHeaders() {
                                                    Map<String, String> header = new HashMap<>();
                                                    header.put("content-type", "application/json");
                                                    header.put("Sender", "id=1038536184094");
                                                    header.put("authorization", "key=AAAA8c2UkR4:APA91bER9hhaiH5l4XH2FOzwiLo_fL43ZKhDzwlMtOAURiuaFpOLnbAJ_amgRdauQvOQuxZyP2Rb5v4ijdOjGH3W0V4ZVrAyn3VKrM8piJuJcnWluDE8hGicjqjUHhtgKQ6TmKNkW9Tv");
                                                    return header;
                                                }
                                            };
                                            mRequestQue.add(request);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        Toast.makeText(EmpProfileActivity.this, "الحرفي في طريقه اليك", Toast.LENGTH_SHORT).show();
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
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                im.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

    }

    public class CustomGridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return jobs.size();
        }

        @Override
        public Object getItem(int position) {
            return jobs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = EmpProfileActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.table_lay, parent, false);
            }

            TextView t2;
            t2 = convertView.findViewById(R.id.txt2);

            t2.setText(jobs.get(position));
            t2.setBackgroundResource(R.drawable.shape2);

            return convertView; }
    }


    class AsyncT extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL url = new URL(" https://fcm.googleapis.com/fcm/send"); //Enter URL here
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
                httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setRequestProperty("Sender", "id=1038536184094");
                httpURLConnection.setRequestProperty("authorization", "key=AAAA8c2UkR4:APA91bER9hhaiH5l4XH2FOzwiLo_fL43ZKhDzwlMtOAURiuaFpOLnbAJ_amgRdauQvOQuxZyP2Rb5v4ijdOjGH3W0V4ZVrAyn3VKrM8piJuJcnWluDE8hGicjqjUHhtgKQ6TmKNkW9Tv");

                httpURLConnection.connect();

                JSONObject json = new JSONObject();
                try {
                    json.put("to", mEmpdata.token);
                    JSONObject notificationObj = new JSONObject();
                    notificationObj.put("title", "Sanaati Notification");
                    notificationObj.put("body", "يوجد لديك طلب خدمة");
                    //replace notification with data when went send data
                    json.put("notification", notificationObj);
                    json.put("data", notificationObj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
                wr.write(json.toString());
                wr.flush();
//                                            wr.close();

                InputStream response = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response));

//                                            BufferedInputStream reader = new BufferedInputStream(inputStream);

                StringBuilder sb = new StringBuilder();
                String line = null;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                }
//                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes(jsonObject.toString());
//                wr.flush();
//                wr.close()

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


        public void execute() {
        }
    }
}