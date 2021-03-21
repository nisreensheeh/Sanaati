package com.example.sanaati.Customers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.sanaati.Customers.Fragment.FabFragment;
import com.example.sanaati.Customers.Fragment.MainFragment;
import com.example.sanaati.Customers.Fragment.ProfileFragment;
import com.example.sanaati.R;
import com.example.sanaati.UsersAuth.Activity.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {


    RelativeLayout homeRel, profileRel;
    FloatingActionButton fab;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Space(savedInstanceState);
//        homeRel = findViewById(R.id.homeRel);
//        profileRel = findViewById(R.id.profileRel);
//        fab = findViewById(R.id.fab);

        frameLayout = findViewById(R.id.frameLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);

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
        ft.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
        ft.addToBackStack(null);
        ft.replace(R.id.frameLayout, new MainFragment());
        ft.commit();

//        profileRel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
//                ft.addToBackStack(null);
//                ft.replace(R.id.frameLayout, new ProfileFragment());
//                ft.commit();
//            }
//        });
//
//        homeRel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
////                ft.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
////                ft.addToBackStack(null);
////                ft.replace(R.id.frameLayout, new MainFragment());
////                ft.commit();
//                recreate();
//            }
//        });
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
//                ft.addToBackStack(null);
//                ft.replace(R.id.frameLayout, new FabFragment());
//                ft.commit();
//            }
//        });
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
                startActivity(new Intent(MainActivity.this, ChangePasswordActivity.class));
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
            }
            else {
                {
                    db.collection("Users").document("type").collection("Customers").document(getSharedPreferences("Info",MODE_PRIVATE).getString("userid",""))
                            .update("token", getSharedPreferences("Info",MODE_PRIVATE).getString("token", "-"));
                }
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
//                startActivity(new Intent(MainActivity.this, MainActivity.class));
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
                ft.addToBackStack(null);
                ft.replace(R.id.frameLayout, new MainFragment());
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
//                        final AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
//                        LayoutInflater inflater1 = MainActivity.this.getLayoutInflater();
//                        builder1.setView(inflater1.inflate(R.layout.help_dialog, null));
//                        final AlertDialog dialog1 = builder1.create();
//                        ((FrameLayout) dialog1.getWindow().getDecorView().findViewById(android.R.id.content)).setForeground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                        WindowManager.LayoutParams lp1 = new WindowManager.LayoutParams();
//                        lp1.copyFrom(dialog1.getWindow().getAttributes());
//                        lp1.width = WindowManager.LayoutParams.WRAP_CONTENT;
//                        lp1.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                        dialog1.show();
//                        dialog1.getWindow().setAttributes(lp1);
//                        final Button close=dialog1.findViewById(R.id.btn2);
//                        final CircleImageView im1=dialog1.findViewById(R.id.im);
//                        im1.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog1.dismiss();
//                            }
//                        });
//                        close.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog1.dismiss();
//                            }
//                        });
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
//                        final AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
//                        LayoutInflater inflater1 = HomeActivity.this.getLayoutInflater();
//                        builder1.setView(inflater1.inflate(R.layout.help_dialog, null));
//                        final AlertDialog dialog1 = builder1.create();
//                        ((FrameLayout) dialog1.getWindow().getDecorView().findViewById(android.R.id.content)).setForeground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                        WindowManager.LayoutParams lp1 = new WindowManager.LayoutParams();
//                        lp1.copyFrom(dialog1.getWindow().getAttributes());
//                        lp1.width = WindowManager.LayoutParams.WRAP_CONTENT;
//                        lp1.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                        dialog1.show();
//                        dialog1.getWindow().setAttributes(lp1);
//                        final Button close=dialog1.findViewById(R.id.btn2);
//                        final CircleImageView im1=dialog1.findViewById(R.id.im);
//                        im1.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog1.dismiss();
//                            }
//                        });
//                        close.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog1.dismiss();
//                            }
//                        });
                        break;

                    case 1:
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
                        ft.addToBackStack(null);
                        ft.replace(R.id.frameLayout, new ProfileFragment());
                        ft.commit();
//                        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
//                        LayoutInflater inflater = HomeActivity.this.getLayoutInflater();
//                        builder.setView(inflater.inflate(R.layout.dialog_info, null));
//                        final AlertDialog dialog = builder.create();
//                        ((FrameLayout) dialog.getWindow().getDecorView().findViewById(android.R.id.content)).setForeground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                        lp.copyFrom(dialog.getWindow().getAttributes());
//                        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                        dialog.show();
//                        dialog.getWindow().setAttributes(lp);
//                        final Button exit=dialog.findViewById(R.id.btn2);
//                        final CircleImageView im=dialog.findViewById(R.id.im);
//                        im.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
//                        exit.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                //finish(); kill this activity only
//                                finishAffinity();
//                                System.exit(0);//kill all activity
//                            }
//                        });
                        break;
                }
            }
        });
        spaceNavigationView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
    }
}