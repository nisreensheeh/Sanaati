package com.example.sanaati.Customers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.sanaati.Customers.Fragment.MainFragment;
import com.example.sanaati.Customers.Fragment.ProfileFragment;
import com.example.sanaati.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {


    RelativeLayout homeRel, profileRel;
    FloatingActionButton fab;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeRel = findViewById(R.id.homeRel);
        profileRel = findViewById(R.id.profileRel);
        fab = findViewById(R.id.fab);

        frameLayout = findViewById(R.id.frameLayout);


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


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new MainFragment());
        ft.commit();

        profileRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
                ft.addToBackStack(null);
                ft.replace(R.id.frameLayout, new ProfileFragment());
                ft.commit();
            }
        });

        homeRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
//                ft.addToBackStack(null);
//                ft.replace(R.id.frameLayout, new MainFragment());
//                ft.commit();
                recreate();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
                ft.addToBackStack(null);
                ft.replace(R.id.frameLayout, new FabFragment());
                ft.commit();
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