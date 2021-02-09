package com.example.sanaati.UsersAuth.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sanaati.MainActivity;
import com.example.sanaati.R;
import com.example.sanaati.UsersAuth.Class.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail,inputPassword,name,phone,addressd;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    Spinner type_spinner, job_spinner;
    String type, job;
    FirebaseFirestore db;
    RelativeLayout jobrel;
    ImageView profileimage;
    private static final int ImageBack = 1;
    StorageReference storageReference;
    String newToken = "";
    String ImageUri="";
    Uri ImageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        name=(EditText) findViewById(R.id.nameField);
        phone=(EditText) findViewById(R.id.phoneField);
        addressd=(EditText) findViewById(R.id.address);
        btnResetPassword = findViewById(R.id.btn_reset_password);
        type_spinner = findViewById(R.id.type_spinner);
        job_spinner = findViewById(R.id.job_spinner);
        jobrel = findViewById(R.id.job_spinner_lay);
        profileimage = findViewById(R.id.profileimage);

        db = FirebaseFirestore.getInstance();
        final String[] typeSpinnerarray = new String[]{"--اختر--", "صاحب حرفة", "طالب خدمة"};

        ArrayAdapter<String> typeaadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, typeSpinnerarray);
        typeaadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(typeaadapter);
        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 type = parent.getSelectedItem().toString();
                 if(type.equals("صاحب حرفة")){
                     jobrel.setVisibility(View.VISIBLE);
                 }else if(type.equals("طالب خدمة")){
                     jobrel.setVisibility(View.GONE);
                 }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final String[] jobSpinnerarray = new String[]{"--اختر--", "ميكانيكي", "موسرجي","كهربجي"};

        ArrayAdapter<String> jobaadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, jobSpinnerarray);
        typeaadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        job_spinner.setAdapter(jobaadapter);
        job_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                job = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,ImageBack);
            }
        });
//        btnResetPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(nova.charity.SignupActivity.this, ResetPasswordActivity.class));
//            }
//        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayList<Users> users = new ArrayList<>();
        users.clear();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                pb.show();

                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                final String username = name.getText().toString().trim();
                final String userphone=phone.getText().toString().trim();
                final String address=addressd.getText().toString().trim();

                if(address.equals("") || password.equals("") || username.equals("") || userphone.equals("") || type.equals("--اختر--")){
                    Toast.makeText(SignupActivity.this, "رجاءا املئ كامل البيانات", Toast.LENGTH_SHORT).show();
//                pb.dismiss();
                }else{
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Users").document("type").collection("Customers")
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    users.add(new Users(document.getString("userid"),document.getString("name"),document.getString("email"),
                                            document.getString("addressd"),document.getString("phone"),document.getString("job"),
                                            document.getString("password"),document.getString("location"),document.getString("type")
                                            , document.getString("rate"), document.getString("token"), document.getString("image")
                                            , document.getString("aid"), document.getString("comission")));

                                }

                            }
                        }
                    });
                    FirebaseFirestore db1 = FirebaseFirestore.getInstance();
                    db1.collection("Users").document("type").collection("Employees")
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    users.add(new Users(document.getString("userid"),document.getString("name"),document.getString("email"),
                                            document.getString("addressd"),document.getString("phone"),document.getString("job"),
                                            document.getString("password"),document.getString("location"),document.getString("type")
                                            , document.getString("rate"), document.getString("token"), document.getString("image")
                                            , document.getString("aid"), document.getString("comission")));
                                }

                            }
                        }
                    });
                    int pos = 0;
                    if(!users.isEmpty() || users.size()!=0 || users!=null){

                        for(int i = 0; i<users.size(); i++){
                            if(users.get(i).name.equals(username)) {
                                pos+=1;
                            }
                        }
                    }
                    if(pos>0){
                        Toast.makeText(SignupActivity.this, "هذا الاسم موجود, لا يمكن تكرار الاسم ", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        Date date = new Date();
                        //This method returns the time in millis
                        long timeMilli = date.getTime();

//                            try{
//                                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
//                                     newToken = instanceIdResult.getToken();
//
//                                });
//                            }
//                            catch (Exception e){}

                        final String android_id = Settings.Secure.getString(SignupActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                        if(type.equals("صاحب حرفة")){
                            if(job.equals("--اختر--")) {
                                Toast.makeText(SignupActivity.this, "رجاءا اختر مهنتك", Toast.LENGTH_SHORT).show();
                            }else {
                                FirebaseStorage storage =  FirebaseStorage.getInstance();;
                                final StorageReference ImageName =  storage.getReference().child("image"+ImageData.getLastPathSegment());
                                ImageName.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(SignupActivity.this, "تم تحميل الصوة", Toast.LENGTH_SHORT).show();
                                        ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
//                                                Users employees = new Users(timeMilli+username,username,email,address,userphone,job,password,"x,y","موظف"
//                                                        , "", newToken, uri.toString()
//                                                        , "", "");

                                                Map<String, Object> user = new HashMap<>();
                                                user.put("userid",timeMilli+username );
                                                user.put("name", username);
                                                user.put("email", email);
                                                user.put("addressd",address );
                                                user.put("phone", userphone);
                                                user.put("job",job );
                                                user.put("password",password );
                                                user.put("location", "0.0,0.0");
                                                user.put("type","موظف" );
                                                user.put("rate", "5");
                                                user.put("token", getSharedPreferences("Info",MODE_PRIVATE).getString("token",""));
                                                user.put("image",uri.toString());
                                                user.put("aid", android_id);
                                                user.put("comission", "0.0");

                                                SharedPreferences.Editor editor = getSharedPreferences("Info",MODE_PRIVATE).edit();
                                                editor.putString("userid",timeMilli+username);
                                                editor.putString("name", username);
                                                editor.putString("email", email);
                                                editor.putString("addressd",address);
                                                editor.putString("phone", userphone);
                                                editor.putString("job",job);
                                                editor.putString("password",password);
                                                editor.putString("location", "0.0,0.0");
                                                editor.putString("type","موظف");
                                                editor.putString("rate", "5");
                                                editor.putString("token", getSharedPreferences("Info",MODE_PRIVATE).getString("token",""));
                                                editor.putString("image",uri.toString());
                                                editor.putString("aid", android_id);
                                                editor.putString("comission", "0.0");
                                                editor.apply();

                                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                db.collection("Users").document("type").collection("Employees").document(user.get("userid").toString())
                                                        .set(user)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    Toast.makeText(SignupActivity.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();
                                                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                                                }
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(SignupActivity.this, "لقد حدث خطأ...حاول مرة اخرى", Toast.LENGTH_SHORT).show();

                                                            }
                                                        });
                                            }
                                        });

                                    }
                                });

                            }

                        }else if(type.equals("طالب خدمة")){

                            FirebaseStorage storage =  FirebaseStorage.getInstance();;
                            final StorageReference ImageName =  storage.getReference().child("image"+ImageData.getLastPathSegment());
                            ImageName.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(SignupActivity.this, "تم تحميل الصوة", Toast.LENGTH_SHORT).show();
                                    ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
//                                                Users employees = new Users(timeMilli+username,username,email,address,userphone,job,password,"x,y","موظف"
//                                                        , "", newToken, uri.toString()
//                                                        , "", "");

                                            Map<String, Object> user = new HashMap<>();
                                            user.put("userid",timeMilli+username );
                                            user.put("name", username);
                                            user.put("email", email);
                                            user.put("addressd",address );
                                            user.put("phone", userphone);
                                            user.put("job",job );
                                            user.put("password",password );
                                            user.put("location", "0.0,0.0");
                                            user.put("type","زبون" );
                                            user.put("rate", "5");
                                            user.put("token", getSharedPreferences("Info",MODE_PRIVATE).getString("token",""));
                                            user.put("image",uri.toString());
                                            user.put("aid", android_id);
                                            user.put("comission", "0.0");

                                            SharedPreferences.Editor editor = getSharedPreferences("Info",MODE_PRIVATE).edit();
                                            editor.putString("userid",timeMilli+username );
                                            editor.putString("name", username);
                                            editor.putString("email", email);
                                            editor.putString("addressd",address );
                                            editor.putString("phone", userphone);
                                            editor.putString("job",job );
                                            editor.putString("password",password );
                                            editor.putString("location", "0.0,0.0");
                                            editor.putString("type","زبون" );
                                            editor.putString("rate", "5");
                                            editor.putString("token", getSharedPreferences("Info",MODE_PRIVATE).getString("token",""));
                                            editor.putString("image",uri.toString());
                                            editor.putString("aid", android_id);
                                            editor.putString("comission", "0.0");
                                            editor.apply();

                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                                            db.collection("Users").document("type").collection("Customers").document(user.get("userid").toString())
                                                    .set(user)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                Toast.makeText(SignupActivity.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();
                                                                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                                            }
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(SignupActivity.this, "لقد حدث خطأ...حاول مرة اخرى", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });
                                        }
                                    });

                                }
                            });

                        }
                    }
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ImageBack){
            if(resultCode == RESULT_OK){
                ImageData = data.getData();
                ImageUri = String.valueOf(ImageData);

                Picasso.get().load(ImageData).into(profileimage);

            }
        }
    }
}