package com.example.sanaati.Customers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sanaati.R;
import com.example.sanaati.UsersAuth.Activity.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText NewPass, ConfirmPass;
    Button changeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        NewPass = findViewById(R.id.NewPass);
        ConfirmPass = findViewById(R.id.ConfirmPass);
        changeBtn = findViewById(R.id.changeBtn);

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NewPass.getText().toString().equals(ConfirmPass.getText().toString())){
                    if(getSharedPreferences("Info", MODE_PRIVATE).getString("type","").toString().equals("موظف")){
                        Map<String, Object> user = new HashMap<>();
                        user.put("password",NewPass.getText().toString());
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("Users").document("type").collection("Employees").document(getSharedPreferences("Info", MODE_PRIVATE).getString("userid",""))
                                .update(user)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
//                                    peasLoadingView.stop(); //stop animation
                                            Toast.makeText(ChangePasswordActivity.this, "تم تغيير كلمة السر بنجاح", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ChangePasswordActivity.this, "لقد حدث خطأ...حاول مرة اخرى", Toast.LENGTH_SHORT).show();
//                                peasLoadingView.stop(); //stop animation

                                    }
                                });

                    }else {
                        Map<String, Object> user = new HashMap<>();
                        user.put("password",NewPass);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("Users").document("type").collection("Customers").document(getSharedPreferences("Info", MODE_PRIVATE).getString("userid",""))
                                .update(user)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
//                                    peasLoadingView.stop(); //stop animation
                                            Toast.makeText(ChangePasswordActivity.this, "تم تغيير كلمة السر بنجاح", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ChangePasswordActivity.this, "لقد حدث خطأ...حاول مرة اخرى", Toast.LENGTH_SHORT).show();
//                                peasLoadingView.stop(); //stop animation

                                    }
                                });
                    }
                }else{
                    Toast.makeText(ChangePasswordActivity.this, "كلمتي السر غير متطابقة", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }
}