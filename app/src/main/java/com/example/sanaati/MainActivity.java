package com.example.sanaati;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    CardView serviceCard, LocationsCard;
    RelativeLayout homeRel, profileRel;
    FloatingActionButton fab;
    EditText search_et;
    Button search_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceCard = findViewById(R.id.serviceCard);
        LocationsCard = findViewById(R.id.LocationsCard);
        homeRel = findViewById(R.id.homeRel);
        profileRel = findViewById(R.id.profileRel);
        fab = findViewById(R.id.fab);
        search_et = findViewById(R.id.search_et);
        search_btn = findViewById(R.id.search_btn);

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