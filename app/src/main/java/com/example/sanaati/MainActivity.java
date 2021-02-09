package com.example.sanaati;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            String token = task.getResult();
//                            if(!getSharedPreferences("Info",MODE_PRIVATE).getString("token", "-").equals(token)){
                                SharedPreferences.Editor editor = getSharedPreferences("Info",MODE_PRIVATE).edit();
                                editor.putString("token", token);
                                editor.putString("new_token", "yes");
                                editor.apply();
                                UploadToken();
//                            }
                        }
                    }
                });


    }


    private void UploadToken() {

        if(getSharedPreferences("Info",MODE_PRIVATE).getString("new_token", "no").equals("yes")){
            SharedPreferences.Editor editor = getSharedPreferences("Info",MODE_PRIVATE).edit();
            editor.putString("new_token", "no");
            editor.apply();

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            if((getSharedPreferences("Info",MODE_PRIVATE).getString("type","").equals("موظف"))){
            db.collection("Users").document("type").collection("Employees").document(getSharedPreferences("Info",MODE_PRIVATE).getString("userid",""))
                    .update("token", getSharedPreferences("Info",MODE_PRIVATE).getString("token", "-"));
            }
            else {
                {
                    db.collection("Users").document("type").collection("Customers").document(getSharedPreferences("Info",MODE_PRIVATE).getString("userid",""))
                            .update("token", getSharedPreferences("Info",MODE_PRIVATE).getString("token", "-"));
                }
            }
        }
    }
}