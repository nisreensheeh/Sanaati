package com.example.sanaati.Employees.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.sanaati.Customers.Activity.ChangePasswordActivity;
import com.example.sanaati.Customers.Activity.MainActivity;
import com.example.sanaati.Customers.Fragment.FabFragment;
import com.example.sanaati.Customers.Fragment.MainFragment;
import com.example.sanaati.Customers.Fragment.ProfileFragment;
import com.example.sanaati.Employees.Fragment.EmpMainFrag;
import com.example.sanaati.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class EmpMainActivity extends AppCompatActivity {
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_main);
        Space(savedInstanceState);

        frameLayout = findViewById(R.id.frameLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            String token = task.getResult();
                            SharedPreferences.Editor editor = getSharedPreferences("Info",MODE_PRIVATE).edit();
                            editor.putString("token", token);
                            editor.putString("new_token", "yes");
                            editor.apply();
                            UploadToken();
                        }
                    }
                });


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
        ft.addToBackStack(null);
        ft.replace(R.id.frameLayout, new EmpMainFrag());
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_example, menu);
//
        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout:
                // do something
                return true;
            case R.id.changpass:
                startActivity(new Intent(EmpMainActivity.this, ChangePasswordActivity.class));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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
            }else {
                db.collection("Users").document("type").collection("Customers").document(getSharedPreferences("Info",MODE_PRIVATE).getString("userid",""))
                        .update("token", getSharedPreferences("Info",MODE_PRIVATE).getString("token", "-"));
            }
        }
    }

    private void Space(Bundle savedInstanceState){

        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);

        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_help));
        spaceNavigationView.addSpaceItem(new SpaceItem("",R.drawable.ic_baseline_person_pin_24));
        spaceNavigationView.showIconOnly();
        spaceNavigationView.setSpaceItemIconSize(100);
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
                ft.addToBackStack(null);
                ft.replace(R.id.frameLayout, new EmpMainFrag());
                ft.commit();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 0:
                        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                        ft1.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
                        ft1.addToBackStack(null);
                        ft1.replace(R.id.frameLayout, new FabFragment());
                        ft1.commit();
                        break;
                    case 1:
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
                        ft.addToBackStack(null);
                        ft.replace(R.id.frameLayout, new ProfileFragment());
                        ft.commit();
                        break;

                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 0:
                        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                        ft1.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
                        ft1.addToBackStack(null);
                        ft1.replace(R.id.frameLayout, new FabFragment());
                        ft1.commit();
                        break;

                    case 1:
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
                        ft.addToBackStack(null);
                        ft.replace(R.id.frameLayout, new ProfileFragment());
                        ft.commit();
                        break;
                }
            }
        });
        spaceNavigationView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
    }

}